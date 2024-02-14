package com.example.project_999.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

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
                .add(containerId, fragmentClass.newInstance())
                .addToBackStack(fragmentClass.name)
                .commit()
        }
    }

}