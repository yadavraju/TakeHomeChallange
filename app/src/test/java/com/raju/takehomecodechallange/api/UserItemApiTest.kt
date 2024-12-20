package com.raju.takehomecodechallange.api

import com.raju.takehomecodechallange.base.BaseServiceTest
import com.raju.takehomecodechallange.network.BASE_URL
import com.raju.takehomecodechallange.network.api.UserItemApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Test

const val STATUS_200 = "mock-responses/get-item-list.json"

class UserItemApiTest : BaseServiceTest<UserItemApi>(UserItemApi::class) {

    override val baseUrl: String
        get() = BASE_URL

    @Test
    fun requestLiveGetCountries() = runTest {
        serviceLive.getItems().also {
            Assert.assertNotNull(it)
            Assert.assertEquals(1000, it.size)
            Assert.assertEquals("", it[0].name)
        }
    }

    @Test
    fun requestMockGetCountry() = runTest {
        enqueueResponse(STATUS_200)
        serviceMock.getItems().also {
            val request: RecordedRequest = mockWebServer.takeRequest()
            Assert.assertEquals("GET", request.method)
            Assert.assertEquals(
                "/hiring.json",
                request.path
            )
            Assert.assertEquals(1000, it.size)
        }
    }
}