package com.fappslab.rickandmortygraphql.core.common.domain.usecase

import com.fappslab.rickandmortygraphql.core.common.domain.model.Characters
import com.fappslab.rickandmortygraphql.core.common.domain.model.Filter
import kotlinx.coroutines.flow.Flow

fun interface GetCharactersUseCase : (Filter) -> Flow<Characters>
