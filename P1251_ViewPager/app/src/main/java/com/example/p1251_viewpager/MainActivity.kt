package com.example.p1251_viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING
import com.example.p1251_viewpager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        private const val PAGE_COUNT = 10
        fun log(message: String) = Log.d("myLogs", message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        with (binding.pager) {
            adapter = pagerAdapter

            setOnPageChangeListener(object : OnPageChangeListener {

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
//                    log("onPageScrolled, position = $position, positionOffset = $positionOffset, positionOffsetPixels = $positionOffsetPixels")
                }

                override fun onPageSelected(position: Int) {
//                    log("onPageSelected, position = $position")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    val message = when (state) {
                        SCROLL_STATE_IDLE -> "ничего не скролится"
                        SCROLL_STATE_DRAGGING -> "пользователь тащит страницу"
                        SCROLL_STATE_SETTLING -> "скроллер долистывает страницу до конца"
                        else -> ""
                    }
//                    log(message)
                }

            })
//        кол-во соседних страниц с сохраняемой View-структурой может быть настроено методом setOffscreenPageLimit
//            offscreenPageLimit = 5
        }

    }

//    private inner class MyFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
//
////        по номеру страницы надо вернуть фрагмент
//        override fun getItem(position: Int): Fragment = PageFragment.newInstance(position)
////        возвращает кол-во страниц
//        override fun getCount(): Int = PAGE_COUNT
////        текст в заголовке
//        override fun getPageTitle(position: Int) = "Title $position"
//
//    }

        private inner class MyFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment = PageFragment.newInstance(position)

        override fun getCount(): Int = PAGE_COUNT

        override fun getPageTitle(position: Int) = "Title $position"

    }


}