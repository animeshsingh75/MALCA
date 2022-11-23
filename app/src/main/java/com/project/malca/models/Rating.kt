package com.project.malca.models

data class Rating(
    var totalPeople:Int,
    var avgRating: Float,
){
    constructor():this(0,0F)
}
