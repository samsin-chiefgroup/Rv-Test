package com.rv.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rv.test.databinding.CellTest2Binding
import com.rv.test.databinding.CellTestBinding

class DiffCallback : DiffUtil.ItemCallback<TestItem>() {
    override fun areItemsTheSame(oldItem: TestItem, newItem: TestItem): Boolean {
        if (oldItem.isItm != newItem.isItm) {
            return false
        }
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestItem, newItem: TestItem): Boolean {
        return false
    }
}

class Adapter : ListAdapter<TestItem, Adapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(val binding: CellTestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CellTestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tv.text = "#$position, ${item.id}"
            tv.setBackgroundColor(ContextCompat.getColor(root.context, if (item.isItm) R.color.teal_200 else R.color.white))
        }
    }
}


class Adapter2 :
    MyListAdapter<TestItem, Adapter2.ViewHolder>() {
    class ViewHolder(val binding: CellTestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CellTestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tv.text = "#$position, ${item.id}"
            tv.setBackgroundColor(ContextCompat.getColor(root.context, if (item.isItm) R.color.teal_200 else R.color.white))
        }
    }
}


class Adapter3 :
    MyListAdapter<TestItem, Adapter3.ViewHolder>() {
    class ViewHolder(val binding: CellTest2Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CellTest2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tv.text = "#$position, ${item.id}X"
            tv.setBackgroundColor(ContextCompat.getColor(root.context, if (item.isItm) R.color.teal_200 else R.color.white))
        }
    }
}

interface Item

data class TestItem(val id: Int, val text: String, val isItm: Boolean) : Item