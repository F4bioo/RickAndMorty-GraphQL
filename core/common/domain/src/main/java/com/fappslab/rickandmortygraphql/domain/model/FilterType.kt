package com.fappslab.rickandmortygraphql.domain.model

enum class FilterType(val value: String) {
    StatusAlive(value = "alive"),
    StatusDead(value = "dead"),
    StatusUnknown(value = "unknown"),

    GenderMale(value = "male"),
    GenderFemale(value = "female"),
    Genderless(value = "genderless"),
    GenderUnknown(value = "unknown"),

    SpeciesHuman(value = "human"),
    SpeciesAlien(value = "alien"),
    SpeciesRobot(value = "robot"),
    SpeciesMythological(value = "mythological"),
    SpeciesHumanoid(value = "humanoid"),
    SpeciesPoopybutthole(value = "poopybutthole"),
    SpeciesAnimal(value = "animal"),
    SpeciesDisease(value = "disease"),
    SpeciesUnknown(value = "unknown")
}
