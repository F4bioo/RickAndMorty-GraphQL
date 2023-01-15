package com.fappslab.rickandmortygraphql.domain.usecase

import kotlinx.coroutines.flow.Flow

fun interface GetFilterUseCase : (String) -> Flow<String?>
