package com.amaar.eilaji

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.amaar.eilaji.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private val profileDataBase = Firebase.firestore.collection("users")
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutBtn.setOnClickListener {
            signOut()
        }
        binding.editProfileImageView.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_profileFragment_to_editProfileFragment)

            var user = setDataUser()
            saveDataUser(user)
        }
    }
    private fun setDataUser():UserInfo{
        val userId = Firebase.auth.currentUser!!.uid
        val nameUser = binding.patientNameTextView.text.toString()
        val numberUser = binding.patientMobileNumberTextView.text.toString()
        val imageUser = binding.personImageView.toString()

        return UserInfo(userId,nameUser,numberUser,imageUser)
    }

    private fun saveDataUser(userInfo : UserInfo){
        profileDataBase.document(userInfo.idUser).set(userInfo)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(this.requireContext(), "saved", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun getDataUser(userInfo: UserInfo){
        profileDataBase.document(userInfo.idUser).get().addOnCompleteListener {

        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this.requireContext())
        val intent = Intent(this.requireActivity(), MainActivity::class.java)
        startActivity(intent)

    }

}