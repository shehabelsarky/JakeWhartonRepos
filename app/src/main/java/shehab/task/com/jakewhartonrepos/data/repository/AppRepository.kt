package shehab.task.com.jakewhartonrepos.data.repository


import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable
import retrofit2.Response
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.data.network.ApiError

interface AppRepository {

    var reposMutableLiveData : LiveData<List<ReposResponse>>

    fun getRepos(
        pageIndex: Int,
        pageSize: Int,
        success: (Response<List<ReposResponse>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun insertRepo(repo: ReposResponse): Disposable

}