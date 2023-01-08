package com.fappslab.rickandmortygraphql.hubsrc.utils

import androidx.annotation.VisibleForTesting
import com.fappslab.rickandmortygraphql.arch.jsonhandle.readFromJSONToModel
import com.fappslab.rickandmortygraphql.arch.jsonhandle.readFromJSONToString

private const val SUCCESS_RESPONSE = "success_response.json"
private const val FAILURE_RESPONSE = "failure_response.json"

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
object StubResponse {
    val expectedSuccessDataResponse = readFromJSONToModel<QueryResponse>(SUCCESS_RESPONSE).data
    val expectedSuccessBodyResponse = readFromJSONToString(SUCCESS_RESPONSE)
    val expectedFailureBodyResponse = readFromJSONToString(FAILURE_RESPONSE)
}
