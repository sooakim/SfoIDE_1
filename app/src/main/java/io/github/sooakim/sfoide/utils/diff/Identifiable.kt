package io.github.sooakim.sfoide.utils.diff

interface Identifiable{
    val identifier: Any

    override operator fun equals(other: Any?): Boolean
}