package com.example.appkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.appkotlin.Activity1
import com.example.appkotlin.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment(), OnClickListener {

    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var edtConfirmPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        navController = findNavController()
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtPassword = view.findViewById(R.id.edtPassword)
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        btnRegister = view.findViewById(R.id.btnRegister)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegister-> {
                if (checkFields()) {
                    Snackbar.make(requireView(), "Por favor, completa todos los campos.", Snackbar.LENGTH_SHORT).show()
                } else if (!checkPassword()) {
                    Snackbar.make(requireView(), "Las contraseñas no coinciden.", Snackbar.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(edtEmail.text.toString(), edtConfirmPassword.text.toString())
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                val activity1: Intent = Intent(requireActivity(), Activity1::class.java)
                                startActivity(activity1)
                                requireActivity().finish()
                            } else {
                                val exception = task.exception
                                if (exception != null) {
                                    val errorMessage = when (exception) {
                                        is FirebaseAuthWeakPasswordException -> "La contraseña es débil. Debe tener al menos 8 caracteres."
                                        is FirebaseAuthInvalidCredentialsException -> "Credenciales inválidas. Revisa el formato del correo electrónico."
                                        is FirebaseAuthUserCollisionException -> "Esta cuenta ya está en uso."
                                        else -> "Error al crear la cuenta. Por favor, inténtalo nuevamente."
                                    }
                                    Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }
            R.id.btnLogin -> navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    private fun checkFields(): Boolean {
        return edtEmail.text.toString().isEmpty() || edtPassword.text.toString().isEmpty() ||
                edtConfirmPassword.text.toString().isEmpty()
    }
    private fun checkPassword(): Boolean {
        return edtPassword.text.toString().equals(edtConfirmPassword.text.toString())
    }
}
