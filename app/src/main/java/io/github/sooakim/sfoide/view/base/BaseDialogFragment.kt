package io.github.sooakim.sfoide.view.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import io.github.sooakim.sfoide.BR

abstract class BaseDialogFragment<VDB: ViewDataBinding, VM: BaseViewModel>(
        @LayoutRes
        protected val layoutResId: Int
): DialogFragment(){
    protected var viewDataBinding: VDB? = null
    protected abstract val viewModel: VM
    protected val windowWidthParam = ViewGroup.LayoutParams.MATCH_PARENT
    protected val windowHeightParam = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding = DataBindingUtil.bind<VDB>(view)?.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(windowWidthParam, windowHeightParam)
    }

    override fun onDestroyView() {
        viewDataBinding = null
        super.onDestroyView()
    }
}