package shehab.task.com.jakewhartonrepos.di.builder


import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import shehab.task.com.jakewhartonrepos.di.qualifier.ViewModelKey
import shehab.task.com.jakewhartonrepos.ui.fragment.HomeFragmentViewModel

@Module
abstract class AppViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun bindMyOrdersViewModel(myOrdersViewModel: HomeFragmentViewModel): ViewModel
}
