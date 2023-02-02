package com.example.convertor.model

import java.util.Objects

open class Rate(
    val time_last_update_unix: String,
    val base_code: String,
    val conversion_rates: RatePair
) {
}


class RatePair(
    val USD: Double = 0.00,
    val ARS: Double = 0.00,
    val EUR: Double = 0.00,
    val GBP: Double = 0.00,
    val KRW: Double = 0.00,
    val MYR: Double = 0.00,
    val PHP: Double = 0.00,
    val RUB: Double = 0.00,
    val JPY: Double = 0.00,
    val SGD: Double = 0.00,
    val THB: Double = 0.00,
    val TWD: Double = 0.00,
    val VND: Double = 0.00,

    ) {
    fun get(c: String): Any {
        if(c == "USD") return USD
        if(c == "ARS") return ARS
        if(c == "EUR") return EUR
        if(c == "GBP") return GBP
        if(c == "KRW") return KRW
        if(c == "MYR") return MYR
        if(c == "PHP") return PHP
        if(c == "RUB") return RUB
        if(c == "JPY") return JPY
        if(c == "SGD") return SGD
        if(c == "THB") return THB
        if(c == "TWD") return TWD
        if(c == "VND") return VND

        return 0

    }
}

