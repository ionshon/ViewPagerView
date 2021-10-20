package com.example.viewpagerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpagerview.databinding.ItemViewpagerBinding

class CustomPagerAdapter(val textList: List<String>): RecyclerView.Adapter<CustomPagerAdapter.Holder>() {



    class Holder(val binding: ItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setText(text:String) {
            binding.textView.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = textList[position]
        holder.setText(text)
    }

    override fun getItemCount()= textList.size

}