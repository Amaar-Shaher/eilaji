package com.amaar.eilaji

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil.setContentView

import androidx.fragment.app.viewModels

import androidx.navigation.Navigation
import com.amaar.eilaji.databinding.FragmentAddBinding
import java.io.File

//private const val FILE_NAME = "photo.jpg"
//private const val REQUEST_CODE = 42
//private lateinit var photoFile: File

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        save.setOnClickListener {
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            photoFile = getPhotoFile(FILE_NAME)
//
//        }
   // }
        private val viewModel: MyViewModel by viewModels()

        var index = 0
        var description = ""
        var firstDay = 0
        var lastDay = 0
        var manyTime = 0


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
//       / arguments.let  {
//  /          index = it?.getInt("index")!!
//            description = it.getString("description")!!
//            firstDay = it.getInt("firstDay")!!
//            lastDay = it.getInt("lastDay")!!
//            manyTime = it.getInt("manyTime")!!
//
//
//
//
//            binding.descriptionEditView.setText(description)
//            binding.firstDayView.setText(firstDay)
//            binding.lastDayView.setText(lastDay)
//            binding.timeView.setText(manyTime)

                // lifecycleOwner=viewLifecycleOwner
                viewmodelvar = viewModel
            }

            binding?.saveBtnView?.setOnClickListener { view: View ->
                Navigation.findNavController(view)
                    .navigate(AddFragmentDirections.actionAddFragmentToHomePageFragment())
                viewModel.AddToList()
            }
        }
    }
