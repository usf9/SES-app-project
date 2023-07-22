package com.examples.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.email_signup_visitor
import kotlinx.android.synthetic.main.activity_sign_up.password_signup_visitor
import kotlinx.android.synthetic.main.activity_sign_up.phone_signup_visitor
import kotlinx.android.synthetic.main.activity_sign_up.signup_visitor_btn

class Login : AppCompatActivity() {

    lateinit var getIdUrl:String
    lateinit var readAuthMobileUrl:String
    lateinit var Id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Id=""

        login_btn.setEnabled(false);

        email_txt_login.addTextChangedListener(textWatcher);
        loginPass_txtField.addTextChangedListener(textWatcher);

        login_btn.setOnClickListener{

            val input =email_txt_login.text

            if(isEmail(input.toString())){
                getIdUrl="https://o6iz3yjg55.execute-api.us-east-1.amazonaws.com/api/v1/auth/getIdByUserAndPassword?USERNAME=${email_txt_login.text.toString()}&PASSWORD=${loginPass_txtField.text.toString()}"
                //1- getting UserID:
                try{
                    val volleyQueue = Volley.newRequestQueue(this)

                    val stringRequest = StringRequest(Request.Method.GET, getIdUrl,
                        Response.Listener<String> { response ->
                            // Handle the response string
                            Id = response
                            /////////////////////////////////////////////////////////2- if the ID in Ranges:///////////////////////////////////////////////////////
                            if(Id.toLong() in (232142320..232142330)){
                                intent= Intent(this,Control::class.java)
                                startActivity(intent)
                            }else if(Id.toInt() in (5443343..5443393)){
                                intent= Intent(this,Speakers_voda::class.java)
                                startActivity(intent)
                            }else if(Id.toInt() in (3648232..3648272)){
                                intent= Intent(this,Speakers_ibm::class.java)
                                startActivity(intent)
                            }else if(Id.toInt() in (7854634..7854654)){
                                intent= Intent(this,Speakers_etisalat::class.java)
                                startActivity(intent)
                            }else if(Id.toInt() in (983439533..983439553)){
                                intent= Intent(this,VIP_visitor::class.java)
                                startActivity(intent)
                            }

                            /////////////////////////////////////////////////////////3- else if ID is not in ranges but in DB:///////////////////////////////////////////////////////
                            else if(Id.toInt() !in (232142320..232142330) && Id.toInt() !in (5443343..5443393) && Id.toInt() !in (3648232..3648272) && Id.toInt() !in (7854634..7854654) && Id.toInt() !in (983439533..983439553) && Id!=null && Id!=""){
                                intent= Intent(this, Home_page::class.java)
                                startActivity(intent)
                            }

                            /////////////////////////////////////////////////////////4- else(not found):///////////////////////////////////////////////////////
                            else{
                                Toast.makeText(this,"Invalid Username or Password!",Toast.LENGTH_LONG).show()
                            }
                        },
                        Response.ErrorListener { error ->
                            // Handle the error
                            Toast.makeText(this,"User not found!",Toast.LENGTH_LONG).show()
                        })

// Add the string request to the request queue
                    Volley.newRequestQueue(this).add(stringRequest)

                }catch(err:Exception){
                    //error during requesting:
                    Toast.makeText(this,"Error:${err}",Toast.LENGTH_LONG).show()
                }

            }else {
                Toast.makeText(this,"Invalid email address",Toast.LENGTH_LONG).show()

            }




        }


        //button sign up intent

        signup_btn_page.setOnClickListener {
            intent= Intent(this, b_signup::class.java)
            startActivity(intent)
        }


    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val n = email_txt_login.text.toString()
            val a = loginPass_txtField.text.toString()


            if (!n.isEmpty() && !a.isEmpty() && !n.isEmpty()){
                login_btn.isEnabled = true
            }

            else {
                login_btn.isEnabled = false
            }
        }

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }


    }
    fun setHint() {
        email_txt_login.hint = "Enter your name"
    }
    fun isEmail(input: String): Boolean {
        if (!input.contains("@")) {
            // input does not contain "@", so it cannot be an email address
            return false
        }
        val regex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regex.matches(input)
    }



}