package com.firstdueapplication.api

import com.firstdueapplication.utils.Helper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var productApi: ProductApi

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        productApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductApi::class.java)

    }

    @Test
    fun testGetProductEmptyList() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getProductList()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() != null)
    }

    @Test
    fun testGetProduct_returnProduct() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFileResources("/product.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getProductList()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body()!!.products.isEmpty())
        Assert.assertEquals(4, response.body()!!.products.size)
    }

    @Test
    fun testGetProduct_returnError() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getProductList()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}