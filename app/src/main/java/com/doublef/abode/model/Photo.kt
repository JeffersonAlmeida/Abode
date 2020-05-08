package com.doublef.abode.model

import java.io.Serializable
import java.util.*

class Photo(var id: String = "0", var url: String, var date: Date, var size: String = "0",
            var height: String = "0", var width: String = "0", var format: String = "jpg",
            var score: Int = 0): Serializable