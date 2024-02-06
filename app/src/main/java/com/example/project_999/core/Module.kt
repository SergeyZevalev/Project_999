package com.example.project_999.core

interface Module<T : Representative<*>> {

    fun representative() : T
}