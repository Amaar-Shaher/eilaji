package com.amaar.eilaji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.amaar.eilaji.databinding.FragmentEditProfileBinding
import com.amaar.eilaji.databinding.FragmentProfileBinding


class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

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
        binding.saveDataUserBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_editProfileFragment_to_profileFragment)

        }
    }
}