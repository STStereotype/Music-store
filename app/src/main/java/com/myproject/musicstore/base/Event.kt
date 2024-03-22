package com.myproject.musicstore.base

interface Event<T> {
    fun send(event: T)
}
