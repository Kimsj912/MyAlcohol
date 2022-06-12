package com.example.lastproj_mp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lastproj_mp.databinding.ListAlcBinding
import com.example.lastproj_mp.databinding.ListHistoryBinding

class MyHistoryAdapter(private var dataSet: MutableList<History>):RecyclerView.Adapter<MyHistoryAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListHistoryBinding) : RecyclerView.ViewHolder(binding.root)
    override fun getItemCount() = dataSet.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    fun setList(newList: MutableList<History>){
        this.dataSet = newList
    }
    fun getElement(pos: Int): History{
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
        val binding = holder.binding
        binding.date.text = dataSet[position].date
        binding.alcName.text = dataSet[position].alcName
        binding.drunk.text = dataSet[position].drunk.toString()+" ml"
        binding.alcAmount.text = dataSet[position].alcAmount.toString()
    }
}