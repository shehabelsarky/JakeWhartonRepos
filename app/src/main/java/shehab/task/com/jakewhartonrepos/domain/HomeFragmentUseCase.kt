package shehab.task.com.jakewhartonrepos.domain

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.data.repository.AppRepository
import javax.inject.Inject

class HomeFragmentUseCase @Inject constructor(private var homeFragmentRepositoryImp: AppRepository) {

    val TAG = HomeFragmentUseCase::class.java.simpleName
    var errorMessage : Int? = null


    fun getRepos(pageIndex: Int,pageSize: Int) : Disposable {
        return homeFragmentRepositoryImp.getRepos(pageIndex,pageSize,{
            Log.d(TAG, "Success Response of getting repos")

        },{

            errorMessage = it.getApiErrorMessage(it.status)
            Log.e(TAG, "getRepos.error() called with $it")
        })
    }

    fun getReposLiveData() : LiveData<List<ReposResponse>> = homeFragmentRepositoryImp.reposMutableLiveData


}