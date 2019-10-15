package shehab.task.com.jakewhartonrepos.di.builder

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import shehab.task.com.jakewhartonrepos.di.builder.AppViewModelBuilder
import shehab.task.com.jakewhartonrepos.di.builder.ViewModelFactory

@Module(
    includes = [
        (AppViewModelBuilder::class)
    ]
)
abstract class ViewModelFactoryBuilder {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}