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
import com.example.mynewsapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    lateinit var userViewModel: UserViewModel
    lateinit var roomUsername: String
    lateinit var roomPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding?.registration?.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Проверка верного логина и пароля, переход в сессию
        val adminLogin: String = "admin"
        val adminPassword: String = "admin"
        val userLogin = binding?.userLogin
        val userPassword = binding?.userPassword

        // Обработка события при нажатия на кнопку:
        binding?.userLogintoClick?.setOnClickListener{

            roomUsername = userLogin?.text.toString().trim()
            roomPassword = userPassword?.text.toString().trim()

            userViewModel.getLoginDetails(requireContext(), roomUsername, roomPassword)!!.observe(viewLifecycleOwner,
                {
                    if (userLogin?.text?.toString()?.trim().equals(adminLogin) && userPassword?.text?.toString()?.trim().equals(adminPassword))
                        view?.let { it1 ->
                            Navigation.findNavController(it1)
                                .navigate(R.id.action_loginFragment_to_newsApp)
                        }
                    else if (it == null){
                        Toast.makeText(context, "Введен неверный логин или пароль", Toast.LENGTH_LONG).show()
                        }
                    else if(it.Password != roomPassword) {
                        Toast.makeText(context, "Введен неверный пароль", Toast.LENGTH_LONG).show()
                        }
                    else {
                        view?.let { it1 ->
                            Navigation.findNavController(it1)
                                .navigate(R.id.action_loginFragment_to_newsApp)
                        }
                    }
                })
        }
        return binding?.root
    }
    override fun onDestroyView(){
        binding = null
        super.onDestroyView()
    }
}