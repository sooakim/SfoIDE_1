package io.github.sooakim.sfoide.view.main.model

import io.github.sooakim.sfoide.utils.diff.Identifiable

data class UserUiModel(
        val id: String,
        val profileImagePath: String?,
        val name: String,
        val gender: String,
        val countryFlag: String,
        val onClick: (() -> Unit)
): Identifiable{
    override val identifier: Any
        get() = id
}