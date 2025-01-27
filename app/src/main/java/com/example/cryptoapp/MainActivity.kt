package com.example.cryptoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.ui.theme.CryptoAppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdaptor: RvAdaptor
    private lateinit var data:ArrayList<Modal>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data=ArrayList<Modal>()
        apiData
        rvAdaptor=RvAdaptor(this,data)
        binding.rv.layoutManager=LinearLayoutManager(this )
        binding.rv.adapter=rvAdaptor
        binding.search1.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val filterdata = ArrayList<Modal>()
                for(item in data){
                    if(item.name.lowercase(Locale.getDefault()).contains(s.toString().lowercase(Locale.getDefault()))){
                            filterdata.add(item)
                    }

                }
                if(filterdata.isEmpty()){
                    Toast.makeText(this@MainActivity,"No data Available",Toast.LENGTH_LONG).show()
                }
                else{
                    rvAdaptor.changedData(filterdata)
                }
            }

        })
    }
    val apiData:Unit
        get() {
            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
            val queue=Volley.newRequestQueue(this)
            val jsonObjectRequest:JsonObjectRequest= @SuppressLint("DefaultLocale")
            object:JsonObjectRequest(
                Method.GET,url,null,
                Response.Listener {
                    response ->
                    binding.progressBar.isVisible=false
                    try{
                        val dataArray=response.getJSONArray("data")
                        for(i in 0 until  dataArray.length()){
                            val dataObject = dataArray.getJSONObject(i)
                            val symbol = dataObject.getString("symbol")
                            val name = dataObject.getString("name")
                            val quote=dataObject.getJSONObject("quote")
                            val usd=quote.getJSONObject("USD")
                            val price = String.format("$ "+"%.2f",usd.getDouble( "price"))
                            data.add(Modal(name,price,symbol))
                        }
                        rvAdaptor.notifyDataSetChanged()
                    }
                    catch (e:Exception){
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                    }

            },Response.ErrorListener{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
            })
            {
                override fun getHeaders(): Map<String, String> {
                    val headers=HashMap<String,String>();
                    headers["X-CMC_PRO_API_KEY"] = "19fdfa37-d3cd-4202-813b-7edf9b7e165d"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }
}