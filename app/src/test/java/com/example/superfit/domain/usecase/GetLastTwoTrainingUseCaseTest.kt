package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate

internal class GetLastTwoTrainingUseCaseTest {
    @Test
    fun emptyList_ReturnTwoNull() {
        val useCase = GetLastTwoTrainingUseCase()
        val testData = emptyList<Training>()
        val expected = Pair<TrainingType?, TrainingType?>(null, null)

        val actual = useCase(testData)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithOneTraining_ReturnTypeAndNull() {
        val useCase = GetLastTwoTrainingUseCase()
        val testData = listOf(Training(LocalDate.now(), TrainingType.PUSH_UP, 10))
        val expected = Pair<TrainingType?, TrainingType?>(TrainingType.PUSH_UP, null)

        val actual = useCase(testData)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithTwoSameTraining_ReturnTypeAndNull() {
        val useCase = GetLastTwoTrainingUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PUSH_UP, 10),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 15)
        )
        val expected = Pair<TrainingType?, TrainingType?>(TrainingType.PUSH_UP, null)

        val actual = useCase(testData)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithTwoDifferentTraining_ReturnTwoDifferentType() {
        val useCase = GetLastTwoTrainingUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PLANK, 10),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 15)
        )
        val expected = Pair<TrainingType?, TrainingType?>(TrainingType.PUSH_UP, TrainingType.PLANK)

        val actual = useCase(testData)

        assertEquals(expected, actual)
    }

    @Test
    fun listWithThreeTraining_ReturnTwoDifferentType() {
        val useCase = GetLastTwoTrainingUseCase()
        val testData = listOf(
            Training(LocalDate.now(), TrainingType.PLANK, 10),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 15),
            Training(LocalDate.now(), TrainingType.PUSH_UP, 12)
        )
        val expected = Pair<TrainingType?, TrainingType?>(TrainingType.PUSH_UP, TrainingType.PLANK)

        val actual = useCase(testData)

        assertEquals(expected, actual)
    }
}