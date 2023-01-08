package stub

import com.fappslab.rickandmortygraphql.domain.model.Characters

fun characterStub() = Characters(
    characters = emptyList(),
    nextPage = 2, totalPages = 42
)
