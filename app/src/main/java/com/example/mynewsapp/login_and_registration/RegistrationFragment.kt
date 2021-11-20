package com.example.mynewsapp.login_and_registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import room.UserViewModel
import com.example.mynewsapp.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null
    lateinit var userViewModel: UserViewModel
    lateinit var roomUsername: String
    lateinit var roomPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding?.logon?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding?.apply{
            val userLogin = userLogin
            val userEmail = userEmail
            val userPassword = userPassword
            val userRepeatPassword = userRepeatPassword
            val switchAgree = switchAgree

            userRegistrationClick.setOnClickListener {

                // Если какое-то из полей пустое, оповестим об этом пользователя
                if(userLogin.text?.toString()?.trim().equals("") ||
                    userEmail.text?.toString()?.trim().equals("") ||
                    userPassword.text?.toString()?.trim().equals("") ||
                    userRepeatPassword.text?.toString()?.trim().equals("") )
                    Toast.makeText(context,"Не все поля заполнены",Toast.LENGTH_LONG).show()

                // Если не согласились с правилами:
                else if(switchAgree.isChecked.equals(false) == true)
                    Toast.makeText(context,"Вы не согласились с правилами",Toast.LENGTH_LONG).show()

                // Не разрешать логин, пароль меньше 3х символов
                else if(userLogin.text?.toString()?.length!! < 3 ||
                    userPassword.text?.toString()?.length!! < 3)
                    Toast.makeText(context,"Длина Логина и Пароля должны быть не менее 3х символов",Toast.LENGTH_LONG).show()

                // Проверяем, правильно ли написали емейл
                else if(userEmail.text?.toString()?.contains("@", ignoreCase = true) == false)
                    Toast.makeText(context,"Не верный e-mail (не хватает @)",Toast.LENGTH_LONG).show()

                // Если пароли не совпадают - оповещаем:
                else if (userPassword.text?.toString() != userRepeatPassword.text?.toString())
                    Toast.makeText(context,"Пароли не совпадают",Toast.LENGTH_LONG).show()

                else {
                    roomUsername = userLogin.text.toString().trim()
                    roomPassword = userPassword.text.toString().trim()
                    userViewModel.insert(requireContext(), roomUsername, roomPassword)
                    Toast.makeText(context,"Регистрация прошла успешно!",Toast.LENGTH_LONG).show()
                }
            }
        }
        return  binding?.root
    }
    override fun onDestroyView(){
        binding = null
        super.onDestroyView()
    }
}