package com.stepstone.stepper.internal.feedback

import android.content.res.Resources
import android.widget.TextView
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.stepstone.stepper.R
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.test.assertion.StepperFeedbackTypeCompositeAssert.Companion.assertThat
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class StepperFeedbackTypeFactoryTest {

    val mockTextView: TextView = mock {}

    val mockResources: Resources = mock {}

    val mockStepperLayout: StepperLayout = mock {
        on { findViewById(R.id.ms_stepTabsProgressMessage) } doReturn mockTextView
        on { resources } doReturn mockResources
    }

    @Test
    fun `Should create empty composite feedback for empty feedback mask`() {
        //given
        val feedbackMask = 0

        //when
        val createdType = StepperFeedbackTypeFactory.createType(feedbackMask, mockStepperLayout)

        //then
        assertCreatedTypeIsComposite(createdType)
        assertThat(createdType as StepperFeedbackTypeComposite)
                .hasNoChildComponents()
    }

    @Test
    fun `Should create empty composite feedback for StepperFeedbackType = NONE mask`() {
        //given
        val feedbackMask = StepperFeedbackType.NONE

        //when
        val createdType = StepperFeedbackTypeFactory.createType(feedbackMask, mockStepperLayout)

        //then
        assertCreatedTypeIsComposite(createdType)
        assertThat(createdType as StepperFeedbackTypeComposite)
                .hasNoChildComponents()
    }

    @Test
    fun `Created composite feedback type should contain TabsStepperFeedbackType when StepperFeedbackType = TABS mask provided`() {
        //given
        val feedbackMask = StepperFeedbackType.TABS

        //when
        val createdType = StepperFeedbackTypeFactory.createType(feedbackMask, mockStepperLayout)

        //then
        assertCreatedTypeIsComposite(createdType)
        assertThat(createdType as StepperFeedbackTypeComposite)
                .hasXChildComponents(1)
                .hasChildComponentOf(TabsStepperFeedbackType::class.java)
    }

    @Test
    fun `Created composite feedback type should contain ContentStepperFeedbackType when StepperFeedbackType = CONTENT mask provided`() {
        //given
        val feedbackMask = StepperFeedbackType.CONTENT

        //when
        val createdType = StepperFeedbackTypeFactory.createType(feedbackMask, mockStepperLayout)

        //then
        assertCreatedTypeIsComposite(createdType)
        assertThat(createdType as StepperFeedbackTypeComposite)
                .hasXChildComponents(1)
                .hasChildComponentOf(ContentStepperFeedbackType::class.java)
    }

    @Test
    fun `Created composite feedback type should contain DisabledBottomNavigationStepperFeedbackType when StepperFeedbackType = DISABLED_BOTTOM_NAVIGATION mask provided`() {
        //given
        val feedbackMask = StepperFeedbackType.DISABLED_BOTTOM_NAVIGATION

        //when
        val createdType = StepperFeedbackTypeFactory.createType(feedbackMask, mockStepperLayout)

        //then
        assertCreatedTypeIsComposite(createdType)
        assertThat(createdType as StepperFeedbackTypeComposite)
                .hasXChildComponents(1)
                .hasChildComponentOf(DisabledBottomNavigationStepperFeedbackType::class.java)
    }

    @Test
    fun `Created composite feedback type should contain all possible child feedback types when provided`() {
        //given
        val feedbackMask = StepperFeedbackType.CONTENT xor StepperFeedbackType.DISABLED_BOTTOM_NAVIGATION xor StepperFeedbackType.TABS

        //when
        val createdType = StepperFeedbackTypeFactory.createType(feedbackMask, mockStepperLayout)

        //then
        assertCreatedTypeIsComposite(createdType)
        assertThat(createdType as StepperFeedbackTypeComposite)
                .hasXChildComponents(3)
                .hasChildComponentOf(ContentStepperFeedbackType::class.java)
                .hasChildComponentOf(DisabledBottomNavigationStepperFeedbackType::class.java)
                .hasChildComponentOf(TabsStepperFeedbackType::class.java)
    }

    private fun assertCreatedTypeIsComposite(createdType: StepperFeedbackType) {
        assertNotNull(createdType)
        MatcherAssert.assertThat(createdType, Matchers.instanceOf(StepperFeedbackTypeComposite::class.java))
    }

}