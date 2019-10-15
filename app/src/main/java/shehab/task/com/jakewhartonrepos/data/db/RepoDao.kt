package shehab.task.com.jakewhartonrepos.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepo(repo: ReposResponse):Long


   /* @Query("SELECT * FROM ReposResponse")
    fun getAllRepos(): Maybe<List<ReposResponse>>*/

    @Query("SELECT * FROM ReposResponse")
    fun getAllRepos(): LiveData<List<ReposResponse>>
}