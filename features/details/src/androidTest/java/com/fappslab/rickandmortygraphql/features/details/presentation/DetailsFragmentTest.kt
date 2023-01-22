package com.fappslab.rickandmortygraphql.features.details.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.rickandmortygraphql.features.details.presentation.model.StatusType
import com.fappslab.rickandmortygraphql.features.details.stub.characterArgsStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class DetailsFragmentTest {

    @get:Rule
    val robot = DetailsFragmentRobot(args = characterArgsStub())

    @Test
    fun checkCharacterId_Should_text_view_has_exactly_character_id_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyCharacterId()
        }
    }

    @Test
    fun checkCharacterName_Should_text_view_has_exactly_character_name_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyCharacterName()
        }
    }

    @Test
    fun checkDetailsTitle_Should_text_view_has_exactly_details_title_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyDetailsTitle()
        }
    }

    @Test
    fun checkDetailsGenderTitle_Should_text_view_has_exactly_details_gender_title_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyDetailsGenderTitle()
        }
    }

    @Test
    fun checkDetailsGenderTitle_Should_text_view_has_exactly_details_gender_content_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyDetailsGenderContent()
        }
    }

    @Test
    fun checkDetailsGenderTitle_Should_text_view_has_exactly_details_species_title_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyDetailsSpeciesTitle()
        }
    }

    @Test
    fun checkDetailsGenderTitle_Should_text_view_has_exactly_details_species_content_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyDetailsSpeciesContent()
        }
    }

    @Test
    fun checkOriginTitle_Should_text_view_has_exactly_origin_title_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyOriginTitle()
        }
    }

    @Test
    fun checkDetailsGenderTitle_Should_text_view_has_exactly_origin_name_title_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyOriginNameTitle()
        }
    }

    @Test
    fun checkDetailsGenderTitle_Should_text_view_has_exactly_origin_name_content_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkTextViewHasExactlyOriginNameContent()
        }
    }

    @Test
    fun checkChipCharacterStatus_Should_chip_view_has_exactly_status_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkChipViewHasExactlyCharacterStatus()
        }
    }

    @Test
    fun checkImageStrokeColor_Should_image_view_has_exactly_stroke_color_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkImageStrokeColor(StatusType.Alive)
        }
    }

    @Test
    fun checkCharacterStatus_Should_chip_view_has_exactly_background_color_When_view_is_displayed() {
        robot.whenLaunch().thenCheck {
            checkChipHasExactlyBackgroundColor(StatusType.Alive)
        }
    }
}
