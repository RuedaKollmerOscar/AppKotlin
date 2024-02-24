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
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(), OnClickListener {

    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtPassword = view.findViewById(R.id.edtPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        btnRegister = view.findViewById(R.id.btnRegister)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                if (checkFields()) {
                    Snackbar.make(requireView(), "Por favor, completa todos los campos.", Snackbar.LENGTH_SHORT).show()
                } else {
                    auth.signInWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString())
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                val activity1: Intent = Intent(requireActivity(), Activity1::class.java)
                                startActivity(activity1)
                                requireActivity().finish()
                            } else {
                                val exception = task.exception
                                if (exception is FirebaseAuthException) {
                                    val errorMessage = when (exception.errorCode) {
                                        "ERROR_INVALID_EMAIL" -> "Correo electrónico inválido."
                                        "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta."
                                        "ERROR_USER_NOT_FOUND" -> "No hay ninguna cuenta asociada a este correo electrónico."
                                        "ERROR_USER_DISABLED" -> "Esta cuenta ha sido deshabilitada."
                                        else -> "Error al iniciar sesión. Por favor, inténtalo nuevamente."
                                    }
                                    Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show()
                                } else {
                                    Snackbar.make(requireView(), "Error al iniciar sesión. Por favor, inténtalo nuevamente.", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }
            R.id.btnRegister -> navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun checkFields(): Boolean {
        return edtEmail.text.toString().isEmpty() || edtPassword.text.toString().isEmpty()
    }
}
