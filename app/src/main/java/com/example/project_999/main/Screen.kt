package com.example.project_999.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun observed(representative: MainRepresentative) = representative.observed()
    fun show(fragmentManager: FragmentManager, containerId: Int)

    abstract class Replace(
        private val fragmentClass: Class<out Fragment>
    ) : Screen {

        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId, fragmentClass.newInstance())
                .commit()
        }
    }

    abstract class Add(private val fragmentClass: Class<out Fragment>
    ) : Screen {

        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId, fragmentClass.newInstance())
                .addToBackStack(fragmentClass.name)
                .commit()
        }
    }

    abstract class Pop(private val fragmentClass: Class<out Fragment>) : Screen {

        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            TODO("Not yet implemented")
        }
    }

    object Empty : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) = Unit

    }

}