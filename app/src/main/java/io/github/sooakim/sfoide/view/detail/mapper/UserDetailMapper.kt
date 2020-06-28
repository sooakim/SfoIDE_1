package io.github.sooakim.sfoide.view.detail.mapper

import com.google.android.gms.maps.model.LatLng
import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.github.sooakim.sfoide.view.detail.model.UserDetailUiModel
import io.github.sooakim.sfoide.view.mapper.Mapper

object UserDetailMapper: Mapper<UserResponse, UserDetailUiModel>{
    override fun mapFrom(from: UserResponse): UserDetailUiModel {
        return UserDetailUiModel(
                profileImagePath = from.picture.large,
                name = "이름: ${from.name.title}. ${from.name.first} ${from.name.last}",
                email = "이메일(클릭시 이동): ${from.email}",
                cellPhone = "휴대전화(클릭시 이동): ${from.cell}",
                phone = "전화(클릭시 이동): ${from.phone}",
                address = "주소: ${from.nat} ${from.location.state} ${from.location.city} ${from.location.street.name} ${from.location.street.number}",
                coordinate = LatLng(
                        from.location.coordinates.latitude.toDouble(),
                        from.location.coordinates.longitude.toDouble()
                )
        )
    }

}