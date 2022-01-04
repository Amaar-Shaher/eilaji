package com.amaar.eilaji

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.amaar.eilaji.databinding.FragmentEditBinding


class EditFragment : Fragment() {


    private lateinit var binding: FragmentEditBinding


    private val viewModel: MyViewModel by viewModels()
    var index = 0
    var takePhoto = ""
    var diescription = ""
    var firstDay = ""
    var lastDay = ""
    var manyTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            index = it?.getInt("index")!!
            diescription = it.getString("diescription")!!
            firstDay = it.getString("firstDay")!!
            lastDay = it.getString("lastDay")!!
            manyTime = it.getString("manyTime")!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("TAG", "onViewCreated: diescription $diescription")
        Log.d("TAG", "onViewCreated: index $index")
            binding.editDescriptionEditView.setText(diescription)
            binding.editFirstDay.setText(firstDay)
            binding.editLastDay.setText(lastDay)
            binding.editManyTime.setText(manyTime)



        binding?.editBtn?.setOnClickListener { view: View ->
            diescription = binding.editDescriptionEditView.text.toString()
            firstDay = binding.editFirstDay.text.toString()
            lastDay = binding.editLastDay.text.toString()
            manyTime = binding.editManyTime.text.toString()
            DataSource().updateTask(
                index,
                MedicationInfo(takePhoto, diescription, firstDay, lastDay, manyTime)
            )
            view.findNavController().navigate(R.id.action_editFragment_to_homePageFragment)

        }

    }
}