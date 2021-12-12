package com.geekbrains.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.weather.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })

        viewModel.getWeather()
    }

    private fun render(state: AppState) {
        when (state) {
            is AppState.Success -> {
                binding.loadingContainer.visibility = View.GONE
                binding.cityName.text = state.weather.city
                binding.temperature.text = state.weather.temperature.toString()
                binding.humidity.text = state.weather.humidity

            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(
                    binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Перезагрузить") {
                        viewModel.getWeather()
                    }.show()
            }

            is AppState.Loading ->
                binding.loadingContainer.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}