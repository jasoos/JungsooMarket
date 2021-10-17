package com.android.jungsoomarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.jungsoomarket.room.Product

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var adapter: ProductsAdapter
    private lateinit var scanViewModel: ScanViewModel
    private lateinit var totalTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ProductsAdapter(requireContext(), ArrayList())
        scanViewModel = ScanViewModel.getInstance(requireActivity().application)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalTV = view.findViewById(R.id.total_TV)
        val rv = view.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        scanViewModel.scanData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                val prod: Product? = scanViewModel.getProduct(it)

                if (prod != null) {
                    adapter.addItem(prod)
                    adapter.notifyDataSetChanged()
                    totalTV.text = getTotal()
                }
            }
        })
    }

    private fun getTotal(): String {
        var totalPrice = 0.0
        for (product in adapter.array) {
            totalPrice += product.price?.replace("$", "")?.toDouble()!!
        }
        return totalPrice.toString()
    }
}