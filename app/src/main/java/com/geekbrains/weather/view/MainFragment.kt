package com.geekbrains.weather.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.weather.R
import com.geekbrains.weather.Weather
import com.geekbrains.weather.databinding.MainFragmentBinding
import com.geekbrains.weather.viewmodel.AppState
import com.geekbrains.weather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = MainAdapter()
    private var isRussian = true

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecyclerView.adapter = adapter

        adapter.listener = MainAdapter.OnItemClick { weather ->

            val bundle = Bundle()
            bundle.putParcelable("WEATHER_EXTRA", weather)

            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }
        }


        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })

        viewModel.getWeatherFromLocalStorageRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getWeatherFromLocalStorageRus()
            } else {
                viewModel.getWeatherFromLocalStorageWorld()
            }
        }

        binding.historyFAB.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }
    }

    private fun render(state: AppState) {
        when (state) {
            is AppState.Success<*> -> {
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.hide()
            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(), "Перезагрузить",
                    {
                        viewModel.getWeatherFromLocalStorageRus()
                    })
            }

            is AppState.Loading ->
                binding.loadingContainer.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() =
            MainFragment()
    }
}


