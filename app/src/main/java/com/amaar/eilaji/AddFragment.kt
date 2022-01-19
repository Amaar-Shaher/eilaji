package com.amaar.eilaji

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil.setContentView

import androidx.fragment.app.viewModels
import androidx.media.MediaBrowserServiceCompat.RESULT_OK

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amaar.eilaji.databinding.FragmentAddBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private var photoFile: File? = null
private val PERMISSION_REQUEST_CODE: Int = 101

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: MyViewModel by viewModels()


    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private fun getAddDataUser(): MedicationInfo {
        val descraption = binding.descriptionEditView.text.toString()
        val farstDay = binding.firstDayView.text.toString()
        val lastDay = binding.lastDayView.text.toString()
        val manyTime = binding.timeView.text.toString()

        return MedicationInfo("", descraption, farstDay, lastDay, manyTime)
    }


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

            viewmodelvar = viewModel
        }

        binding.saveBtnView.setOnClickListener {

            var userAddet = getAddDataUser()

            viewModel.addNew(userAddet, photoFile!!.toUri())

            Navigation.findNavController(view)
                .navigate(AddFragmentDirections.actionAddFragmentToHomePageFragment())
        }
        binding.takePhotoBtn.setOnClickListener {

            camera()


        }


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
            }
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }


    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory =
            this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val takenImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
            binding.photoTextView.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this.requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                // startCamera()
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                this.requireActivity().finish()
            }
        }
    }
}
