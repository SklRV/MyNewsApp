package com.example.mynewsapp.login_and_registration

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import room.UserViewModel
import com.example.mynewsapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var roomUsername: String
    lateinit var roomPassword: String

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

            // Проверка верного логина и пароля, переход в сессию
            val adminLogin: String = "admin"
            val adminPassword: String = "admin"
            val userLogin = userLogin
            val userPassword = userPassword
            val passwordEditText = passwordEditText
            val passwordTextInput = passwordTextInput

            // Обработка события при нажатия на кнопку:
            userLogintoClick.setOnClickListener {

                roomUsername = userLogin.text.toString().trim()
                roomPassword = userPassword.text.toString().trim()

                userViewModel.getLoginDetails(requireContext(), roomUsername, roomPassword)
                    ?.observe(viewLifecycleOwner,
                        {
                            if (userLogin.text?.toString()?.trim().equals(adminLogin) &&
                                userPassword.text?.toString()?.trim().equals(adminPassword))
                                view?.let { view ->
                                    Navigation.findNavController(view)
                                        .navigate(R.id.action_loginFragment_to_newsApp)
                                }
                            else if (it == null) {
                                Toast.makeText(context,"Введен неверный логин или пароль",Toast.LENGTH_LONG).show()
                            } else if (it.password != roomPassword) {
                                Toast.makeText(context, "Введен неверный пароль", Toast.LENGTH_LONG).show()
                            } else {
                                view?.let { view ->
                                    Navigation.findNavController(view)
                                        .navigate(R.id.action_loginFragment_to_newsApp)
                                }
                            }
                        })

                if (!isPasswordValid(passwordEditText.text!!)) {
                    passwordTextInput.error = "Давай пароль поболее 3х символов"
                } else {
                    // Clear the error.
                    passwordTextInput.error = null
                }

            }
        }
        return binding.root
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 3
    }
}