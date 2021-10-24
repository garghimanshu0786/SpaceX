package com.example.spacex

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.spacex.view.LaunchListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val info = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

        if (info == null) {
            setContentView(R.layout.activity_main)
            findViewById<Button>(R.id.restart_btn).setOnClickListener {
                finish()
                startActivity(intent)
            }
        } else {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = LaunchListFragment()
            setContentView(R.layout.fragment_container_layout)
            transaction.replace(R.id.fragment_container, fragment, fragment.toString())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}