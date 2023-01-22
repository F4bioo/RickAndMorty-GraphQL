package com.fappslab.rickandmortygraphql.features.details.presentation

import com.fappslab.rickandmortygraphql.features.details.R
import com.fappslab.rickandmortygraphql.features.details.presentation.model.StatusType
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.RobotCheck
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.assertions.checkChipHasExactlyBackgroundColor
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.assertions.checkShapeableImageViewStrokeColor
import com.fappslab.rickandmortygraphql.libraries.arch.testing.robot.assertions.checkTextViewHasExactlyString

internal class DetailsFragmentRobotCheck : RobotCheck<DetailsFragmentRobotCheck> {

    fun checkTextViewHasExactlyCharacterId() {
        checkTextViewHasExactlyString(
            R.id.text_id,
            expectedString = "#1"
        )
    }

    fun checkTextViewHasExactlyCharacterName() {
        checkTextViewHasExactlyString(
            R.id.text_name,
            expectedString = "Rick Sanchez"
        )
    }

    fun checkTextViewHasExactlyDetailsTitle() {
        checkTextViewHasExactlyString(
            R.id.text_details_title,
            expectedString = "DETAILS"
        )
    }

    fun checkTextViewHasExactlyDetailsGenderTitle() {
        checkTextViewHasExactlyString(
            R.id.text_details_gender_title,
            expectedString = "Gender"
        )
    }

    fun checkTextViewHasExactlyDetailsGenderContent() {
        checkTextViewHasExactlyString(
            R.id.text_details_gender_content,
            expectedString = "Male"
        )
    }

    fun checkTextViewHasExactlyDetailsSpeciesTitle() {
        checkTextViewHasExactlyString(
            R.id.text_details_species_title,
            expectedString = "Species"
        )
    }

    fun checkTextViewHasExactlyDetailsSpeciesContent() {
        checkTextViewHasExactlyString(
            R.id.text_details_species_content,
            expectedString = "Human"
        )
    }

    fun checkTextViewHasExactlyOriginTitle() {
        checkTextViewHasExactlyString(
            R.id.text_origin_title,
            expectedString = "ORIGIN"
        )
    }


    fun checkTextViewHasExactlyOriginNameTitle() {
        checkTextViewHasExactlyString(
            R.id.text_origin_name_title,
            expectedString = "Name"
        )
    }

    fun checkTextViewHasExactlyOriginNameContent() {
        checkTextViewHasExactlyString(
            R.id.text_origin_name_content,
            expectedString = "Earth (C-137)"
        )
    }

    fun checkChipViewHasExactlyCharacterStatus() {
        checkTextViewHasExactlyString(
            R.id.chip_status,
            expectedString = "Alive"
        )
    }

    fun checkImageStrokeColor(statusType: StatusType) {
        checkShapeableImageViewStrokeColor(
            R.id.image_character,
            expectedColorRes = statusType.colorRes
        )
    }

    fun checkChipHasExactlyBackgroundColor(statusType: StatusType) {
        checkChipHasExactlyBackgroundColor(
            R.id.chip_status,
            expectedColorRes = statusType.colorRes
        )
    }
}
