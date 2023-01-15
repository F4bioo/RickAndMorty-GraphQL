package com.fappslab.rickandmortygraphql.filter.presentation.model

import com.fappslab.rickandmortygraphql.domain.model.FilterType
import com.fappslab.rickandmortygraphql.domain.model.KeyType
import com.fappslab.rickandmortygraphql.filter.R
import kotlin.collections.Map.Entry

internal object FilterMapper {

    private val filterNameToIdMap = mapOf(
        KeyType.KeyStatus to mapOf(
            FilterType.StatusAlive.value to R.id.radio_status_alive,
            FilterType.StatusDead.value to R.id.radio_status_dead,
            FilterType.StatusUnknown.value to R.id.radio_status_unknown,
        ),
        KeyType.KeyGender to mapOf(
            FilterType.GenderMale.value to R.id.radio_gender_male,
            FilterType.GenderFemale.value to R.id.radio_gender_female,
            FilterType.Genderless.value to R.id.radio_genderless,
            FilterType.GenderUnknown.value to R.id.radio_gender_unknown,
        ),
        KeyType.KeySpecies to mapOf(
            FilterType.SpeciesHuman.value to R.id.radio_species_human,
            FilterType.SpeciesAlien.value to R.id.radio_species_alien,
            FilterType.SpeciesRobot.value to R.id.radio_species_robot,
            FilterType.SpeciesMythological.value to R.id.radio_species_mythological,
            FilterType.SpeciesHumanoid.value to R.id.radio_species_humanoid,
            FilterType.SpeciesPoopybutthole.value to R.id.radio_species_poopybutthole,
            FilterType.SpeciesAnimal.value to R.id.radio_species_animal,
            FilterType.SpeciesDisease.value to R.id.radio_species_disease,
            FilterType.SpeciesUnknown.value to R.id.radio_species_unknown
        )
    )

    private fun Entry<KeyType, Map<String, Int>>.mapKeyTypeToId(): Pair<KeyType, Map<Int, String>> =
        key to value.map { (key, value) -> value to key }.toMap()

    private val keyTypeToFilterNameMap = filterNameToIdMap.map { it.mapKeyTypeToId() }.toMap()

    fun getId(keyType: KeyType, filterName: String?) = filterNameToIdMap[keyType]?.get(filterName)
    fun getFilterName(keyType: KeyType, id: Int) = keyTypeToFilterNameMap[keyType]?.get(id)
}
