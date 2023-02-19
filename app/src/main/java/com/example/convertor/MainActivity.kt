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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.convertor.helper.*
import com.example.convertor.model.Rate
import com.example.convertor.src.CurrencyBox
import com.example.convertor.ui.theme.ConvertorTheme
import com.example.convertor.view_model.MainViewModel


interface Result {
    fun getResult(id: Rate?)
}


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainViewModel()
        viewModel.getCurrencyRate(this)
        setContent {
            MainScaffold(false, viewModel.supportedCurrency, viewModel)
        }
    }


    @Composable
    fun MainScaffold(isLoading: Boolean, supportedCurrency: Array<String>, viewModel: MainViewModel) {
        val rates = viewModel.rates.observeAsState().value
        
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
                        if (!isLoading && rates != null) {
                                CurrentListing(rates, supportedCurrency, viewModel )
                        } else {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {Text("Loading...")}
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
    fun CurrentListing(rate: Rate?, supportedCurrency: Array<String>, viewModel: MainViewModel) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Header(rate?.time_last_update_unix, viewModel)
            LazyColumn(
            ) {
                items(count = supportedCurrency.size) { index ->
                    val currency = supportedCurrency[index]

                    // Do not display the listItem if base code is itself
                    if(currency != rate?.base_code){
                        if (rate != null) {
                            CurrencyBox(rate, currency, viewModel.values)
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun Header(timeLastUpdateUtc: String?, viewModel: MainViewModel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            if(timeLastUpdateUtc != null) {
                Row {
                    Modifier.padding(vertical = 16.dp)
                    Text("Last updated: ${getDateTime(timeLastUpdateUtc)}")
                }
            }
            TextFieldRow(viewModel)
        }
    }

    @Composable
    fun TextFieldRow(viewModel: MainViewModel) {

        var currency by remember { mutableStateOf(TextFieldValue(viewModel.baseCode)) }
        var value by remember { mutableStateOf(TextFieldValue(roundOffToString(viewModel.values))) }

        @Composable
        fun PriceTextField() {

            OutlinedTextField(
                value = value,
                onValueChange = {
                    value = it
                },
                placeholder = { Text(text = "1.00")},
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
                placeholder = { Text(text = viewModel.baseCode) },
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.weight(1f)) {
                CurrencyTextField()
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                PriceTextField()
            }
            Box(modifier = Modifier.padding(16.dp)) {
                IconButton(content = { Icon(Icons.Rounded.Send, "Send") }, onClick = {
                    val res = viewModel.sendRequest(this@MainActivity, currency.text, value.text.toDoubleOrNull())
                    if(res){
                      viewModel.getCurrencyRate(this@MainActivity)
                    }

                })
            }
        }
    }
}





