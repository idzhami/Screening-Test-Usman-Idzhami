package id.idzhami.screening_test.ui.screenFour

import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import com.google.gson.Gson
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.adapter.GridViewAdapterScreenFour
import id.idzhami.screening_test.data.networks.Resource
import id.idzhami.screening_test.data.networks.UserApi
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.viewmodel.UserViewModel
import id.idzhami.screening_test.databinding.ActivityScreenFourBinding
import id.idzhami.screening_test.databinding.ActivityScreenTwoBinding
import id.idzhami.screening_test.utils.handleApiError
import id.idzhami.screening_test.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_screen_four.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenFourActivity : BaseActivity<UserViewModel, UserRepository>() {
    private lateinit var binding: ActivityScreenFourBinding
    private val pageStart: Int = 0
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
    private var currentPage: Int = pageStart
    private var totalPages: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_four)
        binding = ActivityScreenFourBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setupData()
        observeData()
        binding.swipeRefreshLayout?.setOnRefreshListener {
            binding.swipeRefreshLayout!!.isRefreshing = false
            setupData()
            binding.gridView.startLayoutAnimation()
        }

    }

    override fun setupData() {
        viewModel.GET_USER(1, 10)
    }

    private fun observeData() {
        viewModel._userResponses.observe(this) {
            when (it) {
                is Resource.Loading -> {  progressDialog.show(this)}
                is Resource.Success -> {
                    progressDialog.dialog.dismiss()
                    totalPages = it.value.total_pages
                    Log.d("data respon : ", it.toString())
                    val mainAdapter = GridViewAdapterScreenFour(this@ScreenFourActivity, it.value)
                    gridView.adapter = mainAdapter
                }

                is Resource.Failure -> {
                    progressDialog.dialog.dismiss()
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

    fun loadNextPage() {
        currentPage += 1
        if (currentPage < totalPages) {
            progressDialog.show(this)
            lifecycle.coroutineScope.launch(dispatcher) {
                delay(300L)
                viewModel.GET_USER(currentPage, 10)
            }
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.primary_orange)))
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        binding.toolbar.navigationIcon?.setColorFilter(
            ContextCompat.getColor(
                this,
                R.color.white
            ), PorterDuff.Mode.SRC_ATOP
        )
        supportActionBar?.apply {
            title = getString(R.string.label_guest)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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