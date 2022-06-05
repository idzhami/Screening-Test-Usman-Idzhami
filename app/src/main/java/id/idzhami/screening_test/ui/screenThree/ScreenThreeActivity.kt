package id.idzhami.screening_test.ui.screenThree

import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.adapter.RecycleViewAdapterScreenThree
import id.idzhami.screening_test.data.networks.UserApi
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.sqLite.DbAdapter
import id.idzhami.screening_test.data.viewmodel.UserViewModel
import id.idzhami.screening_test.databinding.ActivityScreenThreeBinding
import id.idzhami.screening_test.ui.base.BaseActivity
import id.idzhami.screening_test.ui.base.BaseFragment
import id.idzhami.screening_test.ui.fragments.ListEventsFragment
import id.idzhami.screening_test.ui.fragments.MapViewFragment
import kotlin.properties.Delegates


class ScreenThreeActivity : BaseActivity<UserViewModel, UserRepository>() {
    private lateinit var binding: ActivityScreenThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_three)
        binding = ActivityScreenThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        val ListEventsFragment = ListEventsFragment()
        val MapViewFragment = MapViewFragment()
        setFragment(ListEventsFragment)
        binding.BTNMAP.setOnClickListener {
            //ganti fragments
            setFragment(MapViewFragment)
            it.visibility = View.GONE
            binding.BTNLISTS.visibility = View.VISIBLE

        }
        binding.BTNLISTS.setOnClickListener {
            setFragment(ListEventsFragment)
            it.visibility = View.GONE
            binding.BTNMAP.visibility = View.VISIBLE

        }

        binding.BTNSEARCHING.setOnClickListener {
            Toast.makeText(
                this,
                R.string.label_update,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.fragmentContainer, fragment, "bottomNavigation")
                commit()
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
            title = getString(R.string.label_event)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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