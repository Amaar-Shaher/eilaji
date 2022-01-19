package com.amaar.eilaji

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amaar.eilaji.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayUserInfo()

        binding!!.logoutBtn.setOnClickListener {
            signOut()
        }
        binding!!.editProfileImageView.setOnClickListener {

            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }


    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this.requireContext())
        val intent = Intent(this.requireActivity(), MainActivity::class.java)
        startActivity(intent)

    }

    private fun displayUserInfo () {
        viewModel.name.observe(viewLifecycleOwner,{binding!!.patientNameTextView.setText(it)})

        viewModel.mobileNumber.observe(viewLifecycleOwner,{binding!!.patientMobileNumberTextView.setText(it)})

        binding!!.patientEmailTextView.setText(viewModel.getUserEmail())

    }

}