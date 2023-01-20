package com.example.masir.services.http

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {




    @GET("users")
    suspend fun getAllUsers(
        @Query("page") page : Int = 1,
        @Query("perPage") perPage : Int = 30
    ) : List<User>


    @GET("users/{USERNAME}")
    suspend fun getSingleUser(
        @Path("USERNAME") userName : String
    ) : SingleUserObj


    @GET("users/{USERNAME}/followers")
    suspend fun getFollowersOfUser(
        @Path("USERNAME") userName : String,
        @Query("perPage") perPage : Int = 30,
        @Query("page") page : Int = 1
    ) : List<User>


    @GET("users/{USERNAME}/following")
    suspend fun getFollowingOfUser(
        @Path("USERNAME") userName : String,
        @Query("perPage") perPage : Int = 30,
        @Query("page") page : Int = 1
    ) : List<User>

    @GET("users/{USERNAME}/repos")
    suspend fun getRepoOfUser(
        @Path("USERNAME") userName : String,
        @Query("perPage") perPage : Int = 30,
        @Query("page") page : Int = 1
    ) : List<GitHubRepositoryObj>


    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query : String,
        @Query("perPage") perPage : Int = 100,
        @Query("page") page : Int = 1
    ) : SearchResultWidget

}