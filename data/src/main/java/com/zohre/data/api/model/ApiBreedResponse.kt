package com.zohre.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiBreedResponse(
    @SerializedName("message" ) val breedDto : Map<String, List<String?>>,
    @SerializedName("status"  ) val status  : String?
)
