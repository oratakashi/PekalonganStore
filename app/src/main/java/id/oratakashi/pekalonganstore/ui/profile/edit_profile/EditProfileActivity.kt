package id.oratakashi.pekalonganstore.ui.profile.edit_profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.oratakashi.pekalonganstore.R
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.root.App
import id.oratakashi.pekalonganstore.ui.dialog.upload_profile.UploadProfileFragment
import id.oratakashi.pekalonganstore.ui.dialog.upload_profile.UploadProfileInterface
import id.oratakashi.pekalonganstore.ui.region.SubdistrictFragment
import id.oratakashi.pekalonganstore.ui.region.SubdistrictInterface
import id.oratakashi.pekalonganstore.utils.FileUtils
import id.oratakashi.pekalonganstore.utils.ImageHelper
import id.oratakashi.pekalonganstore.utils.PermissionManager
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), SubdistrictInterface.Activity,
    UploadProfileInterface, MultiplePermissionsListener {

    lateinit var viewModel: EditProfileViewModel

    private var uri: Uri? = null

    private val pickImage = 1
    private val captureImage = 2

    private var subdiscrict_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        ButterKnife.bind(this)

        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)

        shPhoto.startShimmerAnimation()

        setupViewModel()
    }

    fun setupViewModel(){
        viewModel.showMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })

        /**
         * Get Profile Observer
         */

        viewModel.errorProfile.observe(this, Observer { error ->
            error?.let {
                if(it){
                    Snackbar.make(srProfile, "Gagal memuat data!", Snackbar.LENGTH_SHORT).show()
                    etName.setText(App.sessions!!.getString(Sessions.name))
                    etEmail.setText(App.sessions!!.getString(Sessions.email))
                    etAddress.setText(App.sessions!!.getString(Sessions.address))
                    etPhone.setText(App.sessions!!.getString(Sessions.phone))
                    etLocation.setText(
                        when(App.sessions!!.getString(Sessions.subdistrict_id).isNotEmpty()){
                            true -> {
                                "Tidak diketahui"
                            }
                            false -> {
                                "${App.sessions!!.getString(Sessions.district)}, " +
                                "${App.sessions!!.getString(Sessions.regency)}, " +
                                 App.sessions!!.getString(Sessions.province)
                            }
                        }
                    )
                }
            }
        })
        viewModel.progressProfile.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        llContent.visibility = View.GONE
                        shProfile.visibility = View.VISIBLE
                        ivPhoto.visibility = View.GONE
                        shPhoto.visibility = View.VISIBLE
                        shPhoto.startShimmerAnimation()
                        shProfile.startShimmerAnimation()
                    }
                    false -> {
                        shProfile.stopShimmerAnimation()
                        shPhoto.stopShimmerAnimation()
                        shPhoto.visibility = View.GONE
                        ivPhoto.visibility = View.VISIBLE
                        shProfile.visibility = View.GONE
                        llContent.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.responseProfile.observe(this, Observer { response ->
            response?.let {
                when(it.status){
                    true -> {
                        etName.setText(it.data!!.name)
                        etEmail.setText(it.data.email)
                        etPhone.setText(
                            when(it.data.phone.isNullOrEmpty()){
                                true -> {
                                    ""
                                }
                                false -> {
                                    it.data.phone
                                }
                            }
                        )
                        etAddress.setText(
                            when(it.data.address.isNullOrEmpty()){
                                true -> {
                                    ""
                                }
                                false -> {
                                    it.data.address
                                }
                            }
                        )
                        etLocation.setText(
                            when(it.data.subdistrict != null){
                                true -> {
                                    "${it.data.subdistrict.subdistrict_name}, " +
                                    "${it.data.subdistrict.type} ${it.data.subdistrict.city}, " +
                                    it.data.subdistrict.province
                                }
                                false -> {
                                    ""
                                }
                            }
                        )

                        when(it.data.photo.isNullOrEmpty()){
                            true -> {
                                ivPhoto.setImageDrawable(getDrawable(R.drawable.no_pict))
                            }
                            false -> {
                                ImageHelper.getPicassoWithoutCache(ivPhoto, it.data.photo)
                                App.sessions!!.putString(Sessions.photo, it.data.photo)
                            }
                        }

                        /**
                         * Saving data to sessions
                         */
                        when(it.data.address.isNullOrEmpty()){
                            false -> {
                                App.sessions!!.putString(Sessions.address, it.data.address)
                            }
                        }

                        when(it.data.subdistrict != null){
                            true -> {
                                App.sessions!!.putString(Sessions.subdistrict_id,
                                    it.data.subdistrict.subdistrict_id!!)
                                App.sessions!!.putString(Sessions.district,
                                    it.data.subdistrict.subdistrict_name!!)
                                App.sessions!!.putString(Sessions.regency,
                                    it.data.subdistrict.city!!)
                                App.sessions!!.putString(Sessions.province,
                                    it.data.subdistrict.province!!)
                                App.sessions!!.putString(Sessions.type,
                                    it.data.subdistrict.type!!)
                                subdiscrict_id = it.data.subdistrict.subdistrict_id
                            }
                        }

                        when(it.data.phone != null){
                            true -> {
                                App.sessions!!.putString(Sessions.phone,
                                    it.data.phone)
                            }
                        }

                        App.sessions!!.putString(Sessions.name, it.data.name!!)
                        App.sessions!!.putString(Sessions.email, it.data.email!!)
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        /**
         * Delete Photo Profile Observer
         */

        viewModel.progressDeletePhoto.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        ivPhoto.visibility = View.GONE
                        ivPreview.setImageDrawable(getDrawable(R.drawable.no_pict))
                        shPhoto.visibility = View.VISIBLE
                        shPhoto.startShimmerAnimation()
                    }
                    false -> {
                        shPhoto.startShimmerAnimation()
                        shPhoto.visibility = View.GONE
                        ivPhoto.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.errorDeletePhoto.observe(this, Observer {error ->
            error?.let {
                if(it) Snackbar.make(srProfile, "Uppss, Sepertinya penghapusan foto gagal!",
                    Snackbar.LENGTH_SHORT).setAction("Coba Lagi"){
                    viewModel.deletePhoto()
                }.show()
            }
        })
        viewModel.responseDeletePhoto.observe(this, Observer { response ->
            response?.let{
                when(it.status){
                    true -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        ivPhoto.setImageDrawable(getDrawable(R.drawable.no_pict))
                        App.sessions!!.putString(Sessions.photo, "")
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        /**
         * Update Photo Profile Observer
         */

        viewModel.progressUpdatePhoto.observe(this, Observer { progress ->
            progress?.let {
                when(it){
                    true -> {
                        ivPhoto.visibility = View.GONE
                        shPhoto.visibility = View.VISIBLE
                        shPhoto.startShimmerAnimation()
                    }
                    false -> {
                        shPhoto.startShimmerAnimation()
                        shPhoto.visibility = View.GONE
                        ivPhoto.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.errorUpdatePhoto.observe(this, Observer { error ->
            error?.let {
                if(it) Toast.makeText(applicationContext, "Gagal mengupdate foto profil!",
                    Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.responseUpdatePhoto.observe(this, Observer { response ->
            response?.let {
                when(it.status){
                    true -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        App.sessions!!.putString(Sessions.photo, it.data!!.url!!)
                        ImageHelper.getPicassoWithoutCache(ivPhoto, it.data!!.url!!)
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        /**
         * Update Profile Observer
         */

        viewModel.progressUpdateProfile.observe(this, Observer { progress ->
            progress?.let{
                when(it){
                    true -> {
                        llContent.visibility = View.GONE
                        shProfile.visibility = View.VISIBLE
                        shProfile.startShimmerAnimation()
                    }
                    false -> {
                        shProfile.stopShimmerAnimation()
                        shProfile.visibility = View.GONE
                        llContent.visibility = View.VISIBLE
                    }
                }
            }
        })
        viewModel.errorUpdateProfile.observe(this, Observer { error ->
            error?.let{
                if(it) Snackbar.make(srProfile, "Gagal memperbarui profil!", Snackbar.LENGTH_SHORT)
                    .setAction("Coba Lagi"){
                        viewModel.putProfile(
                            etName.text.toString(),
                            etEmail.text.toString(),
                            etPhone.text.toString(),
                            etAddress.text.toString(),
                            subdiscrict_id
                        )
                    }
            }
        })
        viewModel.responseUpdateProfile.observe(this, Observer { response ->
            response?.let{
                when(it.status){
                    true -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        etName.setText(it.data!!.name)
                        etEmail.setText(it.data.email)
                        etPhone.setText(
                            when(it.data.phone.isNullOrEmpty()){
                                true -> {
                                    ""
                                }
                                false -> {
                                    it.data.phone
                                }
                            }
                        )
                        etAddress.setText(
                            when(it.data.address.isNullOrEmpty()){
                                true -> {
                                    ""
                                }
                                false -> {
                                    it.data.address
                                }
                            }
                        )
                        etLocation.setText(
                            when(it.data.subdistrict != null){
                                true -> {
                                    "${it.data.subdistrict.subdistrict_name}, " +
                                            "${it.data.subdistrict.type} ${it.data.subdistrict.city}, " +
                                            it.data.subdistrict.province
                                }
                                false -> {
                                    ""
                                }
                            }
                        )

                        when(it.data.photo.isNullOrEmpty()){
                            true -> {
                                ivPhoto.setImageDrawable(getDrawable(R.drawable.no_pict))
                            }
                            false -> {
                                ImageHelper.getPicassoWithoutCache(ivPhoto, it.data.photo)
                                App.sessions!!.putString(Sessions.photo, it.data.photo)
                            }
                        }

                        /**
                         * Saving data to sessions
                         */
                        when(it.data.address.isNullOrEmpty()){
                            false -> {
                                App.sessions!!.putString(Sessions.address, it.data.address)
                            }
                        }

                        when(it.data.subdistrict != null){
                            true -> {
                                App.sessions!!.putString(Sessions.subdistrict_id,
                                    it.data.subdistrict.subdistrict_id!!)
                                App.sessions!!.putString(Sessions.district,
                                    it.data.subdistrict.subdistrict_name!!)
                                App.sessions!!.putString(Sessions.regency,
                                    it.data.subdistrict.city!!)
                                App.sessions!!.putString(Sessions.province,
                                    it.data.subdistrict.province!!)
                                App.sessions!!.putString(Sessions.type,
                                    it.data.subdistrict.type!!)
                            }
                        }

                        when(it.data.phone != null){
                            true -> {
                                App.sessions!!.putString(Sessions.phone,
                                    it.data.phone)
                            }
                        }

                        App.sessions!!.putString(Sessions.name, it.data.name!!)
                        App.sessions!!.putString(Sessions.email, it.data.email!!)

                        finish()
                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        /**
         * Call Api for Current Data
         */
        viewModel.getProfile()
    }

    /**
     * On Select Region
     */
    override fun onSelect(
        subdiscrict_id: String,
        provinsi: String,
        kota: String,
        kecamatan: String
    ) {
        this.subdiscrict_id = subdiscrict_id
        etLocation.setText("$kecamatan, $kota, $provinsi")
    }

    /**
     * On Select Dialog Photo Profile
     */
    override fun onSelect(option: String) {
        when(option){
            "galery" -> {
                if(PermissionManager.cekPermission(this)){
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), pickImage)
                }else{
                    PermissionManager.requestPermission(this, this)
                }
            }
            "camera" -> {
                if(PermissionManager.cekPermission(this)){
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, captureImage)
                }else{
                    PermissionManager.requestPermission(this, this)
                }
            }
            "delete" -> {
                viewModel.deletePhoto()
            }
        }
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if(report!!.isAnyPermissionPermanentlyDenied){
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 0)
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        token!!.continuePermissionRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pickImage && resultCode == RESULT_OK){
            uri = data!!.data
            ivPreview.setImageURI(uri)
            viewModel.updatePhoto(FileUtils.getFile(this, uri))
        }else if(requestCode == captureImage && resultCode == RESULT_OK){
            val photo = data!!.extras!!.get("data") as Bitmap
            ivPreview.setImageBitmap(photo)
            viewModel.updatePhoto(FileUtils.getFile(this, ImageHelper.getImageURI(
                applicationContext, photo
            )))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save -> {
                if(subdiscrict_id.isEmpty() || etName.text!!.isEmpty() || etEmail.text!!.isEmpty() ||
                    etPhone.text!!.isEmpty() || etAddress.text!!.isEmpty()){
                    Toast.makeText(applicationContext, "Silahkan lengkapi data untuk menyimpan profile kamu!",
                        Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.putProfile(
                        etName.text.toString(),
                        etEmail.text.toString(),
                        etPhone.text.toString(),
                        etAddress.text.toString(),
                        subdiscrict_id
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @OnClick(R.id.etLocation) fun onSearch(){
        SubdistrictFragment.newInstance(this).show(supportFragmentManager, "dialog")
    }

    @OnClick(R.id.ivChangePhoto) fun onChangePhoto(){
        UploadProfileFragment.newInstance(this).show(supportFragmentManager, "dialog")
    }
}
