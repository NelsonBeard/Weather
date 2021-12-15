package com.geekbrains.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.weather.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()
    private var isRussian = true

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
        binding.mainRecyclerView.adapter = adapter

        adapter.listener = MainAdapter.OnItemClick { weather ->

            val bundle = Bundle()
            bundle.putParcelable("WEATHER_EXTRA", weather)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
    }

    private fun render(state: AppState) {
        when (state) {
            is AppState.Success<*> -> {
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(
                    binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Перезагрузить") {
                        viewModel.getWeatherFromLocalStorageRus()
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