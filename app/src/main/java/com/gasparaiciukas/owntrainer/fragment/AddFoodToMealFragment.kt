package com.gasparaiciukas.owntrainer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gasparaiciukas.owntrainer.R
import com.gasparaiciukas.owntrainer.adapter.MealAdapter
import com.gasparaiciukas.owntrainer.database.MealWithFoodEntries
import com.gasparaiciukas.owntrainer.databinding.FragmentAddFoodToMealBinding
import com.gasparaiciukas.owntrainer.viewmodel.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFoodToMealFragment : Fragment(R.layout.fragment_add_food_to_meal) {
    private var _binding: FragmentAddFoodToMealBinding? = null
    private val binding get() = _binding!!

    lateinit var mealAdapter: MealAdapter

    lateinit var sharedViewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFoodToMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[FoodViewModel::class.java]

        sharedViewModel.ldMeals.observe(viewLifecycleOwner) {
            it?.let { refreshUi(it) }
        }
        initUi()

        sharedViewModel.ldMeals.value?.let {
            refreshUi(it)
        }
    }

    fun initUi() {
        initNavigation()
        initRecyclerView()
    }

    private fun refreshUi(items: List<MealWithFoodEntries>) {
        mealAdapter.items = items
        binding.scrollView.visibility = View.VISIBLE
    }

    private fun initNavigation() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        mealAdapter = MealAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mealAdapter
        }
        mealAdapter.setOnClickListeners(
            singleClickListener = { mealWithFoodEntries: MealWithFoodEntries, _: Int ->
                viewLifecycleOwner.lifecycleScope.launch {
                    sharedViewModel.addFoodToMeal(mealWithFoodEntries)
                    findNavController().popBackStack()
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}