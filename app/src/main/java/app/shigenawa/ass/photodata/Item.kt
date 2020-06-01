package app.shigenawa.ass.photodata

import android.widget.ImageView
import io.realm.RealmObject


public open class Item(

    public open var image: ImageView?=null

) : RealmObject(){}