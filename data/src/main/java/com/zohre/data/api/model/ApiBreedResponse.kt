package com.zohre.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiBreedResponse(
    @SerializedName("message" ) val breedDto : BreedDto,
    @SerializedName("status"  ) val status  : String?
)
