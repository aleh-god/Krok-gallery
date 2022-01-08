package by.godevelopment.kroksample.ui.listcities

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.databinding.ListCitiesFragmentBinding
import by.godevelopment.kroksample.domain.adapters.KrokAdapter
import by.godevelopment.kroksample.domain.model.ListItemModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListCitiesFragment : Fragment() {

    companion object {
        fun newInstance() = ListCitiesFragment()
    }

    private var _binding: ListCitiesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListCitiesViewModel by viewModels()

    private val onClickNav: (Int) -> Unit = { int ->
        Log.e(TAG, "findNavController :  $int")
        findNavController().navigate(
            ListCitiesFragmentDirections.actionListCitiesPointToListPlacesPoint(int)
        )
    }

    private val idCityArgs: ListCitiesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.list_cities_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.onClickNav.value = onClickNav
        viewModel.header.value = "City: #${idCityArgs.idCity}"

        setupUI()

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
                Log.i(TAG, "Fragment : Lifecycle.State.STARTED")
                viewModel.out
                    .onStart {
                        binding.progressDownload.visibility = View.VISIBLE
                    }
                    .onEach {
                        setupAdapter(it)
                    }
                    .onCompletion {
                        binding.progressDownload.visibility = View.INVISIBLE // View.GONE
                    }
                    .catch {
                        Snackbar.make(binding.root, "Loading data failed!", Snackbar.LENGTH_LONG)
                            .setAction("Reload", null) // View.OnClickListener
                            .show()
                    }.collect()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        _binding = null
    }

}