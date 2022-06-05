package id.idzhami.screening_test.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import id.idzhami.screening_test.R
import kotlinx.android.synthetic.main.fragment_dialog_status_palindrome.view.*


class DialogStatusPalindromeFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_status_palindrome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        setupView(view)
    }

    private fun setupView(view: View) {
        view.TXT_STATUS_PALINDROME.text = arguments?.getString("info")
        val value = arguments?.getString("key")
        Log.d("data : ", value.toString())
        if (value.toString() == "Palindrome") {
            view.IMG_RIGHT.isVisible = true
        } else {
            view.IMG_WRONG.isVisible = true
        }
        Handler().postDelayed({
        dismiss()

        }, 3000)
    }
}