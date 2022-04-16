package com.gasparaiciukas.owntrainer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.gasparaiciukas.owntrainer.R
import com.gasparaiciukas.owntrainer.adapter.MealAdapter
import com.gasparaiciukas.owntrainer.database.MealWithFoodEntries
import com.gasparaiciukas.owntrainer.databinding.FragmentMealBinding
import com.gasparaiciukas.owntrainer.viewmodel.MealViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealFragment : Fragment(R.layout.fragment_meal) {
    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!

    lateinit var mealAdapter: MealAdapter

    lateinit var viewModel: MealViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MealViewModel::class.java]
        viewModel.ldMeals.observe(viewLifecycleOwner) {
            it?.let { refreshUi(it) }
        }
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        initNavigation()
        setListeners()
        initRecyclerView()
    }

    private fun refreshUi(items: List<MealWithFoodEntries>) {
        mealAdapter.items = items
        binding.scrollView.visibility = View.VISIBLE
    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController())
        NavigationUI.setupWithNavController(binding.navigationView, findNavController())
        binding.layoutTab.getTabAt(1)?.select() // select Meals tab
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }

    private fun setListeners() {
        // Set up FAB
        binding.fab.setOnClickListener {
            findNavController().navigate(
                MealFragmentDirections.actionMealFragmentToCreateMealItemFragment()
            )
        }

        // Tabs (foods or meals)
        binding.layoutTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    findNavController().navigate(
                        MealFragmentDirections.actionMealFragmentToFoodFragment()
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initRecyclerView() {
        mealAdapter = MealAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mealAdapter
        }
        mealAdapter.setOnClickListeners(
            singleClickListener = { mealWithFoodEntries: MealWithFoodEntries, _: Int ->
                findNavController().navigate(
                    MealFragmentDirections.actionMealFragmentToMealItemFragment(
                        mealId = mealWithFoodEntries.meal.mealId,
                        diaryEntryId = -1
                    )
                )
            },
            longClickListener = { mealId, _ ->
                viewModel.deleteMeal(mealId)
                Snackbar.make(
                    binding.coordinatorLayout,
                    R.string.snackbar_meal_deleted,
                    Snackbar.LENGTH_SHORT
                ).setAnchorView(binding.bottomNavigation)
                    .show()
            }
        )
    }
}