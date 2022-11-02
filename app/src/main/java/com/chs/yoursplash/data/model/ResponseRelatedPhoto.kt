package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRelatedPhoto(
    @SerialName("total")
    val total: Int,
    @SerialName("results")
    val results: List<ResponsePhoto>
)

//@Serializable
//abstract class BasicResponse<T>(
//
//    open val total: Int,
//
//    open val results: List<T>
//)
//
//@Serializable
//data class A(
//    @SerialName("total")
//    override val total: Int,
//    @SerialName("results")
//    override val results: List<ResponsePhoto>
//): BasicResponse<ResponsePhoto>(total, results)