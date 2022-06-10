package com.example.lastproj_mp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lastproj_mp.databinding.ListItemBinding

class MyAdapter(private var dataSet: MutableList<MyElement>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun getItemCount() = dataSet.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    fun setList(newList: MutableList<MyElement>){
        this.dataSet = newList
    }
    fun getElement(pos: Int): MyElement{
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