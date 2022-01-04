package com.amaar.eilaji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaar.eilaji.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {
    private val viewModel: MyViewModel by activityViewModels ()

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val adapter = ItemListAdapter()
       binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)
        binding?.recyclerView?.adapter = adapter
        adapter.submitList(MedicationList)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.addIcon?.setOnClickListener { view:View ->
            val action = HomePageFragmentDirections.actionHomePageFragmentToAddFragment(
             //   getString(R.string.add_fragment_title)

            )
            this.findNavController().navigate(action)
        }
    }
}