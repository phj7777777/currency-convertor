package com.example.convertor.helper

import android.content.Context
import android.widget.Toast
import com.example.convertor.model.Rate


fun toast(context: Context, message: CharSequence) =
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun getFlag(currency: String): String {
    var lowerCurrency = currency.lowercase()
    return "https://wise.com/public-resources/assets/flags/rectangle/$lowerCurrency.png"
}

fun getRateUrl(currency: String): String {
    return "https://v6.exchangerate-api.com/v6/98bda55991eb47bac5fe9c0e/latest/$currency"
}

fun isNumeric(toCheck: String): Boolean {
    return toCheck.toDoubleOrNull() != null
}

fun roundOffToString(price: Any): String{
    if(isNumeric(price.toString())){
        return "%.2f".format(price)
    }
    return price.toString();
}

fun getCurrencyValue(rate: Rate, currency: String, value: Double): String {

    val value = rate.conversion_rates.get(currency).toString().toDouble() * value;
    return "$currency ${roundOffToString(value)}"
}