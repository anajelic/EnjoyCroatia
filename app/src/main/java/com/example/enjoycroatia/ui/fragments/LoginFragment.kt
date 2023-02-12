package com.example.enjoycroatia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.LoginRequest
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var mAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener {
            when {
                etEmail.text.isNullOrBlank() -> {
                    Snackbar.make(it, "Please, enter email!", Snackbar.LENGTH_SHORT).show()
                }
                etPassword.text.isNullOrBlank() -> {
                    Snackbar.make(it, "Please, enter password!", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    val request = LoginRequest(etEmail.text.toString(), etPassword.text.toString())
                    login(request)
                }
            }
        }

        tvRegister.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }

    private fun login(request: LoginRequest) {
        view?.let { Snackbar.make(it, "Authenticating...", Snackbar.LENGTH_SHORT).show() }
        mAuth.signInWithEmailAndPassword(request.email, request.password).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_loginFragment_to_mainActivity2)
            } else {
                view?.let { view ->
                    Snackbar.make(
                        view,
                        "Something is wrong! Try again!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }


}