package io.github.sooakim.sfoide.view.base

interface ViewModelType<Input, Output>{
    val input: Input
    val output: Output

    interface Input

    interface Output
}