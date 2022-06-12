package com.example.lastproj_mp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lastproj_mp.databinding.ListAlcBinding

class MyAlcoholAdapter(private var dataSet: List<Alcohol>):RecyclerView.Adapter<MyAlcoholAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListAlcBinding) : RecyclerView.ViewHolder(binding.root)
    override fun getItemCount() = dataSet.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListAlcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    fun setList(newList: MutableList<Alcohol>){
        this.dataSet = newList
    }
    fun getElement(pos: Int): Alcohol{
        return dataSet[pos]
    }
    private lateinit var itemClickListener : OnItemClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.alcType.text = dataSet[position].alcType
        binding.alcName.text = dataSet[position].alcName
        binding.bottle.text = dataSet[position].bottle.toString()
        binding.cup.text = dataSet[position].cup.toString()
        binding.percent.text = dataSet[position].percent.toString()
        binding.elem.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }
}