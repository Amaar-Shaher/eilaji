package com.amaar.eilaji

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amaar.eilaji.databinding.FragmentLogInBinding
import com.amaar.eilaji.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    var isSignedIn = false

    private val profilViewModel : UserProfileViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    val providers = arrayListOf(
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // action_logInFragment_to_homePageFragment
        // move isLogIn() to this fragment NOT ViewModel
//        if (isLogIn() == true){
//            val intent = Intent(this.requireActivity(), MainActivity2::class.java)
//            startActivity(intent)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.loginBtn.setOnClickListener {

    signInLauncher.launch(signInIntent)
//    val action = LogInFragmentDirections.actionLogInFragmentToHomePageFragment()
//    this.findNavController().navigate(action)

}

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.log_in -> {
                signInLauncher.launch(signInIntent)
            }
            R.id.log_out -> {
                signOut()
                isSignedIn = false
            }

        }

        return true
    }


    override fun onPrepareOptionsMenu(menu: Menu) {

        if (isSignedIn) {
            menu.findItem(R.id.log_in)?.isVisible = false
            menu.findItem(R.id.log_out)?.isVisible = true
        } else {
            menu.findItem(R.id.log_in)?.isVisible =  true
            menu.findItem(R.id.log_out)?.isVisible = false
        }

    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            isSignedIn = true
        }

    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
           // val user = FirebaseAuth.getInstance().currentUser
            val intent = Intent(this.requireActivity(), MainActivity2::class.java)
            startActivity(intent)
         //   val action = LogInFragmentDirections.actionLogInFragmentToHomePageFragment()
        //    this.findNavController().navigate(action)
        //    Navigation.findNavController(view).navigate(LogInFragmentDirections.actionLogInFragmentToHomePageFragment())
            //println(user?.email)
        } else {
            println("else")
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this.requireContext())
        val intent = Intent(this.requireActivity(), MainActivity::class.java)
        startActivity(intent)

    }

//    fun isLogIn():Boolean{
//        val currentUser = Firebase.auth.currentUser
//        if (currentUser != null)
//            return true
//        else return false
//    }
}