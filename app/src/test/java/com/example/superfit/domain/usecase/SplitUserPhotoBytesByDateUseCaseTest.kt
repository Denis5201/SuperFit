package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.UserPhotoBytes
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.time.LocalDate
import java.time.Month

internal class SplitUserPhotoBytesByDateUseCaseTest {
    @Test
    fun splitTwoElement_WithDifferentMonth_WithSameYear() {

        val useCase = SplitUserPhotoBytesByDateUseCase()
        val testData = listOf(
            UserPhotoBytes("1", byteArrayOf(), LocalDate.of(2023, Month.APRIL, 5)),
            UserPhotoBytes("2", byteArrayOf(), LocalDate.of(2023, Month.MAY, 5))
        )
        val expected = listOf(
            listOf(testData[0]),
            listOf(testData[1])
        )

        val actual = useCase(testData)

        Assertions.assertIterableEquals(expected, actual)
    }

    @Test
    fun splitTwoElement_WithSameMonth_WithSameYear() {

        val useCase = SplitUserPhotoBytesByDateUseCase()
        val testData = listOf(
            UserPhotoBytes("1", byteArrayOf(), LocalDate.of(2023, Month.APRIL, 5)),
            UserPhotoBytes("2", byteArrayOf(), LocalDate.of(2023, Month.APRIL, 6))
        )
        val expected = listOf(
            listOf(testData[0], testData[1])
        )

        val actual = useCase(testData)

        Assertions.assertIterableEquals(expected, actual)
    }

    @Test
    fun splitTwoElement_WithSameMonth_WithDifferentYear() {

        val useCase = SplitUserPhotoBytesByDateUseCase()
        val testData = listOf(
            UserPhotoBytes("1", byteArrayOf(), LocalDate.of(2022, Month.APRIL, 5)),
            UserPhotoBytes("2", byteArrayOf(), LocalDate.of(2023, Month.APRIL, 5))
        )
        val expected = listOf(
            listOf(testData[0]),
            listOf(testData[1])
        )

        val actual = useCase(testData)

        Assertions.assertIterableEquals(expected, actual)
    }
}