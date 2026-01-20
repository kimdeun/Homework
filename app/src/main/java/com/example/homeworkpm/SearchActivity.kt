package com.example.homeworkpm  //
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

class SearchActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchActivity> {
        override fun createFromParcel(parcel: Parcel): SearchActivity {
            return SearchActivity(parcel)
        }

        override fun newArray(size: Int): Array<SearchActivity?> {
            return arrayOfNulls(size)
        }
    }
}