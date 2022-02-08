package com.example.mynewsapp.login_and_registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import com.example.mynewsapp.room.UserViewModel
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

            userLogintoClick.setOnClickListener {
                val adminLogin: String = "admin"
                val adminPassword: String = "admin"
                val userLogin = userLogin.text.toString().trim()
                val usernameTextInput = usernameTextInput
                val userPassword = userPassword.text.toString().trim()
                val passwordTextInput = passwordTextInput
                val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("newUser", Context.MODE_PRIVATE)

                roomUsername = userLogin
                roomPassword = userPassword

                infoCheck(userLogin, usernameTextInput)
                infoCheck(userPassword, passwordTextInput)

                userViewModel.getLoginDetails(requireContext(), roomUsername, roomPassword)
                    ?.observe(viewLifecycleOwner
                    ) {
                        if (userLogin == adminLogin &&
                            userPassword == adminPassword
                        ) {
                            view?.let { view ->
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_loginFragment_to_newsApp)
                            }
                        } else if (it == null) {
                            usernameTextInput.error = "Введен неверный логин"
                        } else if (it.password != roomPassword) {
                            passwordTextInput.error = "Введен неверный пароль"
                        } else {
                            sharedPreferences.edit().apply() {
                                putString("userLogin", userLogin)
                                putString("userPassword", userPassword)
                            }.apply()
                            view?.let { view ->
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_loginFragment_to_newsApp)
                            }
                        }
                    }
            }
        }
        return binding.root
    }

    private fun userCheck(userCheckEdit: String) {
        emptyField = userCheckEdit.isNotEmpty()
    }

    private fun infoCheck(infoCheckEdit: String, userCheckInput: TextInputLayout): Boolean {
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