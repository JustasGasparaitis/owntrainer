package com.gasparaiciukas.owntrainer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.gasparaiciukas.owntrainer.MainCoroutineRule
import com.gasparaiciukas.owntrainer.repository.FakeDiaryRepository
import com.gasparaiciukas.owntrainer.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MealItemViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MealItemViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var userRepository: FakeUserRepository
    private lateinit var diaryRepository: FakeDiaryRepository

    @Before
    fun setup() {
        userRepository = FakeUserRepository()
        diaryRepository = FakeDiaryRepository()
        savedStateHandle = SavedStateHandle().apply {
            set("mealId", 1)
        }
        viewModel = MealItemViewModel(userRepository, diaryRepository, savedStateHandle)
    }

    @Test
    fun `when loadData() is called, should load data`() = runTest {

    }
}