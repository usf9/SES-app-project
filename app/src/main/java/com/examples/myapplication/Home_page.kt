package com.examples.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home_page.contract
import kotlinx.android.synthetic.main.activity_home_page.events
import kotlinx.android.synthetic.main.activity_home_page.log_out
import kotlinx.android.synthetic.main.activity_home_page.protocols
import kotlinx.android.synthetic.main.activity_home_page.sessions

class Home_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        events.setOnClickListener {
            intent= Intent(this,companies::class.java)
            startActivity(intent)
        }
        sessions.setOnClickListener {
            intent=Intent(this,companies2::class.java)
            startActivity(intent)
        }
        protocols.setOnClickListener {
            intent=Intent(this,protocols_campanies::class.java)
            startActivity(intent)
        }
        contract.setOnClickListener {
            intent=Intent(this,contracts_companies::class.java)
            startActivity(intent)
        }

        log_out.setOnClickListener {
            intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()

        }

    }


}