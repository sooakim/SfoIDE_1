package io.github.sooakim.sfoide.view.detail.model

import com.google.android.gms.maps.model.LatLng

data class UserDetailUiModel(
        val profileImagePath: String?,
        val name: String,
        val email: String,
        val cellPhone: String,
        val phone: String,
        val address: String,
        val coordinate: LatLng
)