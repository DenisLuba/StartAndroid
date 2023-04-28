package com.example.p1251_viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.p1251_viewpager.MainActivity.Companion.log
import com.example.p1251_viewpager.databinding.FragmentBinding
import kotlin.random.Random

class PageFragment : Fragment() {

    private lateinit var binding: FragmentBinding

    private var pageNumber: Int = 0
    private var backColor: Int = 0

    companion object {
        const val ARGUMENT_PAGE_NUMBER = "argument_page_number"
        const val SAVE_PAGE_NUMBER = "save_page_number"

        @JvmStatic fun newInstance(page: Int) : PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments?.getInt(ARGUMENT_PAGE_NUMBER) ?: 0
        log("Fragment: onCreate: $pageNumber")

        val random = Random

        backColor = Color.argb(
            40,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
            )

        var savedPageNumber = -1
        if (savedInstanceState != null) {
            savedPageNumber = savedInstanceState.getInt(SAVE_PAGE_NUMBER)
        }
        log("Fragment: savedPageNumber = $savedPageNumber")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        log("Fragment: onCreateView: $pageNumber")
        binding = FragmentBinding.inflate(inflater)
        with(binding.tvPage) {
            text = String.format(resources.getString(R.string.page), pageNumber)
            setBackgroundColor(backColor)
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("Fragment: onDestroyView: $pageNumber")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("Fragment: onDestroy: $pageNumber")
    }
}