package com.examples.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject


class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$")
        signup_visitor_btn.setEnabled(false);

        email_signup_visitor.addTextChangedListener(textWatcher);
        password_signup_visitor.addTextChangedListener(textWatcher);
        phone_signup_visitor.addTextChangedListener(textWatcher);


        signup_visitor_btn.setOnClickListener{


            val input =email_signup_visitor.text
            val userInputpass = password_signup_visitor.text


            if (isEmail(input.toString()) ){
                if (regex.matches(userInputpass)) {

                    // Create the request body as a JSONObject.
                    val requestBody = JSONObject()
                    requestBody.put("USERNAME", email_signup_visitor.text.toString())
                    requestBody.put("PASSWORD", password_signup_visitor.text.toString())
                    requestBody.put("PHONE", phone_signup_visitor.text.toString())
                    requestBody.put("ROLE", "VISITOR")

                    //Toast.makeText(this,"user: ${email_signup.text.toString()}, pass: ${password_signup.text.toString()}, phone: ${id_signup_other.text.toString()}",Toast.LENGTH_LONG).show()

// Call the sendPostRequest function with the URL and request body.
                    val url = "https://o6iz3yjg55.execute-api.us-east-1.amazonaws.com/api/v0/auth/create"

                    sendPostRequest(url, requestBody) { responseString ->

                        if(responseString=="created!"){
                            intent= Intent(this,Home_page::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,"Invalid Input Data! Please Try Again!",Toast.LENGTH_SHORT).show()
                        }

                        // Handle the response string here
                        //Log.d("Response", responseString)
                    }


                }else {
                    Toast.makeText(this,"weak password should has At least 8 characters long\n" +
                            "Contains at least 1 uppercase letter\n" +
                            "Contains at least 1 lowercase letter\n" +
                            "Contains at least 1 number\n" +
                            "Contains at least 1 special character from the set @\$!%*?&",Toast.LENGTH_LONG).show()
                }

            }else {
                Toast.makeText(this,"Invalid email address",Toast.LENGTH_LONG).show()

            }

//            try{
//// Create the request body as a JSONObject.
//                val requestBody = JSONObject()
//                requestBody.put("USERNAME", email_signup_visitor.text.toString())
//                requestBody.put("PASSWORD", password_signup_visitor.text.toString())
//                requestBody.put("PHONE", phone_signup_visitor.text.toString())
//                requestBody.put("ROLE", "VISITOR")
//
//                Toast.makeText(this,"user: ${email_signup_visitor.text.toString()}, pass: ${password_signup_visitor.text.toString()}, phone: ${phone_signup_visitor.text.toString()}",Toast.LENGTH_LONG).show()
//
//// Call the sendPostRequest function with the URL and request body.
//                val url = "https://o6iz3yjg55.execute-api.us-east-1.amazonaws.com/api/v0/auth/create"
//
//                sendPostRequest(url, requestBody) { responseString ->
//
//                    if(responseString=="created!"){
//                        intent= Intent(this,Home_page::class.java)
//                        startActivity(intent)
//                    }else{
//                        Toast.makeText(this,"Invalid Input Data! Please Try Again!",Toast.LENGTH_LONG).show()
//                    }
//
//                    // Handle the response string here
//                    Log.d("Response", responseString)
//                }
//
//            }catch(err:Exception){
//                Toast.makeText(this,"Error:${err}",Toast.LENGTH_LONG).show()
//            }


        }


        log_btn111.setOnClickListener {
            intent= Intent(this,Login::class.java)
            startActivity(intent)
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
                //Toast.makeText(this,"Response: ${response.get("message")}",Toast.LENGTH_LONG).show()
                val responseString = response.get("message").toString()
                result(responseString)
            },
            Response.ErrorListener { error ->
                // Handle error here
                Log.e("Error", error.toString())
                //Toast.makeText(this,"Error: ${error.message}",Toast.LENGTH_LONG).show()
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


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val n = email_signup_visitor.text.toString()
            val a = password_signup_visitor.text.toString()
            val r = phone_signup_visitor.text.toString()


            if (!n.isEmpty() && !a.isEmpty() && !r.isEmpty()){
                signup_visitor_btn.isEnabled = true
            }

            else {
                signup_visitor_btn.isEnabled = false
            }
        }

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }
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