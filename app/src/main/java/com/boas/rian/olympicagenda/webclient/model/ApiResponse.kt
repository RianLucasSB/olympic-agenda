package com.boas.rian.olympicagenda.webclient.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(val data: List<T>, val meta: Meta) {
}

data class Meta(
    @SerializedName("current_page")
    var currentPage: Int? = null,
    @SerializedName("from")
    var from: Int? = null,
    @SerializedName("last_page")
    var lastPage: Int? = null,
    @SerializedName("path")
    var path: String? = null,
    @SerializedName("per_page")
    var perPage: Int? = null,
    @SerializedName("to")
    var to: Int? = null,
    @SerializedName("total")
    var total: Int? = null
) {

}
