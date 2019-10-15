package shehab.task.com.jakewhartonrepos.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse


@Database(entities = [ReposResponse::class] , version = AppDatabase.VERSION , exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    companion object {
        const val DB_NAME = "JAKE_WHARTON_REPOS"
        const val VERSION = 1
    }

    abstract fun repoDao(): RepoDao
}