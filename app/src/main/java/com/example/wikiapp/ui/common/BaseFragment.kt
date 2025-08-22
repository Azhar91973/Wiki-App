package com.example.wikiapp.ui.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding
            ?: throw IllegalStateException("Binding is accessed before it's initialized or after it's destroyed")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun setUpViews()

    abstract fun setUpClickListeners()

    abstract fun setUpObservers()

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showLog(tag: String, message: String) {
        Log.d(tag, "showLog: $message")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
