//package com.examples.myapplication
//
//import android.text.Editable
//import android.text.TextWatcher
//import kotlinx.android.synthetic.main.activity_sign_up.email_signup_visitor
//import kotlinx.android.synthetic.main.activity_sign_up.password_signup_visitor
//import kotlinx.android.synthetic.main.activity_sign_up.phone_signup_visitor
//import kotlinx.android.synthetic.main.activity_sign_up.signup_visitor_btn
//
//class constrains {
//
//
//
//
//
//
//}
//
//
//
//
//////fun check email
//fun isEmail(input: String): Boolean {
//    if (!input.contains("@")) {
//        // input does not contain "@", so it cannot be an email address
//        return false
//    }
//    val regex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
//    return regex.matches(input)
//}
//
//
//////button off
//private val textWatcher = object : TextWatcher {
//    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        // Do nothing
//    }
//
//    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        val n = email_signup_visitor.text.toString()
//        val a = password_signup_visitor.text.toString()
//        val r = phone_signup_visitor.text.toString()
//
//
//        if (!n.isEmpty() && !a.isEmpty() && !r.isEmpty()){
//            signup_visitor_btn.isEnabled = true
//        }
//
//        else {
//            signup_visitor_btn.isEnabled = false
//        }
//    }
//
//    override fun afterTextChanged(s: Editable?) {
//        // Do nothing
//    }
//}
//
//
//
/////////code
//signup_visitor_btn.setEnabled(false);
//
//email_signup_visitor.addTextChangedListener(textWatcher);
//password_signup_visitor.addTextChangedListener(textWatcher);
//phone_signup_visitor.addTextChangedListener(textWatcher);
//////////////
//
//
//
//
/////////password
//
//
//val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$")
