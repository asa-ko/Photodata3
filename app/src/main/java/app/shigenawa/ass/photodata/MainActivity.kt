package app.shigenawa.ass.photodata

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toFile
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val realm:Realm=Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val item:Item?=read()

        if(item!=null) {
            val uried:Uri= Uri.parse(item.uri)
            imageView.setImageURI(uried)
        }
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            selectPhoto()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


   fun save(uri: String){
        val item:Item?=read()
            //保存する処理
            realm.executeTransaction {
               if (item != null) {
                    item.uri=uri
                }else{
                    val newItem: Item=realm.createObject(Item::class.java)
                    newItem.uri=uri
               }
                Snackbar.make(imageView,"保存しました",Snackbar.LENGTH_SHORT).show()
            }
       //保存したuriのStringをUriに戻す
    val newUri:Uri= Uri.parse(uri)

    }


    fun read():Item?{
        return realm.where(Item::class.java).findFirst()
    }


   /* fun createImageData() : ByteArray {
        val bitmap = (imageView.drawable)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val imageByteArray = baos.toByteArray()
        return imageByteArray
    }*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    data?.data?.also { uri ->

                       /* realm.executeTransaction {
                            val stringUri:String=uri.toString()
                            //val newItem = realm.createObject(Item::class.java)
                            //newItem.uri=item.uri
                            save(stringUri)
                        }
                        */
                        val stringUri:String=uri.toString()
                        save(stringUri)



                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        val imageView = findViewById<ImageView>(R.id.imageView)
                        imageView.setImageBitmap(image)
                        
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }
}