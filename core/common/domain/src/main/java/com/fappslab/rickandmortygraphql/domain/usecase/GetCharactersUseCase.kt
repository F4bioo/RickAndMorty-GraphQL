package com.fappslab.rickandmortygraphql.domain.usecase

import com.fappslab.rickandmortygraphql.domain.model.Characters
import com.fappslab.rickandmortygraphql.domain.model.Filter
import kotlinx.coroutines.flow.Flow

fun interface GetCharactersUseCase : (Filter) -> Flow<Characters>
