package com.example.fetch.model

import kotlinx.serialization.Serializable

@Serializable
data class HiringData(
    val id: Int, val listId: Int, val name: String = ""
)