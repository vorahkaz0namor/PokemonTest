package com.ccink.model.model

data class BaseModel<T>(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: T? = null,
) : ResponseModel
