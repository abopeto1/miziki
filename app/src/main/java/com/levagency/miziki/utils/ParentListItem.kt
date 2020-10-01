package com.levagency.miziki.utils

class ParentListItem(
    val id: Int,
    val title: String,
    val items: List<ChildListItem>?,
    var description: String? = null,
    var action_navigation: Int? = null
)