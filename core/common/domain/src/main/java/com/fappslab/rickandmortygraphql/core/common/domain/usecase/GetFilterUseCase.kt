package com.fappslab.rickandmortygraphql.core.common.domain.usecase

import kotlinx.coroutines.flow.Flow

fun interface GetFilterUseCase : (String) -> Flow<String?>
