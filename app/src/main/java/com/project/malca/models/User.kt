package com.project.malca.models

data class User(
    val name: String,
    val upper_name: String,
    val imageUrl: String,
    val thumbImage: String,
    val uid: String,
    val deviceToken: String,
    val status: String,
    val onlineStatus: String,
    var creator: Boolean = false,
    val rating: Float = 0F,
    val skills: List<String>,
    var rollNo: String,
    var company: String? = null,
    var country: String? = null,
    var alumni: Boolean = false
) : java.io.Serializable {
    constructor() : this("", "", "", "", "", "", "", "", false, 0F, listOf(), "", null, null, false)
    constructor(
        name: String,
        upper_name: String = name.toUpperCase(),
        imageUrl: String,
        thumbImage: String,
        uid: String,
        deviceToken: String,
        rating: Float,
        skills: List<String>,
        rollNo: String,
        company: String?,
        country: String?,
        alumni: Boolean,
    ) : this(
        name,
        upper_name,
        imageUrl,
        thumbImage,
        uid,
        deviceToken,
        "",
        "",
        rating = rating,
        skills = skills,
        rollNo = rollNo,
        company = company,
        country = country,
        alumni = alumni
    )
}
