package shehab.task.com.jakewhartonrepos.core


import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import shehab.task.com.jakewhartonrepos.di.component.DaggerAppComponent

class AppController : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }
}