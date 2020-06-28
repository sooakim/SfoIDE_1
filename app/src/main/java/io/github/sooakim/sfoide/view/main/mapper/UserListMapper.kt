package io.github.sooakim.sfoide.view.main.mapper

import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.github.sooakim.sfoide.view.main.model.UserUiModel
import io.github.sooakim.sfoide.view.mapper.Mapper
import io.reactivex.subjects.Subject
import java.util.*

class UserListMapper(
        private val onClick: Subject<UserResponse>
) : Mapper<List<UserResponse>, List<UserUiModel>> {
    override fun mapFrom(from: List<UserResponse>): List<UserUiModel> {
        return from.map { user ->
            UserUiModel(
                    id = UUID.randomUUID().toString(),
                    profileImagePath = user.picture.thumbnail,
                    name = "${user.name.title}. ${user.name.first} ${user.name.last}",
                    gender = user.gender.toEmoji(),
                    countryFlag = user.nat.toEmoji(),
                    onClick = {
                        onClick.onNext(user)
                    }
            )
        }
    }
}