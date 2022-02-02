package com.nn.architecture.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [30])
@RunWith(RobolectricTestRunner::class)
abstract class DbTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var _db: AppDatabase
    val db: AppDatabase
        get() = _db

    @Before
    fun setUp() {
        _db = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication().applicationContext,
            AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        _db.close()
    }
}