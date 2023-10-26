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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firstdueapplication.R
import com.firstdueapplication.adapter.AnimalAdapter
import com.firstdueapplication.adapter.ProductAdapter
import com.firstdueapplication.api.NetworkResult
import com.firstdueapplication.databinding.FragmentAnimalBinding
import com.firstdueapplication.databinding.FragmentDashboardBinding
import com.firstdueapplication.models.Photo
import com.firstdueapplication.models.Product
import com.firstdueapplication.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnimalFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAnimalBinding? = null
    private val productViewModel by viewModels<ProductViewModel>()

    private val binding get() = _binding!!

    lateinit var animalAdapter: AnimalAdapter
    lateinit var animalList: List<Photo>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAnimalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animalAdapter = AnimalAdapter(::onAnimalClicked)

        binding.rvAnimalList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvAnimalList.adapter = animalAdapter
        productViewModel.getAnimalList()

        InitializeCategory()
        handleObserver()

    }

    private fun InitializeCategory() {
        val spinAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.animal_list, android.R.layout.simple_spinner_item
        )
        spinAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinType.adapter = spinAdapter
        binding.spinType.setOnItemSelectedListener(this);
    }

    private fun handleObserver() {
        productViewModel.animalMutableList.observe(viewLifecycleOwner, Observer {
            binding.pbProgressBar.isVisible = false
            when (it) {
                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {
                    binding.pbProgressBar.isVisible = true
                }

                is NetworkResult.Success -> {
                    animalList = it.data!!
                    animalAdapter.submitList(it.data)
                }
            }
        })
    }

    private fun onAnimalClicked(animal: Photo) {
        Toast.makeText(context, "${animal.title} clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        (parent?.getChildAt(0) as TextView).setTextColor(Color.BLACK)
        val item = parent?.getItemAtPosition(position) as String
        if (::animalList.isInitialized) {
            when (item) {
                "All" -> {
                    animalAdapter.submitList(animalList)
                }

                "Odd" -> {
                    animalAdapter.submitList(animalList.filter { it.id % 2 != 0 })
                }

                "Even" -> {
                    animalAdapter.submitList(animalList.filter { it.id % 2 == 0 })
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}