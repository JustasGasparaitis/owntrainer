package com.gasparaiciukas.owntrainer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gasparaiciukas.owntrainer.R
import com.gasparaiciukas.owntrainer.databinding.FragmentProgressBinding

class ProgressFragment : Fragment(R.layout.fragment_progress) {
    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        initNavigation()
        binding.scrollView.visibility = View.VISIBLE
    }

    private fun initNavigation() {
        setupBottomNavigation(
            bottomNavigation = binding.bottomNavigation,
            navController = findNavController(),
            checkedItemId = R.id.progressFragment
        )
        setupNavigationView(
            navigationView = binding.navigationView,
            drawerLayout = binding.drawerLayout,
            navController = findNavController(),
            checkedItem = R.id.progressFragment
        )
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }
}