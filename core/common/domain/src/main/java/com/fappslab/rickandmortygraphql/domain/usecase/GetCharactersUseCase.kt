package com.fappslab.rickandmortygraphql.domain.usecase

import com.fappslab.rickandmortygraphql.domain.model.Character
import kotlinx.coroutines.flow.Flow

fun interface GetCharactersUseCase : (Int) -> Flow<List<Character>>
