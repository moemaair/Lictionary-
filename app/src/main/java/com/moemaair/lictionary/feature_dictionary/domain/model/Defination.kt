package com.moemaair.lictionary.feature_dictionary.domain.model

data class Defination(   // mapper class for Defination Dto
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)

