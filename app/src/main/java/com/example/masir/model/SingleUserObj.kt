package com.example.masir.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity
data class SingleUserObj(
    @PrimaryKey
    @ColumnInfo
    val id: Int,
    @ColumnInfo(defaultValue = "")
    val avatar_url: String="",
    @ColumnInfo(defaultValue = "")
    val bio: String="",
    @ColumnInfo(defaultValue = "")
    val blog: String="",
    @ColumnInfo(defaultValue = "")
    val company: String="",
    @ColumnInfo(defaultValue = "")
    val created_at: String="",
    @ColumnInfo(defaultValue = "")
    val email: String ="",
    @ColumnInfo(defaultValue = "")
    val events_url: String="",
    @ColumnInfo
    val followers: Int,
    @ColumnInfo(defaultValue = "")
    val followers_url: String="",
    @ColumnInfo
    val following: Int,
    @ColumnInfo(defaultValue = "")
    val following_url: String="",
    @ColumnInfo(defaultValue = "")
    val gists_url: String="",
    @ColumnInfo(defaultValue = "")
    val gravatar_id: String="",
    @ColumnInfo(defaultValue = "")
    val hireable: String="",
    @ColumnInfo(defaultValue = "")
    val html_url: String="",
    @ColumnInfo(defaultValue = "")
    val location: String="",
    @ColumnInfo(defaultValue = "")
    val login: String="",
    @ColumnInfo(defaultValue = "")
    val name: String="",
    @ColumnInfo(defaultValue = "")
    val node_id: String="",
    @ColumnInfo(defaultValue = "")
    val organizations_url: String="",
    @ColumnInfo
    val public_gists: Int,
    @ColumnInfo
    val public_repos: Int,
    @ColumnInfo(defaultValue = "")
    val received_events_url: String="",
    @ColumnInfo(defaultValue = "")
    val repos_url: String="",
    @ColumnInfo
    val site_admin: Boolean,
    @ColumnInfo(defaultValue = "")
    val starred_url: String="",
    @ColumnInfo(defaultValue = "")
    val subscriptions_url: String="",
    @ColumnInfo(defaultValue = "")
    val twitter_username: String="",
    @ColumnInfo(defaultValue = "")
    val type: String="",
    @ColumnInfo(defaultValue = "")
    val updated_at: String="",
    @ColumnInfo(defaultValue = "")
    val url: String=""
) : java.io.Serializable {


    fun following() = "$following"
    fun followers() = "$followers"
    fun publicRepos() = "$public_repos"


}