package shehab.task.com.jakewhartonrepos.di.builder


import shehab.task.com.jakewhartonrepos.di.builder.main_activity.MainActivityFragmentBuilder
import shehab.task.com.jakewhartonrepos.di.builder.view_models.HomeFragmentViewModelBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector
import shehab.task.com.jakewhartonrepos.ui.activity.MainActivity

@Module
abstract class ActivityBuilder {




    @ContributesAndroidInjector(modules = [MainActivityFragmentBuilder::class, HomeFragmentViewModelBuilder::class])
    abstract fun bindMainActivity(): MainActivity


}