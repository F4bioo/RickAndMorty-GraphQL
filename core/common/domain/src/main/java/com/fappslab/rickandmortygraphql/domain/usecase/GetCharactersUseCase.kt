package com.fappslab.rickandmortygraphql.domain.usecase

import com.fappslab.rickandmortygraphql.domain.model.Characters
import kotlinx.coroutines.flow.Flow

fun interface GetCharactersUseCase : (Int) -> Flow<Characters>
