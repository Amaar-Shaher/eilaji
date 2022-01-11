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
private lateinit var photoFile: File
private val PERMISSION_REQUEST_CODE: Int = 101

class AddFragment : Fragment() {

    private val dataAddDataBase = Firebase.firestore.collection("data medication")
    private lateinit var binding: FragmentAddBinding

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private fun getAddDataUser():MedicationInfo{
        val userId = Firebase.auth.currentUser!!.uid
        val takePhoto = binding.takePhotoTextView.toString()
        val descraption = binding.descriptionEditView.text.toString()
        val farstDay = binding.firstDayView.text.toString()
        val lastDay = binding.lastDayView.text.toString()
        val manyTime = binding.timeView.text.toString()

        return MedicationInfo(userId,takePhoto,descraption,farstDay,lastDay,manyTime)
    }

    private fun saveAddDataUser(medicationInfo : MedicationInfo){
        dataAddDataBase.document(medicationInfo.idDataUser).set(medicationInfo)
            .addOnCompleteListener{task ->
               if (task.isSuccessful) {
                   Toast.makeText(this.requireContext(), "OK", Toast.LENGTH_SHORT).show()
               }
            }

    }



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

//            binding.descriptionEditView.setText(description)
//            binding.firstDayView.setText(firstDay)
//            binding.lastDayView.setText(lastDay)
//            binding.timeView.setText(manyTime)

                // lifecycleOwner=viewLifecycleOwner
                viewmodelvar = viewModel
            }

            binding?.saveBtnView?.setOnClickListener { view: View ->

                var userAddet = getAddDataUser()
                saveAddDataUser(userAddet)
                Navigation.findNavController(view)
                    .navigate(AddFragmentDirections.actionAddFragmentToHomePageFragment())
                viewModel.AddToList()
            }
            binding.takePhotoBtn.setOnClickListener {

              camera()


            }


        }
     fun camera(){
        Toast.makeText(this.requireContext(), "camera", Toast.LENGTH_SHORT).show()
        if (allPermissionsGranted()) {
           val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(takePictureIntent)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(this.requireContext(), "com.amaar.eilaji.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.requireActivity().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)

            } else {
                // Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
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
        //Use `getExternalFilesDir` on Context to access package-specific directories.
        val storageDirectory = this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//          val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.photoTextView.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


      //  if (checkPersmission())  else requestPermission()
    }





//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            PERMISSION_REQUEST_CODE -> {
//
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                  //  takePicture()
//
//                } else {
//                  //  Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//                }
//                return
//            }
//
//            else -> {
//
//            }
//        }
//    }
//
//
//
//    private fun checkPersmission(): Boolean {
//        return (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA) ==
//              PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.requireContext(),
//            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
//    }
//
//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(READ_EXTERNAL_STORAGE, CAMERA), PERMISSION_REQUEST_CODE)
//    }



    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this.requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
               // startCamera()
            } else {
                Toast.makeText(this.requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                this.requireActivity().finish()
            }
        }
    }
    }
