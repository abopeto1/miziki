package com.levagency.miziki.domain.genre.listener

import com.levagency.miziki.domain.genre.entity.Genre

class GenreListener(
    val clickListener: (genreId: Long) -> Unit
) {
    fun onClick(genre: Genre) = clickListener(genre.id)
}