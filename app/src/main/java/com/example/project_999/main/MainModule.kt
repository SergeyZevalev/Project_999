package com.example.project_999.main

import com.example.project_999.core.Core
import com.example.project_999.core.Module

class MainModule(private val core: Core): Module<MainRepresentative> {
    override fun representative(): MainRepresentative {
        return MainRepresentative.Base(core.navigation())
    }
}