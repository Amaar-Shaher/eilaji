package com.amaar.eilaji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import androidx.fragment.app.viewModels

import androidx.navigation.Navigation
import com.amaar.eilaji.databinding.FragmentAddBinding


class AddFragment : Fragment() {

  private lateinit var binding: FragmentAddBinding




    private val viewModel : MyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
           // lifecycleOwner=viewLifecycleOwner
            viewmodelvar=viewModel
        }
        binding?.saveBtnView?.setOnClickListener { view:View ->
            Navigation.findNavController(view).navigate(AddFragmentDirections.actionAddFragmentToHomePageFragment())
            viewModel.AddToList()
        }
    }
}