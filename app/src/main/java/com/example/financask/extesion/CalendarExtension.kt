package com.example.financask.extesion

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro(): String{
    val formatoBrasileiro= "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)

}