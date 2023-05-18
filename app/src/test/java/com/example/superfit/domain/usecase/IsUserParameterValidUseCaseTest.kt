package com.example.superfit.domain.usecase

import org.junit.Assert
import org.junit.Test

internal class IsUserParameterValidUseCaseTest {
    @Test
    fun simpleWeight_isValid() {

        val useCase = IsUserParameterValidUseCase()

        Assert.assertTrue(useCase("60"))
    }

    @Test
    fun simpleHeight_isValid() {

        val useCase = IsUserParameterValidUseCase()

        Assert.assertTrue(useCase("175"))
    }

    @Test
    fun outOfLowerBounds_isNotValid() {

        val useCase = IsUserParameterValidUseCase()

        Assert.assertFalse(useCase("0"))
    }

    @Test
    fun outOfUpperBounds_isNotValid() {

        val useCase = IsUserParameterValidUseCase()

        Assert.assertFalse(useCase("500"))
    }
}