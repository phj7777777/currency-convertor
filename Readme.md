

## Code Architecture

The code architecture of this project is MVVM. The main reason of chosing MVVM is because it allow the business logic to decoupled from Ul. It's easier to maintain and debug during development. With MVVM, the package structure is easier to navigate. Helper function are implemented in helper file. These helper function increase reusability and readability.


## Issue and Improvement
Currently there's no filter function hence too many currency pairs will be displayed to user. To avoid that, this project implemented "supported_currencies" which only few popular currencies are selected to be displayed. We can remove this variable and return all currencies rates as list when there's a function which user are allowed to add their own pair by using UI like currency picker. Apart from that, future improvement can be done by checking if current target currency is same as previous base currency before call currency API. This will reduce unneccessary API call.

As the API currently only update once per day, there's no refresh function in the project. For future improvement, it would be good to have search function for user to search desired currency pair. User should also able to add their own favourite pair instead of displaying all pair as for now.

## Design Ideas
The idea of this project is to simplify user input process by display multiple currency pair results with just few inputs. User don't have perform multiple clicks just to get different currency pair results. To provide better rate information, each currency pairs not only displayed with the exchange reuslt but also the exchange rate with the base currency. Last rate updated time will also displayed to user


## Other Information

**APK file** can be found in /convertor/app/build/outputs/apk/debug

**Android Gradle Plugin Version** - 7.3.1

**Gradle Build** - 7.4

**Minimum SDK Version** - 21 


## Third Party Libraries

1. coil-kt:coil - One of the most popular image loading library. Fast performance which support disk-caching
2. gson - Used to convert json string to deserialized json object. Useful during API call
3. okhttp3 - Useful for http request. Very easy to use and support async callback
4. jetpack compose - Way more easier to build UI. Support material design which provide better looking comparing to XML design 