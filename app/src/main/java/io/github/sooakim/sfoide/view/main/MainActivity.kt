package io.github.sooakim.sfoide.view.main

import android.os.Bundle
import androidx.lifecycle.Observer
import io.github.sooakim.sfoide.R
import io.github.sooakim.sfoide.databinding.ActivityMainBinding
import io.github.sooakim.sfoide.utils.ext.showSafely
import io.github.sooakim.sfoide.view.base.BaseActivity
import io.github.sooakim.sfoide.view.detail.UserDetailActivity
import io.github.sooakim.sfoide.view.main.genderselect.SelectGenderFragment
import io.github.sooakim.sfoide.view.main.adapter.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(
        layoutResId = R.layout.activity_main
){
    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.rvUser.adapter = UserListAdapter()

        viewModel.output.scrollToTop.observe(this, Observer { data ->
            data.consume {
                viewDataBinding.rvUser.layoutManager?.smoothScrollToPosition(viewDataBinding.rvUser, null, 0)
            }
        })

        viewModel.output.startDetail.observe(this, Observer { data ->
            data.consume {
                UserDetailActivity.startActivity(this@MainActivity, it)
            }
        })

        viewModel.output.startGenderFilter.observe(this, Observer { data ->
            data.consume {
                SelectGenderFragment.newInstance()
                        .showSafely(supportFragmentManager, SelectGenderFragment.tag)
            }
        })
    }
}