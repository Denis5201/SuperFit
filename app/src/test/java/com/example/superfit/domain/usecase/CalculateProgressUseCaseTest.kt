package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate

internal class CalculateProgressUseCaseTest {
    @Test
    fun emptyList_ReturnTwoNull() {
        val useCase = CalculateProgressUseCase()
        val testData = emptyList<Training>()
        val expected =  Pair<Int?, Int?>(null, null)

        val actual = useCase(testData, TrainingType.PUSH_UP)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithOneTraining_ReturnRepeatCountAndNull() {
        val useCase = CalculateProgressUseCase()
        val testData = listOf(Training(LocalDate.now(), TrainingType.PUSH_UP, 10))
        val expected =  Pair<Int?, Int?>(testData[0].repeatCount, null)

        val actual = useCase(testData, TrainingType.PUSH_UP)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithTwoDifferentTraining_ReturnRepeatCountAndNull() {
        val useCase = CalculateProgressUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PLANK, 10),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 15)
        )
        val expected =  Pair<Int?, Int?>(testData[1].repeatCount, null)

        val actual = useCase(testData, TrainingType.PUSH_UP)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithTwoSameTraining_ReturnRepeatCountAndPositiveProgress() {
        val useCase = CalculateProgressUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PUSH_UP, 10),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 15)
        )
        val expected =  Pair<Int?, Int?>(testData[1].repeatCount, 50)

        val actual = useCase(testData, TrainingType.PUSH_UP)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithTwoSameTraining_ReturnRepeatCountAndNegativeProgress() {
        val useCase = CalculateProgressUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PUSH_UP, 10),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 5)
        )
        val expected =  Pair<Int?, Int?>(testData[1].repeatCount, -50)

        val actual = useCase(testData, TrainingType.PUSH_UP)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithThreeTraining_ReturnRepeatCountAndPositiveProgress() {
        val useCase = CalculateProgressUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PUSH_UP, 10),
            Training(LocalDate.now(), TrainingType.PLANK, 40),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 15)
        )
        val expected =  Pair<Int?, Int?>(testData[2].repeatCount, 50)

        val actual = useCase(testData, TrainingType.PUSH_UP)

        assertEquals(expected, actual)
    }
}