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
import kotlinx.android.synthetic.main.activity_cp_application_form.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class cp_application_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_application_form)


        val image = intent?.getIntExtra("image",0)
        if (image != null) {
            imgget.setImageResource(image)
        }

        //get title

        var key1 = intent.getStringExtra("key1")

        val phoneNumberRegex = Regex("^01[0125]\\d{8}$")


        ///////////

        finish_btn.setEnabled(false);
        id_signup_des.addTextChangedListener(textWatcher);
        phone_cp.addTextChangedListener(textWatcher);
        email_req_cp.addTextChangedListener(textWatcher)
        finish_btn.setOnClickListener {


            val input =email_req_cp.text
            val des= id_signup_des.text.toString()
            val phone=phone_cp.text.toString()

            if (isEmail(input.toString()))
            {
                if(!des.isNullOrEmpty())
                {
                    if (phone.matches(phoneNumberRegex)) {

                            //--------start---------

                        val futureDate = getDatePlusDays(2) // returns a Calendar object representing the date 2 days in the future
                        val futureDate1 = getDatePlusDays(9) // returns a Calendar object representing the date 2 days in the future

                        val year = futureDate.get(Calendar.YEAR)
                        val month = futureDate.get(Calendar.MONTH) + 1 // add 1 to month value to adjust for 0-based indexing
                        val day = futureDate.get(Calendar.DAY_OF_MONTH)
                        val day1 = futureDate1.get(Calendar.DAY_OF_MONTH)
                        val fullFormDate = "$day-$month-$year"
                        val fullFormDate1 = "$day1-$month-$year"


                        var json:JSONObject= JSONObject()
                        json.put("FROM","kalidkalil4254@gmail.com")
                        json.put("TO",email_req_cp.text.toString())
                        json.put("SUBJECT","SES Reservation -${key1}\n ")
                        json.put("TEXT","Dear ${email_req_cp.text.toString()},\n" +
                                "\n" +
                                "I hope this email finds you well. I am pleased to confirm that we have received the information regarding your contract needs and are available to take on your project. We would like to reserve time on our schedule to ensure that we can provide you with the highest level of service.\n" +
                                "\n" +
                                "As a reminder, the following information has already been provided:\n" +
                                "\n" +
                                "Brief description of the project and timeline:\n" +
                                "\n"+"${id_signup_des.text.toString()}\n\n\n"+
                                "Preferred start date: ${fullFormDate}\n" +
                                "Preferred end date: ${fullFormDate1}\n" +
                                "With Phone Number: ${phone_cp.text.toString()}\n" +
                                "Based on this information, we have developed a proposal outlining the scope of work, timeline, and cost. Please find the proposal attached to this email.\n" +
                                "\n" +
                                "If you have any questions or concerns about the proposal, please do not hesitate to reach out to us. We are committed to providing you with the best possible service and look forward to working with you.\n" +
                                "\n" +
                                "To proceed with the reservation, we will need your confirmation of the proposal and a signed contract. Please let us know if you have any questions or if you would like to schedule a call to discuss the proposal further.\n" +
                                "\n" +
                                "Thank you for your consideration, and we hope to hear from you soon.\n" +
                                "\n" +
                                "Best regards,\n" +
                                "SES")

                        sendPostRequest("https://fbmmxefisc.execute-api.us-east-1.amazonaws.com/dev/api/v0/emails/send",json)

                        { res->
                            if(res=="ok!"){

                                Toast.makeText(this,"Sent successfully!!",Toast.LENGTH_LONG).show()
                                //finish()

                                //---------------------send email ----------------------

                            }
                            else{

                            }

                        }




                        //--------end---------

                    } else {
                        // the phone number is invalid
                        Toast.makeText(this,"the phone number is invalid ",Toast.LENGTH_LONG).show()
                    } // code here

                }else{
                    Toast.makeText(this,"you need to enter description ",Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(this,"invalid email",Toast.LENGTH_LONG).show()

            }

        }






    }


    fun getDatePlusDays(days: Int): Calendar {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DATE, days)
        return currentDate
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
                Toast.makeText(this,"Sent successfully!!",Toast.LENGTH_LONG).show()
                //intent

                intent= Intent(this,Home_page::class.java)
                startActivity(intent)

                val responseString = response.get("message").toString()
                result(responseString)
            },
            Response.ErrorListener { error ->
                // Handle error here
                Log.e("Error", error.toString())

                Toast.makeText(this,"You Have Already Registered ", Toast.LENGTH_LONG).show()

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



    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val n = id_signup_des.text.toString()
            val a = phone_cp.text.toString()
            val r = email_req_cp.text.toString()


            if (!n.isEmpty() && !a.isEmpty() && !r.isEmpty()){
                finish_btn.isEnabled = true
            }

            else {
                finish_btn.isEnabled = false
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





