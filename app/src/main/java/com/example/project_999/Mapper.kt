package com.example.project_999

interface Mapper<T> {

    fun map(): T
    fun map(item: T): Any
}