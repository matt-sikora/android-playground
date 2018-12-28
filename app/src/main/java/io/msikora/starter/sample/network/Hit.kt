package io.msikora.starter.sample.network
import org.joda.time.DateTime

data class Hit(
    val title: String,
    val url: String,
    val createdAt: DateTime
)