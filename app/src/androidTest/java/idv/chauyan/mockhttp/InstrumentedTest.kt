package idv.chauyan.mockhttp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import idv.chauyan.mockhttp.repository.NetworkRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertNotNull

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    val appContext = InstrumentationRegistry.getTargetContext()
    val respository = NetworkRepository.getInstance(appContext)

    @Test
    fun useAppContext() {
        // Context of the app under test.

        assertEquals("idv.chauyan.mockhttp", appContext.packageName)
    }


    @Test
    fun getUsers_Success() {
        respository
                .getUsers(0, 100)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    assert(result.isNotEmpty())
                }, { error ->
                    error.printStackTrace()
                })
    }

    @Test
    fun getUserDetailInfo_Success() {
        val userName = "mojombo"

        respository
                .getUserDetail(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    assertNotNull(result)
                }, { error ->
                    error.printStackTrace()
                })
    }
}
