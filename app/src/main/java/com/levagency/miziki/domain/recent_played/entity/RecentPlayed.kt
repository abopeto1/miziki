package com.levagency.miziki.domain.recent_played.entity

data class RecentPlayed(
    val id: Long,
    val type: String,
    val name: String,
    val entityId: Long,
    val imageUrl: String,
)