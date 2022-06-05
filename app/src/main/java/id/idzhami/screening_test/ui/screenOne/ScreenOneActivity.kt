package id.idzhami.screening_test.ui.screenOne

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.networks.UserApi
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.sqLite.DbAdapter
import id.idzhami.screening_test.data.viewmodel.UserViewModel
import id.idzhami.screening_test.databinding.ActivityScreenOneBinding
import id.idzhami.screening_test.ui.base.BaseActivity
import id.idzhami.screening_test.ui.dialog.DialogStatusPalindromeFragment
import id.idzhami.screening_test.ui.screenTwo.ScreenTwoActivity
import java.io.ByteArrayOutputStream

class ScreenOneActivity : BaseActivity<UserViewModel, UserRepository>() {
    val MY_CAMERA_PERMISSION_CODE = 100
    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var binding: ActivityScreenOneBinding
    var byteArray : ByteArray? = null
    var dbhelper = DbAdapter(this).TablePersons()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_one)
        binding = ActivityScreenOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
        binding.BTNCHECK.isEnabled = false
        binding.BTNNEXT.isEnabled = false
        binding.EDNAME.addTextChangedListener {
            val ed_palindrome = binding.EDNAME.text.toString().trim()
            val ed_name = binding.EDPALINDROME.text.toString().trim()

            if (ed_name.isNotEmpty() && ed_palindrome.isNotEmpty() && it.toString()
                    .isNotEmpty()
            ) {
                binding.BTNNEXT.isEnabled = true
                binding.BTNCHECK.isEnabled = true
            } else {
                binding.BTNCHECK.isEnabled = false
                binding.BTNNEXT.isEnabled = false
            }
        }
        binding.EDPALINDROME.addTextChangedListener {
            val ed_name = binding.EDNAME.text.toString().trim()
            val ed_palindrome = binding.EDPALINDROME.text.toString().trim()

            if (ed_palindrome.isNotEmpty() && ed_name.isNotEmpty() && it.toString()
                    .isNotEmpty()
            ) {
                binding.BTNNEXT.isEnabled = true
                binding.BTNCHECK.isEnabled = true
            } else {
                binding.BTNCHECK.isEnabled = false
                binding.BTNNEXT.isEnabled = false
            }
        }
        binding.ADDIMAGE.setOnClickListener {
            selectImage()
        }
        binding.BTNCHECK.setOnClickListener{
            val ed_palindrome = binding.EDPALINDROME.text.toString().trim()
            if (CheckPalindrome(ed_palindrome)) {
                println("$ed_palindrome is a Palindrome")
                val newFragment: DialogFragment = DialogStatusPalindromeFragment()
                val args = Bundle()
                args.putString("info", "$ed_palindrome : is a Palindrome")
                args.putString("key", "Palindrome")
                newFragment.setArguments(args)
                newFragment.show(
                    (this).supportFragmentManager,
                    "BottomSheetDialog"
                )
            } else {
                println("$ed_palindrome is not a Palindrome")
                val newFragment: DialogFragment = DialogStatusPalindromeFragment()
                val args = Bundle()
                args.putString("info", "$ed_palindrome : is not a Palindrome")
                args.putString("key", "Not Palindrome")
                newFragment.setArguments(args)
                newFragment.show(
                    (this).supportFragmentManager,
                    "BottomSheetDialog"
                )
            }
        }
        binding.BTNNEXT.setOnClickListener {
            val ed_name = binding.EDPALINDROME.text.toString().trim()
            var addperson = dbhelper.add(ed_name,byteArray!!)
            val intent = Intent(this@ScreenOneActivity, ScreenTwoActivity::class.java)
            intent.putExtra("id", addperson)
            startActivity(intent)

        }
    }

    private fun CheckPalindrome(input: String): Boolean {
        val sb = StringBuilder(input)
        val reverseStr = sb.reverse().toString()
        return input.equals(reverseStr, ignoreCase = true)
    }

    override fun setupData() {

    }

    private fun takeCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@ScreenOneActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                /*Code for camera use permission check */
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA),
                        MY_CAMERA_PERMISSION_CODE
                    )
                } else {
                    takeCamera()
                }
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, 2)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                takeCamera()
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }


    @SuppressLint("LongLogTag")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    /* the code to display the image from the camera to the viewImage we prepared earlier */
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val stream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArray = stream.toByteArray()
                    binding.ADDIMAGE.setImageBitmap(imageBitmap)
                }
            } else if (requestCode == 2) {
                val selectedImage = data?.data
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c: Cursor? = contentResolver.query(selectedImage!!, filePath, null, null, null)
                if (c != null) {
                    c.moveToFirst()
                }
                val columnIndex: Int? = c?.getColumnIndex(filePath[0])
                val picturePath: String? = columnIndex?.let { c?.getString(it) }
                if (c != null) {
                    c.close()
                }
                /*the code to display the image from the gallery to the Image view that we prepared earlier*/
                val image = BitmapFactory.decodeFile(picturePath)
                Log.w(
                    "path of image from gallery : ",
                    picturePath + ""
                )
                Log.d("image : " , image.toString())
                val stream = ByteArrayOutputStream()
                image.compress(Bitmap.CompressFormat.PNG, 100, stream)
                 byteArray = stream.toByteArray()
                binding.ADDIMAGE.setImageBitmap(image)
            }
        }
    }
    private fun ADD_PERSON(
        name: String,
        image: ByteArray,
    ) {
        var addperson = dbhelper.add(name,image)

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        this.let { it1 ->
            Snackbar.make(
                it1.findViewById(android.R.id.content),
                getString(R.string.label_close_label),
                Snackbar.LENGTH_INDEFINITE
            ).setDuration(8000).show()
        }
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun getViewModel() = UserViewModel::class.java

    override fun getActivityRepository(): UserRepository {
        val api =
            remoteDataSource.buildApi(
                this,
                UserApi::class.java,
                userPreferences,
            )
        return UserRepository(api, userPreferences)
    }
}