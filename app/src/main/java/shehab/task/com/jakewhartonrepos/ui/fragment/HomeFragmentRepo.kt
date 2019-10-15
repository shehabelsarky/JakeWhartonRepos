package shehab.task.com.jakewhartonrepos.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import shehab.task.com.jakewhartonrepos.data.db.AppDatabase
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.data.network.ApiEndpointInterface
import javax.inject.Inject


class HomeFragmentRepo @Inject constructor(
    var networkInterface: ApiEndpointInterface,
    val database: AppDatabase
) {

    private val TAG: String = HomeFragmentRepo::class.java.simpleName

    private val disposables = CompositeDisposable()
    var reposMutableLiveData : LiveData<List<ReposResponse>> = database.repoDao().getAllRepos()

    fun getRepos(pageIndex: Int, pageSize: Int) {
        networkInterface.getRepos(pageIndex, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<List<ReposResponse>>> {
                override fun onComplete() {
                    Log.d(TAG, "Completed")
                }

                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(response: Response<List<ReposResponse>>) {
                    val reposResponse = response.body() as List<ReposResponse>

                    (0 until reposResponse.size).forEach { i ->
                        disposables.add(insertRepo(reposResponse[i]))
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "Error")
                    e.printStackTrace()
                }
            })
    }


    fun insertRepo(repo: ReposResponse) :Disposable {
        return Observable
            .fromCallable { database.repoDao().insertRepo(repo) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "repo is added: subscribe: $it")
            }
    }


    fun clearDisposables() {
        disposables.dispose()
    }

}