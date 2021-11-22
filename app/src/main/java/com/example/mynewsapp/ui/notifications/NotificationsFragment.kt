package com.example.mynewsapp.ui.notifications

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var binding:FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("newUser", Context.MODE_PRIVATE)

        val itUseraname = sharedPreferences.getString("userLogin", null)
        val itEmail = sharedPreferences.getString("userEmail", null)
        val itPassword = sharedPreferences.getString("userPassword", null)

        binding.yourUseraname.text = itUseraname
        binding.yourEmail.text = itEmail
        binding.yourPassword.text = itPassword

        binding.buttonClear.setOnClickListener{
            clear()
            Navigation.findNavController(it).navigate(R.id.action_navigation_notifications_to_loginFragment)
        }

        return binding.root
    }

    fun clear() {
        val prefs = context?.getSharedPreferences("newUser", 0)
        val editor: SharedPreferences.Editor? =  prefs?.edit()
        editor?.clear()?.apply()
        editor?.putBoolean("clear", true)
        editor?.apply()
    }
}