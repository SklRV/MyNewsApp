package room

import androidx.room.*
import androidx.lifecycle.LiveData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getUserDetails(username: String?) : LiveData<User>
}