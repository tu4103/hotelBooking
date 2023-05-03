package com.tu.bookin_hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tu.bookin_hotel.fragments.WalletFragment

class BookingActivityStep2 : AppCompatActivity() {
    private lateinit var walletFragment: WalletFragment
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_step2)
        walletFragment = WalletFragment()
        replaceFragment(walletFragment, "WalletFragment")

        val walletNavigation = findViewById<BottomNavigationView>(R.id.walet_navigation)
        walletNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.walet -> replaceFragment(walletFragment, "WalletFragment")
                R.id.MoMo -> replaceFragment(walletFragment, "WalletFragment")
            }
            true
}
    }






    private fun replaceFragment(fragment: Fragment, tag: String) {
        currentFragmentTag = tag
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_paymen, fragment, tag)
            .commit()
    }
}