package com.example.mynewsapp.room

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository {
    companion object {

        var userDatabase: UserDatabase? = null
        var userTableModel: LiveData<User>? = null

        private fun initializeDB(context: Context) : UserDatabase {
            return UserDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, id: Int, username: String, password: String) {
            userDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val loginDetails = User(id, username, password)
                userDatabase!!.userDao().insert(loginDetails)
            }
        }

        fun getLoginDetails(context: Context, username: String, password: String) : LiveData<User>? {
            userDatabase = initializeDB(context)
            userTableModel = userDatabase!!.userDao().getUserDetails(username)
            return userTableModel
        }
    }
}