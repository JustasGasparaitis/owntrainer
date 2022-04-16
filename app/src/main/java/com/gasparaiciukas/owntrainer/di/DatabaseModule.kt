package com.gasparaiciukas.owntrainer.di

import android.content.Context
import com.gasparaiciukas.owntrainer.database.*
import com.gasparaiciukas.owntrainer.network.DefaultGetService
import com.gasparaiciukas.owntrainer.prefs.PrefsStoreImpl
import com.gasparaiciukas.owntrainer.repository.DefaultDiaryRepository
import com.gasparaiciukas.owntrainer.repository.DefaultUserRepository
import com.gasparaiciukas.owntrainer.repository.DiaryRepository
import com.gasparaiciukas.owntrainer.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePrefsStore(@ApplicationContext context: Context): PrefsStoreImpl {
        return PrefsStoreImpl(context)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideDiaryEntryDao(appDatabase: AppDatabase): DiaryEntryDao {
        return appDatabase.diaryEntryDao()
    }

    @Provides
    fun provideDiaryEntryWithMealsDao(appDatabase: AppDatabase): DiaryEntryWithMealsDao {
        return appDatabase.diaryEntryWithMealsDao()
    }

    @Provides
    fun provideFoodEntryDao(appDatabase: AppDatabase): FoodEntryDao {
        return appDatabase.foodEntryDao()
    }

    @Provides
    fun provideMealDao(appDatabase: AppDatabase): MealDao {
        return appDatabase.mealDao()
    }

    @Provides
    fun provideReminderDao(appDatabase: AppDatabase): ReminderDao {
        return appDatabase.reminderDao()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideDefaultDiaryRepository(
        diaryEntryDao: DiaryEntryDao,
        diaryEntryWithMealsDao: DiaryEntryWithMealsDao,
        mealDao: MealDao,
        foodEntryDao: FoodEntryDao,
        defaultGetService: DefaultGetService
    ) = DefaultDiaryRepository(
        diaryEntryDao,
        diaryEntryWithMealsDao,
        mealDao,
        foodEntryDao,
        defaultGetService
    ) as DiaryRepository

    @Singleton
    @Provides
    fun provideDefaultUserRepository(
        userDao: UserDao,
        reminderDao: ReminderDao,
        prefsStore: PrefsStoreImpl
    ) = DefaultUserRepository(userDao, reminderDao, prefsStore) as UserRepository
}