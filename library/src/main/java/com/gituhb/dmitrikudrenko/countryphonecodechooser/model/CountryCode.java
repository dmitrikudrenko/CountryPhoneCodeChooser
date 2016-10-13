package com.gituhb.dmitrikudrenko.countryphonecodechooser.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CountryCode implements Comparable<CountryCode>, Parcelable {
    @SerializedName("name")
    private String name;
    @SerializedName("dial_code")
    private String phoneCode;
    @SerializedName("code")
    private String code;
    @SerializedName("is_default")
    private boolean isDefault;

    private CountryCode() {
    }

    public CountryCode(String name, String phoneCode, String code) {
        this.name = name;
        this.phoneCode = phoneCode;
        this.code = code;
    }

    @Override
    public int compareTo(@NonNull CountryCode countryCode) {
        return name.compareTo(countryCode.name);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code.toLowerCase();
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public String getPhoneCodeNonSpaced() {
        return phoneCode.replaceAll(" ", "");
    }

    public boolean isDefault() {
        return isDefault;
    }

    public char getSection() {
        return name.charAt(0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phoneCode);
        parcel.writeString(code);
        parcel.writeInt(isDefault ? 1 : 0);
    }

    public static final Creator<CountryCode> CREATOR = new Creator<CountryCode>() {
        @Override
        public CountryCode createFromParcel(Parcel source) {
            CountryCode countryCode = new CountryCode();
            countryCode.name = source.readString();
            countryCode.phoneCode = source.readString();
            countryCode.code = source.readString();
            countryCode.isDefault = source.readInt() == 1;
            return countryCode;
        }

        @Override
        public CountryCode[] newArray(int size) {
            return new CountryCode[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        return (o instanceof CountryCode) && name.equals(((CountryCode) o).name)
                && phoneCode.equals(((CountryCode) o).phoneCode);
    }
}
