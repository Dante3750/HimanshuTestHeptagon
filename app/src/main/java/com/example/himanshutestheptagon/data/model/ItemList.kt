package com.example.himanshutestheptagon.data.model

data class ItemList(
    val course_process_id: Int,
    val questions: List<Question>,
    val status: Boolean
)