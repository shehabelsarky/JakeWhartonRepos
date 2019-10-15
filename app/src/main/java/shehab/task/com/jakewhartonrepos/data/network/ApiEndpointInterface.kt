package shehab.task.com.jakewhartonrepos.data.network


import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*
import shehab.task.com.jakewhartonrepos.data.model.ReposResponse
import shehab.task.com.jakewhartonrepos.utils.PAGE_NUMBER
import shehab.task.com.jakewhartonrepos.utils.PAGE_SIZE

interface ApiEndpointInterface {

    @GET(ApiUrls.REPOS)
    fun getRepos(
        @Query(PAGE_NUMBER) pageIndex: Int,
        @Query(PAGE_SIZE) pageSize: Int
    ): Observable<Response<List<ReposResponse>>>

}
