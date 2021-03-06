package com.example.mynewsapp.login_and_registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mynewsapp.R
import com.example.mynewsapp.room.UserViewModel
import com.example.mynewsapp.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputLayout

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

        binding.apply {
            nextButton.setOnClickListener {
                val usernameEdit = usernameEditText
                val usernameTextInput = usernameTextInput
                val emailEdit = emailEditText
                val emailTextInput = emailTextInput
                val passwordEdit = passwordEditText
                val passwordTextInput = passwordTextInput
                val repeatPasswordEdit = repeatPasswordEditText
                val repeatPasswordTextInput = repeatPasswordTextInput
                val switchAgree = switchAgree
                roomUsername = usernameEdit.text.toString().trim()
                roomPassword = passwordEdit.text.toString().trim()
                val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("newUser", Context.MODE_PRIVATE)

                userViewModel.getLoginDetails(requireContext(), roomUsername, roomPassword)
                    ?.observe(viewLifecycleOwner
                    ) { user ->
                        infoCheck(usernameEdit, usernameTextInput)
                        emailCheck(emailEdit, emailTextInput)
                        infoCheck(passwordEdit, passwordTextInput)
                        repeatCheck(repeatPasswordEdit, repeatPasswordTextInput, passwordEdit)
                        switchCheck(switchAgree)

                        if (user != null) {
                            if (user.username == roomUsername) {
                                usernameTextInput.error = "?????????? ???????? ?????? ????????????????????"
                            }
                        } else if (
                            infoCheck(usernameEdit, usernameTextInput) &&
                            infoCheck(passwordEdit, passwordTextInput) &&
                            emailCheck(emailEdit, emailTextInput) &&
                            repeatCheck(
                                repeatPasswordEdit,
                                repeatPasswordTextInput,
                                passwordEdit
                            ) &&
                            switchCheck(switchAgree)
                        ) {
                            Toast.makeText(
                                context,
                                "?????????????????????? ???????????? ??????????????!",
                                Toast.LENGTH_LONG
                            ).show()
                            roomUsername = usernameEdit.text.toString().trim()
                            roomPassword = passwordEdit.text.toString().trim()
                            userViewModel.insert(requireContext(), id, roomUsername, roomPassword)
                            sharedPreferences.edit()
                                .apply() { putString("userEmail", emailEdit.text.toString()) }
                                .apply()
                            Navigation.findNavController(it)
                                .navigate(R.id.action_registrationFragment_to_loginFragment)
                        }
                    }
            }
        }
        return binding.root
    }

    private fun userCheck(userCheckEdit: EditText) {
        shortWord = userCheckEdit.text?.toString()?.length!! >= 3
        emptyField = !userCheckEdit.text.toString().trim().isEmpty()
        dogEmail = userCheckEdit.text?.toString()?.contains("@", ignoreCase = true) != false
    }

    private fun infoCheck(infoCheckEdit: EditText, userCheckInput: TextInputLayout): Boolean {
        var userOk: Boolean = false
        userCheck(infoCheckEdit)
        if (!emptyField) {
            userCheckInput.error = "???????????? ????????"
        } else if (!shortWord) {
            userCheckInput.error = "??????-???? ???????????????? ???????????? 3??"
        } else {
            userCheckInput.error = null
            userOk = true
        }
        return userOk
    }

    private fun emailCheck(emailCheckEdit: EditText, emailCheckInput: TextInputLayout): Boolean {
        var emailOk: Boolean = false
        userCheck(emailCheckEdit)
        if (!emptyField) {
            emailCheckInput.error = "???????????? ????????"
        } else if (!shortWord) {
            emailCheckInput.error = "??????-???? ???????????????? ???????????? 3??"
        } else if (!dogEmail) {
            emailCheckInput.error = "???? ???????????? e-mail (???? ?????????????? @)"
        } else {
            emailCheckInput.error = null
            emailOk = true
        }
        return emailOk
    }

    private fun repeatCheck(repeatCheckEdit: EditText, repeatCheckInput: TextInputLayout, passwordCheckEdit: EditText): Boolean {
        var repeatOk: Boolean = false
        userCheck(repeatCheckEdit)
        variousPassword = repeatCheckEdit.text?.toString() == passwordCheckEdit.text?.toString()
        if (!emptyField) {
            repeatCheckInput.error = "???????????? ????????"
        } else if (!shortWord) {
            repeatCheckInput.error = "??????-???? ???????????????? ???????????? 3??"
        } else if (!variousPassword) {
            repeatCheckInput.error = "???????????? ???? ??????????????????"
        } else {
            repeatCheckInput.error = null
            repeatOk = true
        }
        return repeatOk
    }

    private fun switchCheck(switchAgree: SwitchCompat): Boolean {
        var switchOk: Boolean = false
        emptyRules = switchAgree.isChecked.equals(false) != true
        if (!emptyRules) {
            Toast.makeText(context, "???? ???? ?????????????????????? ?? ??????????????????", Toast.LENGTH_LONG).show()
        } else {
            switchOk = true
        }
        return switchOk
    }
}