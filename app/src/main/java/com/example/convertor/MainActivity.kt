package com.example.convertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.convertor.helper.*
import com.example.convertor.model.Rate
import com.example.convertor.model.RatePair
import com.example.convertor.src.SwipeBox
import com.example.convertor.ui.theme.ConvertorTheme


interface Result {
    fun getResult(id: Rate?)
}


class MainActivity : ComponentActivity() {

    // Set SGD 1 as default base code so there's something display initially
    var baseCode = "SGD"
    var values = 1.00


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // callback function to get rate from API
        getRequest(getRateUrl(baseCode),object : Result {
            override fun getResult(rate: Rate?) {
                setContent {
                    MainScaffold(false, supportedCurrency, rate)
                }
            }
        })

        setContent {
            MainScaffold(isLoading = true, supportedCurrency, null)
        }
    }


    @Composable
    fun MainScaffold(isLoading: Boolean, supportedCurrency: Array<String>, rate: Rate?) {
        ConvertorTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    content = {
                        if (!isLoading && rate != null) {
                            CurrentListing(rate, supportedCurrency)
                        } else {
                            Text("Loading")
                        }

                    }
                )
            }
        }
    }


    @Composable
    fun TopBar() {
        TopAppBar {
            Box(Modifier.padding(horizontal = 16.dp)){ Text("Convertor", color = Color.White)}
        }
    }

    @Composable
    fun CurrentListing(rate: Rate, supportedCurrency: Array<String>) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Header(rate.time_last_update_unix)
            LazyColumn(
            ) {
                items(count = supportedCurrency.size) { index ->
                    val currency = supportedCurrency[index % supportedCurrency.size]

                    // Do not display the listItem if base code is itself
                    if(currency != rate?.base_code){
                        SwipeBox(rate, currency, values)
                    }

                }
            }
        }
    }

    @Composable
    fun Header(timeLastUpdateUtc: String) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Row {
                Modifier.padding(vertical = 16.dp)
                Text("Last updated: ${getDateTime(timeLastUpdateUtc)}")
            }
            TextFieldRow()

        }
    }

    @Composable
    fun TextFieldRow() {

        var currency by remember { mutableStateOf(TextFieldValue(baseCode)) }
        var value by remember { mutableStateOf(TextFieldValue(values.toString())) }

        @Composable
        fun PriceTextField() {

            OutlinedTextField(
                value = value,
                onValueChange = {
                    value = it
                },
                placeholder = { Text(text = values.toString()) },
                label = { Text(text = "Value") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

        }

        @Composable
        fun CurrencyTextField() {

            OutlinedTextField(
                value = currency,
                onValueChange = {
                    currency = it
                },
                label = { Text(text = "Currency") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                placeholder = { Text(text = baseCode.toString()) },
            )
        }

        Row(Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.weight(1f)) {
                CurrencyTextField()
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                PriceTextField()
            }
            Box(modifier = Modifier.padding(16.dp)) {
                IconButton(content = { Icon(Icons.Rounded.Send, "Send") }, onClick = {

                    if(RatePair().get(currency.text) == 0) {
                        toast(this@MainActivity, "Sorry, we are unable to support this currency: ${currency.text}")
                    }
                    else if(value.text.toDoubleOrNull() == null){
                        toast(this@MainActivity, "Please ensure value input correctly")
                    }else{
                        baseCode = currency.text
                        values = value.text.toDouble()
                        toast(this@MainActivity, "Refresh successfully")
                        // Refresh List
                        getRequest(getRateUrl(baseCode),object : Result {
                            override fun getResult(rate: Rate?) {
                                setContent {
                                    MainScaffold(false, supportedCurrency, rate)
                                }

                            }
                        })
                    }


                })
            }
        }
    }
}





