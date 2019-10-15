package shehab.task.com.jakewhartonrepos.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import shehab.task.com.jakewhartonrepos.di.scopes.ReposScope

@Module
class HomeFragmentModule {

    @ReposScope
    @Provides
    fun providesLayoutManager(context: Context) : LinearLayoutManager {
        return LinearLayoutManager(context)
    }


}