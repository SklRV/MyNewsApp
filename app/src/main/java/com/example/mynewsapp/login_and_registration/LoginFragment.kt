package com.example.mynewsapp.login_and_registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding?.registration?.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        // Проверка перехода в сессию при верном логине и пароле
        val adminLogin: String = "admin"
        val adminPassword: String = "admin"
        val userLogin = binding?.userLogin
        val userPassword = binding?.userPassword
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("newUser", Context.MODE_PRIVATE)

        // Обработка события при нажатия на кнопку:
        binding?.userLogintoClick?.setOnClickListener{

            // Если пользователь уже зарегестрирован:
            if (userLogin?.text?.toString()?.trim().equals(sharedPreferences.getString("userLogin", null)) &&
                userPassword?.text?.toString()?.trim().equals(sharedPreferences.getString("userPassword", null)) )
                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_newsApp)

            // Ввели параметры Админа
            else if (userLogin?.text?.toString()?.trim().equals(adminLogin) &&
                userPassword?.text?.toString()?.trim().equals(adminPassword) )

            // Переход на страницу Session:
                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_newsApp)
            else {
                Toast.makeText(context, "Введен неверный логин или пароль", Toast.LENGTH_LONG).show()
            }
        }
        return binding?.root
    }
    override fun onDestroyView(){
        binding = null
        super.onDestroyView()
    }
}