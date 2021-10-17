package com.android.jungsoomarket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.jungsoomarket.room.Product

class ProductsAdapter(var context: Context, var array: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parentViewGroup = parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.prodIDTV.text = array[position].name
        holder.prodPriceTV.text = array[position].price
    }

    override fun getItemCount(): Int {
        return array.size
    }

    fun addItem(it: Product?) {
        it?.let { it1 -> array.add(it1) }

    }

    inner class ViewHolder(parentViewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.product_item, parentViewGroup, false)
    ) {
        val prodIDTV: TextView = itemView.findViewById(R.id.prod_name)
        val prodPriceTV: TextView = itemView.findViewById(R.id.prod_price)
    }
}


