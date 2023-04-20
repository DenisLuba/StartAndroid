package com.example.p1151_multiplescreen

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class DetailsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && isLarge()) {
            finish()
            return
        }

        if (savedInstanceState == null) {
            val details: DetailsFragment = DetailsFragment.getInstance(intent.getIntExtra("position", 0))
            supportFragmentManager.beginTransaction().add(android.R.id.content, details).commit()
        }
    }

    private fun isLarge(): Boolean = (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
            Configuration.SCREENLAYOUT_SIZE_LARGE
}