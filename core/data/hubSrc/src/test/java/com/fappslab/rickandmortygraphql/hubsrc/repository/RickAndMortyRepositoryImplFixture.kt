package com.fappslab.rickandmortygraphql.hubsrc.repository

import stub.QueryResponse
import stub.readFromJSONToModel
import stub.readFromJSONToString

private const val SUCCESS_RESPONSE = "characters_success_response.json"
private const val FAILURE_RESPONSE = "failure_response.json"
private const val FAILURE_EMPTY_RESPONSE = "{}"

fun expectedSuccessDataResponse() = readFromJSONToModel<QueryResponse>(SUCCESS_RESPONSE).data
fun expectedSuccessBodyResponse() = readFromJSONToString(SUCCESS_RESPONSE)
fun expectedFailureBodyResponse() = readFromJSONToString(FAILURE_RESPONSE)
fun expectedFailureEmptyBodyResponse() = FAILURE_EMPTY_RESPONSE