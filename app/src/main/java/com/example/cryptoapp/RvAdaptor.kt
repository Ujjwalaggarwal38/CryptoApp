package com.example.cryptoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.databinding.RvItemBinding

class RvAdaptor(val context:Context, var data:ArrayList<Modal>):
    RecyclerView.Adapter<RvAdaptor.ViewHolder>() {

        fun changedData(filterData:ArrayList<Modal>){
                data=filterData
            notifyDataSetChanged()
        }
    inner class ViewHolder(val binding:RvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setAnimation(holder.itemView)
       holder.binding.name.text = data[position].name
       holder.binding.symbol.text = data[position].symbol
       holder.binding.price.text = data[position].price
    }
    fun setAnimation(view: View){
        val anime = AlphaAnimation(.0f,1.0f)
        anime.duration=1000
        view.startAnimation(anime)


    }
}