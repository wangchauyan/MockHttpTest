package idv.chauyan.mockhttp.apis

import android.content.Context
import idv.chauyan.mockhttp.model.User
import idv.chauyan.mockhttp.model.UserDetail
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApiService {

    /**
     * get user list from github
     */
    @GET("users")
    fun users(@Query("since") since:Int, @Query("per_page") per_page:Int): Observable<List<User>>

    /**
     * query user detail information from github by using an exact user name
     */
    @GET("users/{username}")
    fun userDetail(@Path("username") userName:String): Observable<UserDetail>

    companion object Factory {
        fun create(debug: Boolean, context: Context): GithubApiService {

            val retrofitBuilder = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")

            if (debug) {
                val httpClient = OkHttpClient.Builder().addInterceptor(FakeInterceptor(context)).build()
                retrofitBuilder.client(httpClient)
            }

            return retrofitBuilder.build().create(GithubApiService::class.java)
        }
    }
}