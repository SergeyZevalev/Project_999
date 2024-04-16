package com.example.project_999.core

import android.os.Build
import android.os.Bundle
import java.io.Serializable

interface SaveAndRestore {

    interface Save<T : Serializable> {
        fun save(data: T)
    }

    interface Read<T : Serializable>: IsEmpty {

        fun read(): T
    }

    interface Mutable<T : Serializable> : Save<T>, Read<T>

    abstract class Abstract<T : Serializable>(
        private val bundle: Bundle?,
        private val key: String,
        private val clasz: Class<T>
    ) : Mutable<T> {

        override fun save(data: T) = bundle!!.putSerializable(key, data)

        override fun read(): T =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) bundle!!.getSerializable(
                key,
                clasz
            ) as T
            else bundle!!.getSerializable(key) as T

        override fun isEmpty() = bundle == null

    }
}