package com.geekbrains.weather.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.weather.Weather
import com.geekbrains.weather.databinding.DetailFragmentBinding
import com.geekbrains.weather.model.MainIntentService
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val listener = Repository.OnLoadListener {
        RepositoryImpl.getWeatherFromServer()?.let { weather ->
            binding.condition.text = weather.condition
            binding.temperature.text = weather.temperature.toString()
            binding.feelsLike.text = weather.feelsLike.toString()
            binding.humidity.text = weather.humidity.toString()
        } ?: Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
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

        RepositoryImpl.addLoadListener(listener)

        arguments?.getParcelable<Weather>("WEATHER_EXTRA")?.let { weather ->

            binding.cityName.text = weather.city.name
            binding.latLon.text = "${weather.city.lat} / ${weather.city.lon}"

            requireActivity().startService(
                Intent(
                    requireContext(),
                    MainIntentService::class.java
                ).apply {
                    putExtra("WEATHER_EXTRA", weather)
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RepositoryImpl.removeLoadListener(listener)
        _binding = null
    }
}