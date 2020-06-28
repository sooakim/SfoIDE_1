package io.github.sooakim.sfoide.view.mapper

interface Mapper<T, R>{
    fun mapFrom(from: T): R
}