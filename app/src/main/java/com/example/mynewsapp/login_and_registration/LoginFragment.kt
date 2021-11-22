package com.example.mynewsapp.login_and_registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import room.UserViewModel
import com.example.mynewsapp.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var roomUsername: String
    lateinit var roomPassword: String
    var emptyField: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        with(binding) {
                registration.setOnClickListener {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_loginFragment_to_registrationFragment)
                }

                // Обработка события при нажатия на кнопку:
                userLogintoClick.setOnClickListener {
                    // Проверка верного логина и пароля, переход в сессию
                    val adminLogin: String = "admin"
                    val adminPassword: String = "admin"
                    val userLogin = userLogin
                    val usernameTextInput = usernameTextInput
                    val userPassword = userPassword
                    val passwordTextInput = passwordTextInput
                    val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("newUser", Context.MODE_PRIVATE)


                    roomUsername = userLogin.text.toString().trim()
                    roomPassword = userPassword.text.toString().trim()

                    infoCheck(userLogin, usernameTextInput)
                    infoCheck(userPassword, passwordTextInput)

                    userViewModel.getLoginDetails(requireContext(), roomUsername, roomPassword)
                        ?.observe(viewLifecycleOwner,
                            {
                                if (userLogin.text?.toString()?.trim().equals(adminLogin) &&
                                    userPassword.text?.toString()?.trim().equals(adminPassword)
                                )
                                    view?.let { view ->
                                        Navigation.findNavController(view)
                                            .navigate(R.id.action_loginFragment_to_newsApp)
                                    }
                                else if (it == null) {
                                    usernameTextInput.error = "Введен неверный логин"
                                } else if (it.password != roomPassword) {
                                    passwordTextInput.error = "Введен неверный пароль"
                                } else {
                                    sharedPreferences.edit().apply() {
                                        putString("userLogin", userLogin.text.toString())
                                        putString("userPassword", userPassword.text.toString())
                                    }.apply()
                                    view?.let { view ->
                                        Navigation.findNavController(view)
                                            .navigate(R.id.action_loginFragment_to_newsApp)
                                    }
                                }
                            })
                }
            }
        return binding.root
    }

    fun userCheck(userCheckEdit: EditText) {
        emptyField = !userCheckEdit.text.toString().trim().isEmpty()
    }

    fun infoCheck(infoCheckEdit: EditText, userCheckInput: TextInputLayout): Boolean {
        var userOk: Boolean = false
        userCheck(infoCheckEdit)

        if (!emptyField) {
            userCheckInput.error = "Пустое поле"
        } else {
            userCheckInput.error = null
            userOk = true
        }
        return userOk
    }
}