package io.github.sooakim.sfoide.view.main.genderselect

import io.github.sooakim.sfoide.R
import io.github.sooakim.sfoide.databinding.FragmentGenderSelectBinding
import io.github.sooakim.sfoide.view.base.BaseDialogFragment
import io.github.sooakim.sfoide.view.base.DialogFragmentLauncher
import io.github.sooakim.sfoide.view.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

class SelectGenderFragment(

): BaseDialogFragment<FragmentGenderSelectBinding, MainViewModel>(
        layoutResId = R.layout.fragment_gender_select
){
    override val viewModel: MainViewModel by sharedViewModel()

    companion object: DialogFragmentLauncher<SelectGenderFragment>{
        override val fragmentClass: KClass<SelectGenderFragment>
            get() = SelectGenderFragment::class

        override fun newInstance(): SelectGenderFragment {
            return SelectGenderFragment()
        }
    }
}