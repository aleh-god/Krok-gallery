package by.godevelopment.kroksample.ui.listregions

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
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.databinding.ListRegionsFragmentBinding
import by.godevelopment.kroksample.domain.adapters.KrokAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListRegionsFragment : Fragment() {

    companion object {
        fun newInstance() = ListRegionsFragment()
    }

    private var _binding: ListRegionsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListRegionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_regions_fragment,
            container,
            false
        )
        val onClickNav: (Int) -> Unit = { int ->
            findNavController().navigate(
                ListRegionsFragmentDirections.actionListRegionsPointToListCitiesPoint(int)
            )
        }
        viewModel.onClickNav.value = onClickNav
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupUI()
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
                        binding.progressDownload.visibility = View.INVISIBLE // View.GONE
                    }
                    .catch {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.message_error_loading),
                            Snackbar.LENGTH_LONG)
                            .show()
                    }.collect()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
