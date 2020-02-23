package id.oratakashi.pekalonganstore.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionManager{
    companion object{

        var context: Context? = null

        fun requestPermission(activity: Activity, listener: MultiplePermissionsListener){
            this.context = context

            val perms = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )

            var permission : MutableList<String> = ArrayList()

            perms.forEach {
                permission.add(it)
            }

            Dexter.withActivity(activity)
                .withPermissions(permission)
                .withListener(listener)
                .onSameThread()
                .check()
        }

        fun cekPermission(activity: Activity) : Boolean{

            var return_type = false

            val perms = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )

            var permission : MutableList<String> = ArrayList()

            perms.forEach {
                permission.add(it)
            }

            Dexter.withActivity(activity)
                .withPermissions(permission)
                .withListener(object : MultiplePermissionsListener{
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if(report!!.areAllPermissionsGranted()){
                            return_type = true
                        }
                        if(report.isAnyPermissionPermanentlyDenied){
                            return_type = false
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()

            return return_type
        }
    }
}