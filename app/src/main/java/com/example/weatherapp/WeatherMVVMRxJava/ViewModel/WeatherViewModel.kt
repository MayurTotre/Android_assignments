package com.example.weatherapp.WeatherMVVMRxJava.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.WeatherMVVMRxJava.WeatherRepository
import com.example.weatherapp.WeatherMVVMRxJava.WeatherResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers;



class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val disposables = CompositeDisposable()

    fun fetchWeather(key: String, location: String, days: Int, aqi: String, alerts: String){
        disposables.add(
            repository.getWeather(location, days)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response -> _weatherData.value = response},
                    {error -> _errorMessage.value = error.localizedMessage}
                )
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}