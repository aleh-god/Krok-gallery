package by.godevelopment.kroksample.ui.listviews

import android.os.Bundle
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
import by.godevelopment.kroksample.databinding.ListViewsFragmentBinding
import by.godevelopment.kroksample.domain.adapters.KrokAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListViewsFragment : Fragment() {

    companion object {
        fun newInstance() = ListViewsFragment()
    }

    private var _binding: ListViewsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.list_views_fragment, container, false)
        val idCityArgs: ListViewsFragmentArgs by navArgs()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.idKey.value = idCityArgs.idKey
        setupUI()
        setupNavigation()
        return binding.root
    }

    private fun setupUI() {
        val adapter = KrokAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.out
                    .onStart {
                        binding.progressDownload.visibility = View.VISIBLE
                    }
                    .onEach {
                        adapter.listItemModels = it
                        binding.progressDownload.visibility = View.INVISIBLE
                    }
                    .catch {
                        Snackbar.make(binding.root, "Loading data failed!", Snackbar.LENGTH_LONG)
                            .setAction("Reload", null)
                            .show()
                        binding.progressDownload.visibility = View.INVISIBLE
                    }.collect()
            }
        }
    }

    private fun setupNavigation() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            event.get()?.let {
                findNavController().navigate(
                    ListViewsFragmentDirections.actionListViewsPointToDetailsPoint(it)
                )
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
