package by.godevelopment.kroksample.ui.listregions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.data.TestData
import by.godevelopment.kroksample.databinding.ListRegionsFragmentBinding
import by.godevelopment.kroksample.domain.adapters.KrokAdapter
import by.godevelopment.kroksample.domain.model.ListItemModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListRegionsFragment : Fragment() {

    companion object {
        fun newInstance() = ListRegionsFragment()
    }

    private var _binding: ListRegionsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListRegionsViewModel by viewModels()

    private val onClickNav: (Int) -> Unit = { int ->
        Log.e(TAG, "findNavController :  $int")
        findNavController().navigate(
            ListRegionsFragmentDirections.actionListRegionsPointToListCitiesPoint(int)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.list_regions_fragment, container, false)
        viewModel.onClickNav.value = onClickNav
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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