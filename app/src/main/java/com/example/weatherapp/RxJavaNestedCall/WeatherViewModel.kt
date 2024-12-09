package com.example.weatherapp.RxJavaNestedCall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
//    private val _weatherData = MutableLiveData<WeatherResponse>()
//    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _cityWeatherData = MutableLiveData<CityWeatherResponse>()
    val cityWeatherData: LiveData<CityWeatherResponse> get() = _cityWeatherData
//
//    private val _cityData = MutableLiveData<CityResponse>()
//    val cityData: LiveData<CityResponse> get() = _cityData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

//    val disposables = CompositeDisposable()

    fun getCityData(city: String) {
//        disposables.add(
        repository.getCityName(city)
            .subscribeOn(Schedulers.io())
            .flatMap { cityRes ->
                val lat = cityRes.firstOrNull()?.latitude ?: 0.0
                val long = cityRes.firstOrNull()?.longitude ?: 0.0

                val lat_long = "${lat},${long}"

                repository.getWeather(lat_long, 1)
                    .map { weatherResponse ->
//                            WeatherDisplayModel(
//                                temperature = weatherResponse.current.temp_c,
//                                condition = weatherResponse.current.condition.toString()
//                            )
                        Pair(cityRes, weatherResponse)

                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    val res = CityWeatherResponse(response.first, response.second)
                    _cityWeatherData.value = res
                },
                { error -> _errorMessage.value = error.localizedMessage }
            )

    }

//    fun getWeatherData(key: String, location: String, days: Int, aqi: String, alerts: String){
//        disposables.add(
//            repository.getWeather(location, days)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    {response -> _weatherData.value = response},
//                    { error -> _errorMessage.value = error.localizedMessage}
//                )
//        )
//    }
}