package com.tu.bookin_hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tu.bookin_hotel.fragments.BookingFragment
import com.tu.bookin_hotel.fragments.CartFragment
import com.tu.bookin_hotel.fragments.HomeFragment
import com.tu.bookin_hotel.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var cartFragment: CartFragment
    private lateinit var profileFragment: ProfileFragment
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Khởi tạo các Fragment
        homeFragment = HomeFragment()
        cartFragment = CartFragment()
        profileFragment = ProfileFragment()

        // Replace Fragment mặc định là HomeFragment
        replaceFragment(homeFragment, "HomeFragment")

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(homeFragment, "HomeFragment")
                R.id.hotel_cart -> replaceFragment(cartFragment, "CartFragment")
                R.id.profile -> replaceFragment(profileFragment, "ProfileFragment")
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        // Lưu trạng thái của Fragment hiện tại vào SharedPreferences
        val prefs = getPreferences(MODE_PRIVATE)
        prefs.edit().putString("CURRENT_FRAGMENT_TAG", currentFragmentTag).apply()
    }

    override fun onStart() {
        super.onStart()
        // Khôi phục trạng thái của Fragment từ SharedPreferences
        val prefs = getPreferences(MODE_PRIVATE)
        val savedTag = prefs.getString("CURRENT_FRAGMENT_TAG", null)
        savedTag?.let {
            when (it) {
                "HomeFragment" -> replaceFragment(homeFragment, it)
                "CartFragment" -> replaceFragment(cartFragment, it)
                "ProfileFragment" -> replaceFragment(profileFragment, it)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        currentFragmentTag = tag
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment, tag)
            .commit()
    }
}
