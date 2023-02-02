package com.example.convertor.src

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.convertor.ui.theme.Shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import coil.compose.rememberAsyncImagePainter
import com.example.convertor.helper.getCurrencyValue
import com.example.convertor.helper.getFlag
import com.example.convertor.model.Rate
import com.example.convertor.ui.theme.GrayLight
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBox(rate: Rate, currency: String, value: Double) {

    Card(
        border = BorderStroke(1.dp, GrayLight),
        modifier = Modifier
            .fillMaxWidth()
            .size(80.dp)
            .padding(vertical = 8.dp)
            .clip(Shapes.medium)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            _frontComponent(currency)
            EndComponent(rate, currency, value)
        }

    }
}

@Composable
fun _frontComponent(currency: String) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryFlagImg(currency)
        Text(text = currency)
    }

}


@Composable
fun EndComponent(rate: Rate, currency: String, value: Double) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = getCurrencyValue(rate, currency, value))
            Text(
                text = "1 ${rate.base_code} = ${rate.conversion_rates.get(currency)} $currency",
                style = MaterialTheme.typography.caption
            )
        }

    }
}


@Composable
fun CountryFlagImg(currency: String) {

    Image(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        painter = rememberAsyncImagePainter(getFlag(currency)),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}