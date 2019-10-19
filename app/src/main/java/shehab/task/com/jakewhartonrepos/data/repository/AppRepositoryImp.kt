package shehab.task.com.jakewhartonrepos.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import shehab.task.com.jakewhartonrepos.data.db.AppDatabase
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.data.network.ApiDisposable
import shehab.task.com.jakewhartonrepos.data.network.ApiEndpointInterface
import shehab.task.com.jakewhartonrepos.data.network.ApiError


class AppRepositoryImp (
    var networkInterface: ApiEndpointInterface,
    val database: AppDatabase
) : AppRepository{


    private val TAG: String = AppRepositoryImp::class.java.simpleName
    private val disposables = CompositeDisposable()
   override var reposMutableLiveData : LiveData<List<ReposResponse>> = database.repoDao().getAllRepos()

    override fun getRepos(
        pageIndex: Int,
        pageSize: Int,
        success: (Response<List<ReposResponse>>) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {

        return networkInterface.getRepos(pageIndex, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<Response<List<ReposResponse>>>(
                    {
                        success(it)
                        val reposResponse = it.body() as List<ReposResponse>
                        (0 until reposResponse.size).forEach { i ->
                            disposables.add(insertRepo(reposResponse[i]))
                        }
                    },
                    {
                        failure(it)
                    }
                )
            )

    }



   override fun insertRepo(repo: ReposResponse) :Disposable {
        return Observable
            .fromCallable { database.repoDao().insertRepo(repo) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "repo is added: subscribe: $it")
            }
    }


}