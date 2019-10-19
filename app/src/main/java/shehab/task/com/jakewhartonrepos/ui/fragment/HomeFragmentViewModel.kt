package shehab.task.com.jakewhartonrepos.ui.fragment

import androidx.lifecycle.LiveData
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.domain.HomeFragmentUseCase
import shehab.task.com.jakewhartonrepos.ui.fragment.base.BaseViewModel
import shehab.task.com.jakewhartonrepos.utils.SnackbarMessage
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private var homeFragmentUseCase: HomeFragmentUseCase
) : BaseViewModel() {

    private val mSnackbarText = SnackbarMessage()


    init {
        mSnackbarText.value = homeFragmentUseCase.errorMessage
    }

    fun getRepos(pageIndex: Int, pageSize: Int) {
        compositeDisposable.add(homeFragmentUseCase.getRepos(pageIndex, pageSize))
    }

    fun getReposLiveData(): LiveData<List<ReposResponse>> = homeFragmentUseCase.getReposLiveData()

    internal fun getSnackbarMessage(): SnackbarMessage {
        return mSnackbarText
    }
}