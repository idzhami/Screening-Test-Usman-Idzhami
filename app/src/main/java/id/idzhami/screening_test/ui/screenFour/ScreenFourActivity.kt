package id.idzhami.screening_test.ui.screenFour

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.adapter.GridViewAdapterScreenFour
import id.idzhami.screening_test.data.networks.Resource
import id.idzhami.screening_test.data.networks.UserApi
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.viewmodel.UserViewModel
import id.idzhami.screening_test.databinding.ActivityScreenFourBinding
import id.idzhami.screening_test.utils.handleApiError
import id.idzhami.screening_test.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_screen_four.*

class ScreenFourActivity : BaseActivity<UserViewModel, UserRepository>() {
    private lateinit var binding: ActivityScreenFourBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_four)
        setupData()
        observeData()
    }

    override fun setupData() {
        viewModel.GET_USER(1, 10)
    }

    private fun observeData() {
        viewModel._userResponses.observe(this) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("data respon : ", it.toString())
                    val mainAdapter = GridViewAdapterScreenFour(this@ScreenFourActivity, it.value)
                    gridView.adapter = mainAdapter
                }

                is Resource.Failure -> {
                    val error = it.errorBody!!.string()
                    val gson = Gson()
                    var map: Map<String?, Any?> = java.util.HashMap()
                    map = gson.fromJson(error, map.javaClass) as Map<String?, Any?>
                    Toast.makeText(
                        this,
                        map["detail"].toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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