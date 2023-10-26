package com.firstdueapplication.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firstdueapplication.R
import com.firstdueapplication.adapter.AnimalAdapter
import com.firstdueapplication.adapter.ProductAdapter
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.databinding.FragmentDashboardBinding
import com.firstdueapplication.models.Photo
import com.firstdueapplication.models.Product
import com.firstdueapplication.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentDashboardBinding? = null
    private val productViewModel by viewModels<ProductViewModel>()

    private val binding get() = _binding!!

    lateinit var adapter: ProductAdapter
    lateinit var productList: List<Product>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter(::onProductClicked)
        binding.rvAnimalList.layoutManager = GridLayoutManager(context, 2)


        binding.rvAnimalList.adapter = adapter
        productViewModel.getProductList()

        InitializeCategory()
        handleObserver()

    }

    private fun InitializeCategory() {
        val spinAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cateogry_list, android.R.layout.simple_spinner_item
        )
        spinAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinCategory.adapter = spinAdapter
        binding.spinCategory.setOnItemSelectedListener(this);
    }

    private fun handleObserver() {
        productViewModel.productMutableList.observe(viewLifecycleOwner, Observer {
            binding.pbProgressBar.isVisible = false
            when (it) {
                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {
                    binding.pbProgressBar.isVisible = true
                }

                is NetworkResult.Success -> {
                    productList = it.data!!
                    adapter.submitList(it.data)
                }
            }
        })
    }

    private fun onProductClicked(product: Product) {
        Toast.makeText(context, "${product.name} clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        (parent?.getChildAt(0) as TextView).setTextColor(Color.BLACK)
        val item = parent?.getItemAtPosition(position) as String
        if (::productList.isInitialized) {
            when (item) {
                "All" -> {
                    adapter.submitList(productList)
                }

                "Physical" -> {
                    adapter.submitList(productList.filter { it.category == "physical" })
                }

                "Digital" -> {
                    adapter.submitList(productList.filter { it.category == "digital" })
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}