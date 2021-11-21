package com.example.mynewsapp.login_and_registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import room.UserViewModel
import com.example.mynewsapp.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    lateinit var binding:FragmentRegistrationBinding
    lateinit var userViewModel: UserViewModel
    lateinit var roomUsername: String
    lateinit var roomPassword: String
    var emptyField: Boolean = false
    var emptyRules: Boolean = false
    var shortWord: Boolean = false
    var dogEmail: Boolean = false
    var variousPassword: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.logon.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.apply{
            val userLogin = userLogin
            val userEmail = userEmail
            val userPassword = userPassword
            val userRepeatPassword = userRepeatPassword
            val switchAgree = switchAgree

            userRegistrationClick.setOnClickListener {

                registrationCheck(userLogin, userEmail, userPassword, userRepeatPassword, switchAgree)

                if(!emptyField) {
                    Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                } else if(!emptyRules){
                    Toast.makeText(context,"Вы не согласились с правилами",Toast.LENGTH_LONG).show()
                } else if(!shortWord){
                    Toast.makeText(context,"Длина Логина и Пароля должны быть не менее 3х символов",Toast.LENGTH_LONG).show()
                } else if(!dogEmail){
                    Toast.makeText(context,"Не верный e-mail (не хватает @)",Toast.LENGTH_LONG).show()
                } else if (!variousPassword){
                    Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(context,"Регистрация прошла успешно!",Toast.LENGTH_LONG).show()
                    roomUsername = userLogin.text.toString().trim()
                    roomPassword = userPassword.text.toString().trim()
                    userViewModel.insert(requireContext(), roomUsername, roomPassword)
                }
            }
        }
        return  binding.root
    }

    fun registrationCheck(userLogin: EditText, userEmail: EditText,userPassword: EditText, userRepeatPassword: EditText, switchAgree: Switch){
        emptyField = !(userLogin.text?.toString()?.trim() == "" ||
                userEmail.text?.toString()?.trim() == "" ||
                userPassword.text?.toString()?.trim() == "" ||
                userRepeatPassword.text?.toString()?.trim() == "")

        emptyRules = switchAgree.isChecked.equals(false) != true

        shortWord = !(userLogin.text?.toString()?.length!! < 3 ||
                userPassword.text?.toString()?.length!! < 3)

        dogEmail = userEmail.text?.toString()?.contains("@", ignoreCase = true) != false

        variousPassword = userPassword.text?.toString() == userRepeatPassword.text?.toString()
    }
}