package io.github.sooakim.sfoide.utils.lifecycle

import java.util.concurrent.atomic.AtomicBoolean

data class SingleData<T>(
        private val data: T,
        private val isConsumed: AtomicBoolean = AtomicBoolean()
){
    fun consume(block: (T) -> Unit){
        if(isConsumed.compareAndSet(false, true)){
            block.invoke(data)
        }
    }
}