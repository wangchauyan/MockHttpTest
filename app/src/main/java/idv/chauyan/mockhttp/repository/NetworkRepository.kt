package idv.chauyan.mockhttp.repository

import android.content.Context
import idv.chauyan.mockhttp.apis.GithubApiService
import idv.chauyan.mockhttp.model.User
import idv.chauyan.mockhttp.model.UserDetail
import io.reactivex.Observable


class RetrofitRepository(val apiService : GithubApiService) {
    /**
     * outside api - get user list
     */
    fun getUsers(since:Int, per_page:Int) : Observable<List<User>> {
        return apiService.users(since, per_page)
    }

    /**
     * outside api - get user detail information
     */
    fun getUserDetail(username: String) : Observable<UserDetail> {
        return apiService.userDetail(username)
    }
}

object NetworkRepository {

    fun getInstance(context: Context): RetrofitRepository {
        return RetrofitRepository(GithubApiService.create(false, context))
    }
}