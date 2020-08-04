package com.nn.interview.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.robolectric.RuntimeEnvironment

abstract class DbTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var _db: AppDatabase
    val db: AppDatabase
        get() = _db

    @Before
    fun setUp() {
        _db = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        _db.close()
    }
}