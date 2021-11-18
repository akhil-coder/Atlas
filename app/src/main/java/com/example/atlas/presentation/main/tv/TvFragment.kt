package com.example.atlas.presentation.main.tv

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atlas.R
import com.example.atlas.databinding.MovieFragmentBinding
import com.example.atlas.databinding.TvFragmentBinding

class TvFragment : Fragment() {

    private var _binding: TvFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TvFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        // TODO: Use the ViewModel
    }

}