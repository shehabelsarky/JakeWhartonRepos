package shehab.task.com.jakewhartonrepos.di.component

import android.app.Application
import shehab.task.com.jakewhartonrepos.di.module.ContextModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import shehab.task.com.jakewhartonrepos.core.AppController
import shehab.task.com.jakewhartonrepos.di.builder.ActivityBuilder
import shehab.task.com.jakewhartonrepos.di.builder.ViewModelFactoryBuilder
import shehab.task.com.jakewhartonrepos.di.module.DatabaseModule
import shehab.task.com.jakewhartonrepos.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ActivityBuilder::class,
        ContextModule::class,
        ViewModelFactoryBuilder::class,
        DatabaseModule::class
    ]
)
interface AppComponent : AndroidInjector<AppController> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}