package id.idzhami.screening_test.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import id.idzhami.screening_test.R
import id.idzhami.screening_test.data.sqLite.DbAdapter
import id.idzhami.screening_test.databinding.FragmentDialogEventBinding
import id.idzhami.screening_test.ui.base.BaseDialogFragment
import id.idzhami.screening_test.ui.screenThree.ScreenThreeActivity
import id.idzhami.screening_test.utils.enable


class DialogEventFragment : BaseDialogFragment<FragmentDialogEventBinding>() {
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        val id =  arguments?.getString("id")
        val lat =  arguments?.getString("lat")
        val long =  arguments?.getString("long")
        binding.EDLATITUDE.setText(lat)
        binding.EDLONGITUDE.setText(long)
        binding.BTNSAVE.enable(false)
        binding.EDLATITUDE.addTextChangedListener {
            val EDLATITUDE = binding.EDLATITUDE.text.toString().trim()
            val EDLONGITUDE = binding.EDLONGITUDE.text.toString().trim()

            binding.BTNSAVE.enable(
                EDLATITUDE.isNotEmpty() && EDLONGITUDE.isNotEmpty() && it.toString()
                    .isNotEmpty()
            )
        }
        binding.EDLONGITUDE.addTextChangedListener {
            val EDLATITUDE = binding.EDLATITUDE.text.toString().trim()
            val EDLONGITUDE = binding.EDLONGITUDE.text.toString().trim()

            binding.BTNSAVE.enable(
                EDLATITUDE.isNotEmpty() && EDLONGITUDE.isNotEmpty() && it.toString()
                    .isNotEmpty()
            )
        }
        binding.BTNSAVE.setOnClickListener {
            val EDLATITUDE = binding.EDLATITUDE.text.toString().trim()
            val EDLONGITUDE = binding.EDLONGITUDE.text.toString().trim()

            val db = DbAdapter(requireContext())
            val updateData = db.TableEvents().update(id.toString(),EDLATITUDE.toDouble(),EDLONGITUDE.toDouble())
            Handler().postDelayed({
                Toast.makeText(
                    requireContext(),
                    "Success",
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }, 1000)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentDialogEventBinding.inflate(inflater,container,false)
}