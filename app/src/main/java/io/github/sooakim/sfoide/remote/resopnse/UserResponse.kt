package io.github.sooakim.sfoide.remote.resopnse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.github.sooakim.sfoide.utils.Emojiable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(
        @field:SerializedName("nat")
        val nat: Nation,

        @field:SerializedName("gender")
        val gender: Gender,

        @field:SerializedName("phone")
        val phone: String,

        @field:SerializedName("dob")
        val dob: Dob,

        @field:SerializedName("name")
        val name: Name,

        @field:SerializedName("registered")
        val registered: Registered,

        @field:SerializedName("location")
        val location: Location,

        @field:SerializedName("id")
        val id: Id,

        @field:SerializedName("login")
        val login: Login,

        @field:SerializedName("cell")
        val cell: String,

        @field:SerializedName("email")
        val email: String,

        @field:SerializedName("picture")
        val picture: Picture
) : Parcelable {
    @Parcelize
    enum class Gender: Parcelable, Emojiable{
        @SerializedName("male")
        MALE,

        @SerializedName("female")
        FEMALE;

        override fun toEmoji(): String{
            return when(this){
                FEMALE -> "\uD83D\uDC69"
                MALE -> "\uD83D\uDC68"
            }
        }
    }

    @Parcelize
    enum class Nation: Parcelable, Emojiable{
        @SerializedName("AU")
        AU,
        @SerializedName("BR")
        BR,
        @SerializedName("CA")
        CA,
        @SerializedName("CH")
        CH,
        @SerializedName("DE")
        DE,
        @SerializedName("DK")
        DK,
        @SerializedName("ES")
        ES,
        @SerializedName("FI")
        FI,
        @SerializedName("FR")
        FR,
        @SerializedName("GB")
        GB,
        @SerializedName("IE")
        IE,
        @SerializedName("IR")
        IR,
        @SerializedName("NO")
        NO,
        @SerializedName("NL")
        NL,
        @SerializedName("NZ")
        NZ,
        @SerializedName("TR")
        TR,
        @SerializedName("US")
        US;

        override fun toEmoji(): String {
            return when(this){
                AU -> "\uD83C\uDDE6\uD83C\uDDFA"
                BR -> "\uD83C\uDDE7\uD83C\uDDF7"
                CA -> "\uD83C\uDDE8\uD83C\uDDE6"
                CH -> "\uD83C\uDDE8\uD83C\uDDED"
                DE -> "\uD83C\uDDE9\uD83C\uDDEA"
                DK -> "🇩🇰"
                ES -> "🇪🇸"
                FR -> "🇫🇷"
                GB -> "🇬🇧"
                IE -> "🇮🇪"
                IR -> "🇮🇷"
                NO -> "🇳🇴"
                NL -> "🇳🇱"
                NZ -> "🇳🇿"
                TR -> "🇹🇷"
                US -> "🇺🇸"
                else -> "Unkown"
            }
        }
    }

    @Parcelize
    data class Dob(

            @field:SerializedName("date")
            val date: String,

            @field:SerializedName("age")
            val age: Int
    ) : Parcelable

    @Parcelize
    data class Id(

            @field:SerializedName("name")
            val name: String,

            @field:SerializedName("value")
            val value: String?
    ) : Parcelable

    @Parcelize
    data class Location(

            @field:SerializedName("city")
            val city: String,

            @field:SerializedName("street")
            val street: Street,

            @field:SerializedName("timezone")
            val timezone: Timezone,

            @field:SerializedName("postcode")
            val postcode: String,

            @field:SerializedName("coordinates")
            val coordinates: Coordinates,

            @field:SerializedName("state")
            val state: String
    ) : Parcelable {
        @Parcelize
        data class Street(
                @field:SerializedName("number")
                val number: String,

                @field:SerializedName("name")
                val name: String
        ) : Parcelable

        @Parcelize
        data class Coordinates(

                @field:SerializedName("latitude")
                val latitude: String,

                @field:SerializedName("longitude")
                val longitude: String
        ) : Parcelable
    }

    @Parcelize
    data class Login(

            @field:SerializedName("sha1")
            val sha1: String,

            @field:SerializedName("password")
            val password: String,

            @field:SerializedName("salt")
            val salt: String,

            @field:SerializedName("sha256")
            val sha256: String,

            @field:SerializedName("uuid")
            val uuid: String,

            @field:SerializedName("username")
            val username: String,

            @field:SerializedName("md5")
            val md5: String
    ) : Parcelable

    @Parcelize
    data class Name(

            @field:SerializedName("last")
            val last: String,

            @field:SerializedName("title")
            val title: String,

            @field:SerializedName("first")
            val first: String
    ) : Parcelable

    @Parcelize
    data class Picture(

            @field:SerializedName("thumbnail")
            val thumbnail: String,

            @field:SerializedName("large")
            val large: String,

            @field:SerializedName("medium")
            val medium: String
    ) : Parcelable

    @Parcelize
    data class Registered(

            @field:SerializedName("date")
            val date: String,

            @field:SerializedName("age")
            val age: Int
    ) : Parcelable

    @Parcelize
    data class Timezone(

            @field:SerializedName("offset")
            val offset: String,

            @field:SerializedName("description")
            val description: String
    ) : Parcelable
}