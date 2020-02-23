package id.oratakashi.pekalonganstore.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.oratakashi.pekalonganstore.R
import java.io.ByteArrayOutputStream


class ImageHelper {
    companion object{
        fun getPicasso(imageView: ImageView, image_url : String){
            Picasso.get().load(image_url)
                .placeholder(R.drawable.img_no_images)
                .error(R.drawable.img_no_images)
                .into(imageView, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception) {
                        Log.e("Picasso", e.message!!)
                        Log.e("Picasso_URl", image_url)
                    }
                })
        }
        fun getPicassoWithoutCache(imageView: ImageView, image_url : String){
            Picasso.get().load(image_url)
                .placeholder(R.drawable.img_no_images)
                .error(R.drawable.img_no_images)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception) {
                        Log.e("Picasso", e.message!!)
                        Log.e("Picasso_URl", image_url)
                    }
                })
        }
        fun getImageURI(context: Context, bitmap : Bitmap) : Uri{
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String =
                MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                    "Title", null)
            return Uri.parse(path)
        }
    }
}