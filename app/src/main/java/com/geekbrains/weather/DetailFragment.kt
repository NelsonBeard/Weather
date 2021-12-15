package com.geekbrains.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.weather.databinding.DetailFragmentBinding

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
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<Weather>("WEATHER_EXTRA")
        binding.cityName.text = weather?.city?.name ?: ""
        binding.latLon.text = weather?.city?.lat.toString() + "/" + weather?.city?.lon.toString()
        binding.temperature.text = "Температура: " + weather?.temperature.toString()
        binding.feelsLike.text = "Ощущается как: " + weather?.feelsLike.toString()
        binding.humidity.text = "Влажность: " + weather?.humidity
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}