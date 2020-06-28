package io.github.sooakim.sfoide.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.sooakim.sfoide.R
import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.github.sooakim.sfoide.databinding.ActivityUserDetailBinding
import io.github.sooakim.sfoide.view.base.ActivityLauncher
import io.github.sooakim.sfoide.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>(
        layoutResId = R.layout.activity_user_detail
), OnMapReadyCallback{
    override val viewModel: UserDetailViewModel by viewModel {
        parametersOf(intent.getParcelableExtra(EXTRA_USER))
    }
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.frag_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        viewModel.output.user.observe(this, Observer { data ->
            this@UserDetailActivity.title = data.name
        })

        viewModel.output.startEmail.observe(this, Observer { data ->
            data.consume { email ->
                startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${email}")))
            }
        })

        viewModel.output.startPhone.observe(this, Observer { data ->
            data.consume { phone ->
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}")))
            }
        })

        viewModel.output.startCellPhone.observe(this, Observer { data ->
            data.consume { cellPhone ->
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${cellPhone}")))
            }
        })
    }

    override fun onDestroy() {
        currentMarker = null
        super.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if(googleMap == null) return

        viewModel.output.user.observe(this, Observer { user ->
            if(currentMarker == null){
                currentMarker = googleMap.addMarker(MarkerOptions()
                        .title(user.name)
                        .snippet(user.address)
                        .position(user.coordinate)
                )
            }else{
                currentMarker?.position = user.coordinate
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user.coordinate, DEFAULT_ZOOM_LEVEL))
        })
    }

    companion object : ActivityLauncher<UserDetailActivity> {
        private const val DEFAULT_ZOOM_LEVEL = 17f

        private const val EXTRA_USER = "extraUser"

        override val activityClass = UserDetailActivity::class

        fun startActivity(context: Context?, user: UserResponse) {
            startActivityWithExtra(context, bundleOf(
                    EXTRA_USER to user
            ))
        }
    }
}