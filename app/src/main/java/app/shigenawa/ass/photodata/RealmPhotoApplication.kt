package app.shigenawa.ass.photodata

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmPhotoApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        //Realmの初期化
        Realm.init(this)

        //保存するモデルに変更を加えたときアプリを削除して再インストールする手間を省く
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

}