package by.godevelopment.kroksample.ui.listplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.databinding.ListPlacesFragmentBinding
import by.godevelopment.kroksample.domain.adapters.KrokAdapter
import by.godevelopment.kroksample.domain.model.ListItemModel
import by.godevelopment.kroksample.ui.listcities.ListCitiesFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPlacesFragment : Fragment() {

    companion object {
        fun newInstance() = ListPlacesFragment()
    }

    private var _binding: ListPlacesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListPlacesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.list_places_fragment, container, false)
        val idCityArgs: ListPlacesFragmentArgs by navArgs()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.idKey.value = idCityArgs.idKey
        setupUI()
        setupNavigation()
        return binding.root
    }

    private fun setupAdapter(listItemInput: List<ListItemModel>) {
        val adapter = KrokAdapter()
        adapter.listItemModels = listItemInput
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.i(TAG, "ListPlacesFragment : Lifecycle.State.STARTED")
                viewModel.out
                    .onStart {
                        binding.progressDownload.visibility = View.VISIBLE
                    }
                    .onEach {
                        setupAdapter(it)
                        binding.progressDownload.visibility = View.INVISIBLE
                    }
                    .catch {
                        Snackbar.make(binding.root, "Loading data failed!", Snackbar.LENGTH_LONG)
                            .setAction("Reload", null) // View.OnClickListener
                            .show()
                        binding.progressDownload.visibility = View.INVISIBLE
                    }.collect()
            }
        }
    }

    private fun setupNavigation() {
        viewModel.navigationEvent.observe(this) { event ->
            event.get()?.let {
                Log.i(TAG, "ListPlacesFragment findNavController : $it")
                findNavController().navigate(
                    ListPlacesFragmentDirections.actionListPlacesPointToDetailsPoint(it)
                )
            }
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "ListPlacesFragment : onDestroy()")
        _binding = null
        super.onDestroy()
    }
}
