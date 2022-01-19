package com.amaar.eilaji

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.amaar.eilaji.databinding.FragmentEditBinding
import com.bumptech.glide.Glide
import java.io.File


private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private var photoFile: File? = null


class EditFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


    private lateinit var binding: FragmentEditBinding


    private val viewModel: MyViewModel by viewModels()
    var index = ""
    var takePhoto = ""
    var diescription = ""
    var firstDay = ""
    var lastDay = ""
    var manyTime = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            index = it?.getString("index")!!
            diescription = it.getString("diescription")!!
            firstDay = it.getString("firstDay")!!
            lastDay = it.getString("lastDay")!!
            manyTime = it.getString("manyTime")!!
            takePhoto = it.getString("takePhoto")!!

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
            Glide.with(requireContext()).load(takePhoto.toUri())
            .into(binding.photoTextView)





        binding?.editBtn?.setOnClickListener { view: View ->
            diescription = binding.editDescriptionEditView.text.toString()
            firstDay = binding.editFirstDay.text.toString()
            lastDay = binding.editLastDay.text.toString()
            manyTime = binding.editManyTime.text.toString()


            viewModel.editmed(MedicationInfo(takePhoto,  diescription , firstDay ,lastDay ,manyTime ,index),
                photoFile?.toUri())
            Log.e("TAG", "onViewCreated:${takePhoto} " )
//            DataSource().updateTask(
//                index,
//                MedicationInfo(takePhoto, diescription, firstDay, lastDay, manyTime)
//            )
            view.findNavController().navigate(R.id.action_editFragment_to_homePageFragment)

        }

        binding.editPhotoBtn.setOnClickListener {
            camera()
        }

    }

    private fun allPermissionsGranted() = EditFragment.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this.requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    private fun getPhotoFile(fileName: String): File {
        //Use `getExternalFilesDir` on Context to access package-specific directories.
        val storageDirectory = this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }


    fun camera() {
        Toast.makeText(this.requireContext(), "camera", Toast.LENGTH_SHORT).show()
        if (allPermissionsGranted()) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(takePictureIntent)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(
                this.requireContext(),
                "com.amaar.eilaji.fileprovider",
                photoFile!!
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.requireActivity().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)

            } else {
                // Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                EditFragment.REQUIRED_PERMISSIONS,
                EditFragment.REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//          val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
            binding.photoTextView.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}