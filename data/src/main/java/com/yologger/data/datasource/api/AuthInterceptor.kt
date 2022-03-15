package com.yologger.data.datasource.api

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.yologger.common.Constant
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenSuccessResponse
import com.yologger.data.datasource.pref.Session
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.domain.usecase.auth.verify_access_token.ReissueTokenResponse
import okhttp3.*
import org.json.JSONObject

class AuthInterceptor constructor(
    private val sessionStore: SessionStore,
    private val gson: Gson
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        sessionStore.getSession()?.let {

            val accessToken = it.accessToken
            val originalResponse = chain.proceed(requestWithAccessToken(chain.request(), accessToken))

            if (originalResponse.isSuccessful) {
                // Original request succeeds.
                return originalResponse
            } else {
                // Original request fails.
                // Reissue token
                val memberId = sessionStore.getSession()!!.memberId
                val refreshToken = sessionStore.getSession()!!.refreshToken
                val body = JSONObject()
                body.put("member_id", memberId.toString())
                body.put("refresh_token", refreshToken)
                val requestBody = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    body.toString()
                )
                val request = Request.Builder()
                    .url("${Constant.BASE_URL}/auth/reissueToken")
                    .post(requestBody)
                    .build()
                val okHttpClient = OkHttpClient()

                // Try reissuing token.
                val reissueTokenResponse = okHttpClient.newCall(request).execute()
                if (reissueTokenResponse.isSuccessful) {
                    // Reissuing token succeeds.
                    val reissueTokenSuccessResponse = gson.fromJson<ReissueTokenSuccessResponse>(reissueTokenResponse?.body()?.string()!!, ReissueTokenSuccessResponse::class.java)
                    val session = Session(
                        memberId = reissueTokenSuccessResponse.memberId,
                        email = reissueTokenSuccessResponse.email,
                        name = reissueTokenSuccessResponse.name,
                        nickname = reissueTokenSuccessResponse.nickname,
                        accessToken = reissueTokenSuccessResponse.accessToken,
                        refreshToken = reissueTokenSuccessResponse.refreshToken,
                        avatarUrl = reissueTokenSuccessResponse.avatarUrl
                    )
                    sessionStore.putSession(session)

                    originalResponse.close()
                    reissueTokenResponse.close()

                    // Retry original request with new access token
                    val newAccessToken = sessionStore.getSession()!!.accessToken
                    val retryResponse = chain.proceed(requestWithAccessToken(chain.request(), newAccessToken))
                    return retryResponse
                } else {
                    // Reissuing token fails.
                    // Clear session storage
                    sessionStore.deleteSession()
                    originalResponse?.close()
                    return reissueTokenResponse
                }
            }
        } ?: run {
            // When there's no access token in local cache.
            val jsonString = "{\"code\":\"LOCAL001\",\"message\":\"Empty Access Token\",\"status\":\"400\"}"
            val responseBody = ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)

            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(400)
                .body(responseBody)
                .build()
        }
    }

    private fun requestWithAccessToken(request: Request, accessToken: String): Request {
        return request
            .newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}