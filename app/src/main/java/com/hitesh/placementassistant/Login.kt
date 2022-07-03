package com.hitesh.placementassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class Login : Fragment() {

    lateinit var etLoginOtp: EditText
    lateinit var etLoginMobile: EditText
    lateinit var btnLogin: Button
    lateinit var btnLoginOtp: Button

    lateinit var auth: FirebaseAuth

    private var mVerificationId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        etLoginMobile = v.findViewById(R.id.et_login_mobile)
        etLoginOtp = v.findViewById(R.id.et_login_otp)
        btnLogin = v.findViewById(R.id.btn_login)
        btnLoginOtp = v.findViewById(R.id.btn_login_get_otp)

        FirebaseApp.initializeApp(requireActivity());
        auth = FirebaseAuth.getInstance()
        btnLoginOtp.setOnClickListener{
            showBottomSheetDialog()
        }
        return v
    }
    fun showBottomSheetDialog() {
        sendVerificationCode(etLoginMobile.text.toString())
        btnLogin.setOnClickListener(View.OnClickListener {
            val code = etLoginOtp.text.toString().trim { it <= ' ' }
            if (code.isEmpty() || code.length < 6) {
                etLoginOtp.error = "Enter valid code"
                etLoginOtp.requestFocus()
                return@OnClickListener
            } else {
                verifyVerificationCode(code)
            }
        })
    }

    private fun sendVerificationCode(no: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$no",
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            mCallbacks
        )
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                code?.let { verifyVerificationCode(it) }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
            }
        }

    private fun verifyVerificationCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId.toString(), code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(
                requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity,"Successfull", Toast.LENGTH_SHORT).show()
                    } else {
                        var message = "Somthing is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT)
                    }
                })
    }
}