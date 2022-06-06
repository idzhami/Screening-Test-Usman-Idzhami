package id.idzhami.screening_test.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import id.idzhami.screening_test.data.networks.Resource

import java.util.HashMap


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    var userPreferences = UserPreferences
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    Log.e("Action", action.toString())
    action?.let {
        snackbar.setAction("Coba Lagi") {
            it()
        }
    }
    snackbar.show()

}

fun showAlert(
    context: Context?, title: String?, text: String?, positive: String?,
    positiveClick: DialogInterface.OnClickListener?,
    onBackPressed: DialogInterface.OnKeyListener?
) {
    val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
    alertBuilder.setTitle(title)
    alertBuilder.setMessage(text)
    alertBuilder.setCancelable(false)
    alertBuilder.setOnKeyListener(onBackPressed)
    alertBuilder.setPositiveButton(positive, positiveClick)
    alertBuilder.create().show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    try {
        when {
            failure.isNetworkError -> {
                    requireView().snackbar(
                        "Please check your internet connection",
                        retry
                    )
            }
        }
        val errorawal = failure.errorBody!!.toString()
        val gson = Gson()
        var map: Map<String?, Any?> = HashMap()
        map = gson.fromJson(errorawal, map.javaClass) as Map<String?, Any?>
        requireView().snackbar(map["detail"].toString())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


