package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.retrofit.RetrofitHelper
import com.retrofit.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.create
import java.net.URL

class MainActivity : AppCompatActivity() {

    //создаем поля
    private var user_field: EditText? = null//поле ввода города
    private var main_button: Button? = null//кнопка подтверждения
    private var result_info: TextView? = null//поле результата погоды


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Блок инициализации
        user_field = findViewById(R.id.user_field)
        main_button = findViewById(R.id.main_btn)
        result_info = findViewById(R.id.result_info)

        //создаем обработчик события для кнопки main_button
        main_button?.setOnClickListener{
            if(user_field?.text?.toString()?.equals("")!!)
                Toast.makeText(this,"Введите город", Toast.LENGTH_LONG).show()
            else{
                var city: String = user_field?.text.toString()//получили город из EditText
                //связываем RetrofitHelper и Weatherapi (связывание объекта с интерфесом)
                GlobalScope.launch (Dispatchers.Main){
                    val weatherApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)
                    val result = weatherApi.getWeather(city,getString(R.string.api_key))
                    println(result)
                    //result_info?.text = result.body().toString()
                    println(result.body())
                    val temp = result.body()?.main?.temp//температура
                    val desc = result.body()?.weather?.get(0)?.description
                    result_info?.text = "Температура: $temp\n$desc"
                   // println(weather)
                }
            }
        }

    }
}