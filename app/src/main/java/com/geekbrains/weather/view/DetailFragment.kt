package com.geekbrains.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.weather.Weather
import com.geekbrains.weather.databinding.DetailFragmentBinding
import com.geekbrains.weather.model.WeatherDTO
import com.geekbrains.weather.model.WeatherLoader

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>("WEATHER_EXTRA")?.let { weather ->

            binding.cityName.text = weather.city.name
            binding.latLon.text = "${weather.city.lat} / ${weather.city.lon}"

            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {

                override fun onLoaded(weatherDTO: WeatherDTO) {

                    weatherDTO.fact?.let {fact ->
                        binding.condition.text = fact.condition
                        binding.temperature.text = fact.temp.toString()
                        binding.feelsLike.text = fact.feels_like.toString()
                        binding.humidity.text = fact.humidity.toString()
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    Toast.makeText(requireActivity(), throwable.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}