package com.github.dmitrikudrenko.countryphonecodechooser.model

import android.os.Parcel
import com.github.dmitrikudrenko.countryphonecodechooser.utils.KParcelable
import com.github.dmitrikudrenko.countryphonecodechooser.utils.parcelableCreator
import com.google.gson.annotations.SerializedName

class CountryCode private constructor(
        @SerializedName("name")
        var name: String = "",
        @SerializedName("dial_code")
        var phoneCode: String = "",
        @SerializedName("code")
        var code: String = "",
        @SerializedName("is_default")
        private var isDefault: Boolean = false
) : Comparable<CountryCode>, KParcelable {

    val section: Char?
        get() = name[0]

    override fun compareTo(other: CountryCode): Int {
        return name.compareTo(other.name)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(phoneCode)
        writeString(code)
        writeInt(if (isDefault) 1 else 0)
    }

    override fun equals(other: Any?): Boolean {
        return (other is CountryCode && name == other.name
                && phoneCode == other.phoneCode)
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + phoneCode.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + isDefault.hashCode()
        return result
    }

    private constructor(p: Parcel) : this(
            p.readString(),
            p.readString(),
            p.readString(),
            p.readInt() == 1
    )

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::CountryCode)
    }
}
