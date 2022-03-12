package com.gasparaiciukas.owntrainer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gasparaiciukas.owntrainer.database.FoodEntry
import com.gasparaiciukas.owntrainer.repository.DefaultFoodRepository
import com.gasparaiciukas.owntrainer.database.MealWithFoodEntries
import com.gasparaiciukas.owntrainer.network.Food
import com.gasparaiciukas.owntrainer.repository.DefaultDiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFoodToMealViewModel @Inject internal constructor(
    private val foodRepository: DefaultFoodRepository,
    private val diaryRepository: DefaultDiaryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val foodItem: Food? = savedStateHandle["foodItem"]
    private val quantity: Int? = savedStateHandle["quantity"]

    val ldMeals = diaryRepository.getMealsWithFoodEntries().asLiveData()
    lateinit var meals: List<MealWithFoodEntries>

    suspend fun addFoodToMeal(mealWithFoodEntries: MealWithFoodEntries) {
        if (foodItem != null && quantity != null) {
            var protein = 0.0
            var fat = 0.0
            var carbs = 0.0
            var calories = 0.0

            val nutrients = foodItem.foodNutrients
            if (nutrients != null) {
                for (nutrient in nutrients) {
                    if (nutrient.nutrientId == 1003) protein = (nutrient.value ?: 0.0)
                    if (nutrient.nutrientId == 1004) fat = (nutrient.value ?: 0.0)
                    if (nutrient.nutrientId == 1005) carbs = (nutrient.value ?: 0.0)
                    if (nutrient.nutrientId == 1008) calories = (nutrient.value ?: 0.0)
                }
            }
            val foodEntry = FoodEntry(
                mealWithFoodEntries.meal.mealId,
                foodItem.description.toString(),
                calories,
                carbs,
                fat,
                protein,
                quantity.toDouble()
            )
            foodRepository.insertFood(foodEntry)
        }
    }
}