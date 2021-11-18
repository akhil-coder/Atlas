package com.example.atlas.presentation.main.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atlas.business.domain.models.ResultsEntity
import com.example.atlas.business.domain.utils.StateMessageCallback
import com.example.atlas.databinding.MovieFragmentBinding
import com.example.atlas.presentation.util.TopSpacingItemDecoration
import com.example.atlas.presentation.util.processQueue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    val TAG = "MovieFragment"
    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!
    private var recyclerAdapter: MovieListAdapter? = null

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            //            uiCommunicationListener.displayProgressBar(state.isLoading)
            processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(MovieEvents.OnRemoveHeadFromQueue)
                    }
                }
            )
            recyclerAdapter?.apply {
                submitList(movieList = state.movieList?.resultsEntity)
            }
        }

    }

    private fun initRecyclerView() {
        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = MovieListAdapter()
            adapter = recyclerAdapter
        }
        var  a = listOf<ResultsEntity>( ResultsEntity(1, "", "23", "dfdkfl", "",23.00), ResultsEntity(1, "", "23", "dfdkfl", "",23.00)  as ResultsEntity)
        recyclerAdapter?.apply {
            submitList(movieList = a)
        }
    }
}