package com.firstdueapplication.api

import com.firstdueapplication.utils.Helper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AnimalApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var animalApi: ProductApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        animalApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductApi::class.java)

    }

    @Test
    fun testGetAnimalEmptyList() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = animalApi.getAnimalList()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() != null)
    }

    @Test
    fun testGetAnimal_returnAnimal() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFileResources("/animal.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = animalApi.getAnimalList()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body()!!.photos.isEmpty())
        Assert.assertEquals(5, response.body()!!.photos.size)
    }

    @Test
    fun testGetAnimal_returnError() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(402)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = animalApi.getAnimalList()
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(402, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}