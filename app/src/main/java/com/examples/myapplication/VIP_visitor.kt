package com.examples.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vip_visitor.*


class VIP_visitor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip_visitor)

        special.setOnClickListener {
            intent=Intent(this,Special_events::class.java)
            startActivity(intent)
        }
        events1.setOnClickListener {
            intent= Intent(this,companies::class.java)
            startActivity(intent)
        }
        sss.setOnClickListener {
            intent=Intent(this,companies2::class.java)
            startActivity(intent)
        }
        spp.setOnClickListener {
            intent=Intent(this,protocols_campanies::class.java)
            startActivity(intent)
        }
        spc.setOnClickListener {
            intent=Intent(this,contracts_companies::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()

        }
    }
}