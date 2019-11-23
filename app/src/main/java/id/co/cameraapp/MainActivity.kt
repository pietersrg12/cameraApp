package id.co.cameraapp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val REQUEST_PERMISSION_CODE = 100
    val REQUEST_CAMERA_CODE = 100
    var currentPhoto: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check perizinan
        val daftarIzin = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(android.Manifest.permission.CAMERA)
        }
        if (daftarIzin.size>0){
            val iz = arrayOfNulls<String>(daftarIzin.size)
            for (i in 0 until daftarIzin.size){
                iz[i] = daftarIzin.get(i)
            }
            ActivityCompat.requestPermissions(this, iz, REQUEST_PERMISSION_CODE)
        }else{
            //do nothing
        }
        //tambahkan fungsi click pada Imageview (dengan id foto, jadi ketika diklik, akan memmanggil fungsi takePicture yaitu membuka aplikasi
        //kamerea android dan file foto disimpan dengan nama file foto1.jpg
        foto.setOnClickListener {
            takepicture("foto1.jpg")
        }
    }
    fun takepicture(namafile: String){
        //persiapan untuk buka aplikasi kamera bawaan android
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //siapkan file yang akan menyimpan hasil foto
        val filePhoto = File(getExternalFilesDir( null),namafile)
        //siapkan public URI, jadi aplikasi kamera bisa nyimpan hasil foto di folder aplikasi kita
        val uriPhoto = FileProvider.getUriForFile(this,
            "id.co.plnntb.camera1.fileprovider",
            filePhoto
        )
        //ambil lokasi file foto tersebut, untuk ditampilkan nanti di ImageView
        currentPhoto = filePhoto.absolutePath
        //infokan ke aplikasi kamera lokasi tempat nyimpan hasil fotonya
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriPhoto)
        //start aplikasi kamera
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }
}
