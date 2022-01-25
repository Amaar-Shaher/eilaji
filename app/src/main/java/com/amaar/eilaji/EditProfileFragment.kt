package com.amaar.eilaji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amaar.eilaji.databinding.FragmentEditProfileBinding
import com.amaar.eilaji.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditProfileFragment : Fragment() {

    private val profileDataBase = Firebase.firestore.collection("users")
    private lateinit var binding: FragmentEditProfileBinding


    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayUserInfo()

        binding.saveDataUserBtn.setOnClickListener {
            var user = setDataUser()
            saveDataUser(user)
        }
    }

    private fun setDataUser(): UserInfo {
        val userId = Firebase.auth.currentUser!!.uid
        val nameUser = binding.patientNameEditText.text.toString()
        val numberUser = binding.patientMobileNumberEditText.text.toString()
        val imageUser = binding.personImageView.toString()

        return UserInfo(userId, nameUser, numberUser, imageUser)
    }

    fun saveDataUser(userInfo: UserInfo) {
        setDataUser()
        profileDataBase.document(userInfo.idUser).set(userInfo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)

                }
            }
    }

    private fun displayUserInfo() {
        viewModel.name.observe(viewLifecycleOwner, { binding!!.patientNameEditText.setText(it) })

        viewModel.mobileNumber.observe(viewLifecycleOwner,
            { binding!!.patientMobileNumberEditText.setText(it) })

    }
}