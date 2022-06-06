package id.idzhami.screening_test.ui.loading

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import id.idzhami.screening_test.R
import kotlinx.android.synthetic.main.item_loading.view.*
import java.util.*

    class ViewLoadingDialog {

        lateinit var dialog: CustomDialog

        fun show(context: Context): Dialog {
            return show(context, null)
        }

        fun show(context: Context, title: CharSequence?): Dialog {
            Log.e("show","True")
            val inflater = (context as Activity).layoutInflater
            val view = inflater.inflate(R.layout.item_loading, null)
            if (title != null) {
                view.cp_title.text = title
            }

            // Card Color
            view.cp_cardview.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))

            // Progress Bar Color
//            setColorFilter(view.cp_pbar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.colorPrimary, null))

            // Text Color
            view.cp_title.setTextColor(Color.BLACK)

            dialog = CustomDialog(context)
            dialog.setContentView(view)
            dialog.show()
            return dialog
        }

        private fun setColorFilter(drawable: Drawable, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
            } else {
                @Suppress("DEPRECATION")
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }

        class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
            init {
                // Set Semi-Transparent Color for Dialog Background
                window?.decorView?.rootView?.setBackgroundResource(R.color.dialogBackground)
                window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                    insets.consumeSystemWindowInsets()
                }
            }
        }
    }