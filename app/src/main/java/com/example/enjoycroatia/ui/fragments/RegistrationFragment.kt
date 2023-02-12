package com.example.enjoycroatia.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.LoginRequest
import com.example.enjoycroatia.data.User
import com.example.enjoycroatia.util.FirebaseStorageManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {
            val request = LoginRequest(etEmail.text.toString(), etPassword.text.toString())
            register(request, etName.text.toString())
        }

        tvBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }


    private fun register(request: LoginRequest, name: String) {
        view?.let { Snackbar.make(it, "Registering...", Snackbar.LENGTH_SHORT).show() }
        mAuth.createUserWithEmailAndPassword(request.email, request.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    FirebaseStorageManager.updateUser(User(request.email, name))
                    mAuth.signInWithEmailAndPassword(request.email, request.password)
                    findNavController().navigate(R.id.action_registrationFragment_to_mainActivity2)
                } else {
                    view?.let { view ->
                        Snackbar.make(
                            view,
                            "Something is wrong! Try again!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

            }.addOnFailureListener { e ->
                Log.w("Registration", "Error in registration", e)
            }
    }


}