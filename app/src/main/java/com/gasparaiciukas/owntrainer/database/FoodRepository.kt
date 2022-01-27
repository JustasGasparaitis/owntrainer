package com.gasparaiciukas.owntrainer.database

import com.gasparaiciukas.owntrainer.BuildConfig
import com.gasparaiciukas.owntrainer.network.Food
import com.gasparaiciukas.owntrainer.network.GetService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(
    private val getService: GetService
) {
    suspend fun getFoods(query: String) = getService.getFoods(BuildConfig.API_KEY, query).foods ?: listOf()
}