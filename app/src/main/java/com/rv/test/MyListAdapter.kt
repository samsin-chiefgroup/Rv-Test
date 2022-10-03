package com.rv.test

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListDiffer.ListListener
import androidx.recyclerview.widget.RecyclerView

abstract class MyListAdapter<T, VH : RecyclerView.ViewHolder>() :
    RecyclerView.Adapter<VH>() {

    private var differ: AsyncListDiffer<T>? = null
    private val listener = ListListener { previousList, currentList -> this.onCurrentListChanged(previousList, currentList) }
//
//    init {
//        differ.addListListener(listener)
//    }

    fun setDiffer(differ: AsyncListDiffer<T>) {
        this.differ = differ
        this.differ?.addListListener(listener)
    }

    open fun submitList(list: List<T>?) {
        differ!!.submitList(list)
    }

    open fun submitList(list: List<T>?, commitCallback: Runnable?) {
        differ!!.submitList(list, commitCallback)
    }

    protected open fun getItem(position: Int): T {
        return differ!!.currentList[position]
    }

    override fun getItemCount(): Int {
        return differ!!.currentList.size
    }

    open fun getCurrentList(): List<T> {
        return differ!!.currentList
    }

    open fun onCurrentListChanged(previousList: List<T>, currentList: List<T>) {}
}