package app.shigenawa.ass.photodata

import android.net.Uri
import android.widget.ImageView
import io.realm.RealmObject

public open class Item(
    public open var uri: String?=null
): RealmObject(){}

