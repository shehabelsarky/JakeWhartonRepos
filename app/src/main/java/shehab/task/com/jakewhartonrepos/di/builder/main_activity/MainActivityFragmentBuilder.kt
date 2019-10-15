package shehab.task.com.jakewhartonrepos.di.builder.main_activity

import shehab.task.com.jakewhartonrepos.di.module.HomeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import shehab.task.com.jakewhartonrepos.di.scopes.ReposScope
import shehab.task.com.jakewhartonrepos.ui.fragment.HomeFragment

@Module
abstract class MainActivityFragmentBuilder{


    @ReposScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun provideMyOrdersFragment(): HomeFragment

}