package com.example.p1151_multiplescreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.p1151_multiplescreen.databinding.ActivityMainBinding

class MainActivity : FragmentActivity(), TitlesFragment.OnItemClickListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var position = 0
    private var withDetails = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState != null)
            position = savedInstanceState.getInt("position")

        withDetails = binding.content != null
        if (withDetails) showDetails(position)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("position", position)
    }

    override fun itemClick(_position: Int) {
        position = _position
        showDetails(position)
    }

    private fun showDetails(position: Int) {
        if (withDetails) {
            var details: DetailsFragment? =
                supportFragmentManager.findFragmentById(R.id.content) as? DetailsFragment

            if (details == null || details.position() != position) {
                details = DetailsFragment.getInstance(position)

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, details)
                    .commit()
            }
        } else
            startActivity(Intent(this, DetailsActivity::class.java)
                .putExtra("position", position))
    }
}