package id.idzhami.screening_test.ui.screenTwo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.networks.UserApi
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.sqLite.DbAdapter
import id.idzhami.screening_test.data.viewmodel.UserViewModel
import id.idzhami.screening_test.databinding.ActivityScreenTwoBinding
import id.idzhami.screening_test.ui.base.BaseActivity
import id.idzhami.screening_test.ui.screenFour.ScreenFourActivity
import id.idzhami.screening_test.ui.screenThree.ScreenThreeActivity
import kotlinx.coroutines.runBlocking


class ScreenTwoActivity : BaseActivity<UserViewModel, UserRepository>() {
    private lateinit var binding: ActivityScreenTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_two)
        binding = ActivityScreenTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras

        val name = runBlocking { userPreferences.getName() }
        binding.TXTNAME.setText(name)

        binding.BTNCHOOSEEVENT.setOnClickListener {
            val intent = Intent(this@ScreenTwoActivity, ScreenThreeActivity::class.java)
            startActivity(intent)
        }
        binding.BTNCHOOSEGUEST.setOnClickListener {
            val intent = Intent(this@ScreenTwoActivity, ScreenFourActivity::class.java)
            startActivity(intent)
        }
    }

    /*get Image from User Preferences*/
    fun decodeBase64(input: String?): Bitmap? {
        val decodedByte: ByteArray = Base64.decode(input, 0)
        binding.img.setImageBitmap(BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size))
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    override fun setupData() {
        TODO("Not yet implemented")
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