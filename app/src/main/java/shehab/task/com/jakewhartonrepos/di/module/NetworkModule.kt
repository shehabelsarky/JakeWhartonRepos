package shehab.task.com.jakewhartonrepos.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import dagger.Provides
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shehab.task.com.jakewhartonrepos.data.db.AppDatabase
import shehab.task.com.jakewhartonrepos.data.network.ApiEndpointInterface
import shehab.task.com.jakewhartonrepos.data.network.ApiUrls
import shehab.task.com.jakewhartonrepos.data.repository.AppRepository
import shehab.task.com.jakewhartonrepos.data.repository.AppRepositoryImp
import shehab.task.com.jakewhartonrepos.utils.ACCESS_TOKEN
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {


    @Provides
    @Singleton
    fun networkInterface(retrofit: Retrofit): ApiEndpointInterface {
        return retrofit.create(ApiEndpointInterface::class.java)
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }


    @Provides
    @Singleton
    fun retrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {

        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
        return retrofitBuilder.build()
    }


    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun dispatcher(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 3
        return dispatcher
    }

    @Provides
    @Singleton
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request() //original request
                val newRequest = request.newBuilder() //modified request
                        //add your github access token for the project to work
                    .addHeader("Authorization", "token " + ACCESS_TOKEN)
                    .build()

                chain.proceed(newRequest) //response of the request
            }
            .dispatcher(dispatcher)
            .addInterceptor(loggingInterceptor)
            .build()
    }



    @Provides
    fun provideRepository(networkInterface: ApiEndpointInterface,database: AppDatabase): AppRepository {
        return AppRepositoryImp(networkInterface,database)
    }

}
