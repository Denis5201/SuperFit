package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.UserPhotoBytes
import javax.inject.Inject

class SplitUserPhotoBytesByDateUseCase @Inject constructor() {

    operator fun invoke(listPhoto: List<UserPhotoBytes>): List<List<UserPhotoBytes>> {
        val resultList = mutableListOf<MutableList<UserPhotoBytes>>()

        if (listPhoto.isEmpty()) {
            return resultList
        }
        var i = 0
        while (i < listPhoto.size) {
            val section = mutableListOf<UserPhotoBytes>()
            section.add(listPhoto[i])
            i += 1
            while (i < listPhoto.size && listPhoto[i].date.month == listPhoto[i - 1].date.month &&
                listPhoto[i].date.year == listPhoto[i - 1].date.year) {
                section.add(listPhoto[i])
                i += 1
            }
            resultList.add(section)
        }

        return resultList
    }
}