package com.example.project_999.main

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.project_999.core.ProvideRepresentative
import com.example.project_999.core.Representative

abstract class BaseFragment<T: Representative<*>>(
    @LayoutRes private val layoutId: Int
) : Fragment(layoutId) {

    protected lateinit var representative: T
    protected abstract val clasz: Class<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        representative = (requireActivity() as ProvideRepresentative).provideRepresentative(
            clasz)
    }
}