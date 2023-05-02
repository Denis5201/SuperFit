package com.example.superfit.presentation

import android.content.Context
import com.example.superfit.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val resources = context.resources

    fun getMessage(reason: Int): String {
        return when(reason) {
            WRONG_EMAIL_FORMAT -> resources.getString(R.string.wrong_email_format)
            EMPTY_INPUT -> resources.getString(R.string.empty_input)
            CODE_NOT_EQUAL_WITH_REPEAT -> resources.getString(R.string.code_not_equal_with_repeat)
            WRONG_LENGTH_CODE -> resources.getString(R.string.wrong_length_code)
            CODE_CONTAIN_ZERO -> resources.getString(R.string.code_with_zero)
            else -> ERROR
        }
    }

    companion object {
        const val ERROR = "Error"
        const val EMPTY_INPUT = 0
        const val WRONG_EMAIL_FORMAT = 1
        const val CODE_NOT_EQUAL_WITH_REPEAT = 2
        const val WRONG_LENGTH_CODE = 3
        const val CODE_CONTAIN_ZERO = 4
    }
}
