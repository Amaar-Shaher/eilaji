package com.amaar.eilaji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amaar.eilaji.databinding.FragmentAddBinding


class AddFragment : Fragment() {

//private lateinit var binding: FragmentAddBinding
//
//
//
//
//  private val viewModel : Viewmodel by viewModels()
//
//
//  override fun onCreateView(
//      inflater: LayoutInflater,
//      container: ViewGroup?,
//      savedInstanceState: Bundle?
//  ): View? {
//      binding = FragmentAddBinding.inflate(inflater, container, false)
//      return binding.root
//  }
//
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//      binding.apply {
//          lifecycleOwner=viewLifecycleOwner
//          viewmodelvar=viewModel
//      }
//      binding?.saveBotton?.setOnClickListener { view:View ->
//          Navigation.findNavController(view).navigate(AddFragmentDirections.actionAddFragmentToTodoFragment())
//          viewModel.AddToList()
//      }
//  }
}