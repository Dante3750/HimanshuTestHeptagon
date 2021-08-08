package com.example.himanshutestheptagon.data.model

data class Question(
    val answers: String,
    val error_message: String,
    val image_flag: Int,
    val images: List<String>,
    val mandatory: Int,
    val multi_option_flag: Int,
    val multi_select_flag: Int,
    val placeholder: String,
    val question: String,
    val question_id: Int,
    val type: String,
    var url: String?="",
    val values: List<Value>
)