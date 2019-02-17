package idv.chauyan.mockhttp.apis

import android.content.Context
import android.support.annotation.RawRes
import idv.chauyan.mockhttp.BuildConfig
import okhttp3.*
import java.io.InputStream

class FakeInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response

        if (BuildConfig.DEBUG) {
            // get request path
            val path = chain.request().url().encodedPath()

            // load mock data from raw folder
            val mockData = context.readRaw(context.resources
                    .getIdentifier("raw/mock_".plus(path), null, context.packageName))

            response = Response.Builder().code(200)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), mockData.toByteArray()))
                    .addHeader("content-type", "application/json")
                    .build()
        }
        else {
            response = chain.proceed(chain.request())
        }

        return response
    }

}

/**
 * get raw resources
 */
fun Context.getRawInput(@RawRes resourceId: Int): InputStream {
    return resources.openRawResource(resourceId)
}

fun Context.readRaw(@RawRes resourceId: Int): String {
    return getRawInput(resourceId).bufferedReader(Charsets.UTF_8).use { it.readText() }
}
