package com.zohre.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiBreedImagesResponse(
    @SerializedName("message" ) val images : List<String>,
    @SerializedName("status"  ) val status  : String?
)
