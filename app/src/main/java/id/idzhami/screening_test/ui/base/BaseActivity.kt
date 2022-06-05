package id.idzhami.screening_test.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import id.idzhami.screening_test.data.networks.RemoteDataSource
import id.idzhami.screening_test.data.repository.BaseRepository
import id.idzhami.screening_test.utils.Key
import id.idzhami.screening_test.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
/**
 * Created by Usman idzhami On 04/06/2022
 */
abstract class BaseActivity<VM : ViewModel, R: BaseRepository> : AppCompatActivity() {

    protected lateinit var viewModel:VM
    protected val remoteDataSource = RemoteDataSource()
    protected lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences(this)
        val factory = ViewModelFactory(getActivityRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
    }

    abstract fun setupData()

    abstract fun getViewModel() : Class<VM>

    abstract fun getActivityRepository(): R

    open fun gotoActivityWithObject(
        context: Context?,
        destActivity: Class<*>?,
        intentData: Parcelable?
    ) {
        val intent = Intent(context, destActivity)
        intent.putExtra(Key.INTENT_DATA, intentData)
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}