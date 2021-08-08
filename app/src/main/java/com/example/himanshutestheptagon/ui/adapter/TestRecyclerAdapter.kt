package com.example.himanshutestheptagon.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.himanshutestheptagon.data.model.ItemList
import com.example.himanshutestheptagon.databinding.ListItemTestBinding
import java.util.*


internal class TestRecyclerAdapter(val mListener: (ItemList) -> Unit) :
    RecyclerView.Adapter<TestRecyclerAdapter.DrugAdapterAdapterViewHolder>() {


    var list1: ArrayList<ItemList> = arrayListOf()
    /**
     * This method is called right when adapter is created &
     * is used to initialize ViewHolders
     * */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DrugAdapterAdapterViewHolder {
        val binding =
            ListItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DrugAdapterAdapterViewHolder(binding)
    }

    /** It is called for each ViewHolder to bind it to the adapter &
     * This is where we pass data to ViewHolder
     * */
    @SuppressLint("LogNotTimber", "SetTextI18n")
    override fun onBindViewHolder(holder: DrugAdapterAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }


    private fun getItem(position: Int): ItemList {
        return list1[position]
    }

    fun getItems(): java.util.ArrayList<ItemList> {
        return list1
    }

    /**
     * This method returns the size of collection that contains the items we want to display
     * */
    override fun getItemCount(): Int {
        return list1.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    /**
     * Swap function to set new data on updating
     */
    fun replaceItems(items: ArrayList<ItemList>) {
        list1 = items
        notifyDataSetChanged()
    }

    inner class DrugAdapterAdapterViewHolder(val dataBinding: ListItemTestBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        init {
            dataBinding.root.setOnClickListener {
                mListener(getItem(adapterPosition))
            }
        }

        fun onBind(data: ItemList, position: Int) {

            val binding = dataBinding
            binding.mylist = data


            /**
             * function to test data in unit test
             */
            fun addItemsForUnitTest(items: ArrayList<ItemList>) {
                list1 = items
            }

        }


    }

}