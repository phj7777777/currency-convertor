package com.example.convertor.view_model
import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.*
import com.example.convertor.Result
import com.example.convertor.helper.getRateUrl
import com.example.convertor.helper.getRequest
import com.example.convertor.helper.toast
import com.example.convertor.model.Rate
import com.example.convertor.model.RatePair
import kotlinx.coroutines.launch
import com.example.convertor.MainActivity

class MainViewModel : ViewModel() {

    // Set SGD 1 as default base code so there's something display initially
    var baseCode: String = "SGD"
    var values: Double = 1.00
    val supportedCurrency = arrayOf("USD","ARS","EUR","GBP","KRW","MYR","PHP","RUB","JPY","SGD","THB","TWD","VND")

    private val _rates = MutableLiveData<Rate>()
    var rates: LiveData<Rate>
        get() = _rates
        set(value) {}

    fun sendRequest(context: Context, currency: String, value: Double?): Boolean {

        return if(RatePair().get(currency) < 0) {
            toast(context, "Sorry, we are unable to support this currency: $currency")
            false;
        } else if(value == null){
            toast(context, "Please ensure value input correctly")
            false;
        }else{
            baseCode = currency
            values = value
            toast(context, "Refresh successfully")
            true;

        }

    }

    fun getCurrencyRate(activity: MainActivity){
        activity.lifecycleScope.launch {
            // suspend callback function to get rate from API
            getRequest(getRateUrl(baseCode),object : Result {
                override fun getResult(r: Rate?) {
                    // set value to notify
                    _rates.postValue(r)
                }
            })
        }
    }
}