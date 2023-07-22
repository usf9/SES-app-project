package com.examples.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register_form.*
import kotlinx.android.synthetic.main.activity_sign_up.email_signup_visitor
import kotlinx.android.synthetic.main.activity_sign_up.password_signup_visitor
import org.json.JSONObject
import java.util.Date

class Register_Form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)



        var title = intent.getStringExtra("title")

        var intent3  = intent

        confirm.setEnabled(false);

        email_req.addTextChangedListener(textWatcher);
        phone_rec.addTextChangedListener(textWatcher);
        confirm.setOnClickListener {


            val input =email_req.text

            if (isEmail(input.toString()) ){


                //------------------------------Registeration request--------------------
                var jsonObj: JSONObject = JSONObject()
//                                                     (getExtras HERE -->EMAIL)
                jsonObj.put("KEY", email_req.text.toString()+  title)
//                                                     (getExtras HERE -->EMAIL)
                jsonObj.put("EMAIL",email_req.text.toString())

                jsonObj.put("COMPANY", title)

                //---------------------registering new User----------------------
                sendPostRequest("https://nc2mr41fz8.execute-api.us-east-1.amazonaws.com/api/v1/sessions/registerationCreate",jsonObj)
                { res->
                    if(res=="ok!"){
                        intent3 = Intent(this, QRcode_gen::class.java)
                        intent3.putExtra("car",title)
                        intent3.putExtra("email_Add",email_req.text.toString())
                        startActivity(intent3)

                        var jsonObj1= JSONObject()
                        jsonObj1.put("FROM","yousefabdallah9999@gmail.com")
                        jsonObj1.put("TO","${email_req.text.toString()}")
                        jsonObj1.put("SUBJECT","Join us at SES and discover the latest trends in ICT")
                        jsonObj1.put("TEXT","Dear ${email_req.text.toString()},\n" +
                                "\n" +
                                "We are excited to invite you to our upcoming ICT exhibition, SES, which will be held on ${Date().toString()} at New Cairo from 09:00 AM to 10:00 PM. The exhibition will showcase the latest technologies and innovations in the field of information and communication technology, and we would be honored to have you join us.\n" +
                                "\n" +
                                "As a leader in the ICT industry, we believe that your presence at the exhibition would be invaluable. The event will provide an excellent opportunity for you to network with other professionals in the field, as well as learn about new products, services, and solutions that can help your organization stay ahead of the curve.\n" +
                                "\n" +
                                "We look forward to seeing you at the exhibition and sharing the latest developments in ICT with you.\n" +
                                "\n" +
                                "Thank you for your continued support.\n" +
                                "\n" +
                                "Best regards,\n" +
                                "\n" +
                                "SES\n")


                        //---------------------send email ----------------------
                        sendPostRequest("https://fbmmxefisc.execute-api.us-east-1.amazonaws.com/dev/api/v0/emails/send",jsonObj1)
                        { res->
                            if(res=="ok!"){
//                            intent3 = Intent(this, QRcode_gen::class.java)
//                            intent3.putExtra("car",title)
//                            startActivity(intent3)
//                            Toast.makeText(this,"Email Sent Successfully!",Toast.LENGTH_LONG).show()

                                //---------------------send email ----------------------

                            }
                            else{
//                            Toast.makeText(this,"Email Sending Failed!",Toast.LENGTH_LONG).show()
                            }

                        }


//                    Toast.makeText(this,"You Have Registered Successfully!",Toast.LENGTH_LONG).show()
                    }
                    else{
//                    Toast.makeText(this,"You Have Already Registered in This Event!",Toast.LENGTH_LONG).show()
                    }

                }

            }
            else {
                Toast.makeText(this,"Invalid email address",Toast.LENGTH_LONG).show()

            }





        }
    }








    private fun sendPostRequest(url: String, requestBody: JSONObject, result: (String) -> Unit) {

        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url,
            requestBody,
            Response.Listener { response ->
                // Handle the response here
                Log.d("Response", response.toString())
                Toast.makeText(this,"Response: ${response.get("message")}",Toast.LENGTH_LONG).show()
                val responseString = response.get("message").toString()
                result(responseString)
            },
            Response.ErrorListener { error ->
                // Handle error here
//                Log.e("Error", error.toString())
//
//                Toast.makeText(this,"You Have Already Registered in This Event!",Toast.LENGTH_LONG).show()

//                Toast.makeText(this,"Error: ${error.message}",Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                // Set the Content-Type header to application/json
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest)
    }

    fun isEmail(input: String): Boolean {
        if (!input.contains("@")) {
            // input does not contain "@", so it cannot be an email address
            return false
        }
        val regex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regex.matches(input)
    }




    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val n = email_req.text.toString()
            val a = phone_rec.text.toString()


            if (!n.isEmpty() && !a.isEmpty() && !n.isEmpty()){
                confirm.isEnabled = true
            }

            else {
                confirm.isEnabled = false
            }
        }

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }
    }




}