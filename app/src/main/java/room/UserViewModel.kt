package room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var liveDataUser: LiveData<User>? = null

    fun insert(context: Context, id: Int, username: String, password: String) {
        UserRepository.insertData(context, id, username, password)
    }

    fun getLoginDetails(context: Context, username: String, password: String) : LiveData<User>? {
        liveDataUser = UserRepository.getLoginDetails(context, username, password)
        return liveDataUser
    }
}