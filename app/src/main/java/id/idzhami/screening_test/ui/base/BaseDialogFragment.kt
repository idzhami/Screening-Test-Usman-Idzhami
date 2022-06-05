package id.idzhami.screening_test.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment  < B : ViewBinding> : DialogFragment(){
    protected lateinit var binding: B
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = getFragmentBinding(inflater, container)
        return binding.root
    }
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B
}