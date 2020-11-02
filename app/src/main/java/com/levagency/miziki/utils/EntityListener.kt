package com.levagency.miziki.utils

class EntityListener<T>(
    val clickListener: (entity: T) -> Unit
) {
    fun onClick(entity: T) = clickListener(entity)
}
