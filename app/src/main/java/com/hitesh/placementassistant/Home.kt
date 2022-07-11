package com.hitesh.placementassistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class Home : Fragment() {

    lateinit var mainTabLayout: TabLayout
    lateinit var mainViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        val fragments =
            listOf(
                OnCampus(),
                OffCampus()
            )
        mainTabLayout = v.findViewById(R.id.main_tab_layout)
        mainViewPager = v.findViewById(R.id.main_view_pager)
        val adapter = FragmentViewPagerAdapter(this.childFragmentManager, lifecycle, fragments)
        mainViewPager.adapter = adapter
        mainTabLayout.addTab(
            mainTabLayout
                .newTab()
                .setText("OnCampus")
        )
        mainTabLayout.addTab(
            mainTabLayout
                .newTab()
                .setText("OffCampus")
        )
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                mainViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        mainViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mainTabLayout.selectTab(mainTabLayout.getTabAt(position))
            }
        })
    }

}