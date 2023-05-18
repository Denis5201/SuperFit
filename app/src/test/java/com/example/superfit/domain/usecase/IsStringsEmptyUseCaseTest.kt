package com.example.superfit.domain.usecase

import org.junit.Assert
import org.junit.Test


internal class IsStringsEmptyUseCaseTest {
    @Test
    fun withOneArgument_emptyString_isTrue() {

        val useCase = IsStringsEmptyUseCase()

        Assert.assertTrue(useCase(""))
    }

    @Test
    fun withOneArgument_notEmptyString_isFalse() {

        val useCase = IsStringsEmptyUseCase()

        Assert.assertFalse(useCase("test"))
    }

    @Test
    fun withThreeArgument_emptyStrings_isTrue() {

        val useCase = IsStringsEmptyUseCase()

        Assert.assertTrue(useCase("", "", ""))
    }

    @Test
    fun withThreeArgument_emptyOneStrings_isTrue() {

        val useCase = IsStringsEmptyUseCase()

        Assert.assertTrue(useCase("test", "", "123"))
    }

    @Test
    fun withThreeArgument_notEmptyStrings_isFalse() {

        val useCase = IsStringsEmptyUseCase()

        Assert.assertFalse(useCase("test", "string2", "three"))
    }
}