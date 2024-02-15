package com.peopleHere.people_here.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.peopleHere.people_here.R

class VerifyEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = "jungan0403@egmail.com"
// Firebase에서 제공하는 이메일 인증 링크 전송 설정
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://peoplehere-745e7.firebaseapp.com/__/auth/action?mode=action&oobCode=code") // 인증 후 이동할 URL
            .setHandleCodeInApp(true) // 앱에서 링크를 처리할 것인지 여부
            .setAndroidPackageName(
                applicationContext.packageName,
                false, /* installIfNotAvailable */
                null /* minimumVersion */
            )
            .build()
       // sendEmailVerification()
// 이메일 주소로 인증 링크 전송
        Firebase.auth.sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("EmailSent", "Email sent.")
                    // 이메일이 성공적으로 보내졌을 때의 처리
                    // 사용자에게는 이메일을 확인하라는 메시지를 표시하면 됩니다.
                } else {
                    Log.e("EmailSent_fail", "Failed to send email.", task.exception)
                    // 이메일 전송이 실패한 경우의 처리
                }
            }

        setContentView(R.layout.activity_verify_email)
    }
   /* fun sendEmailVerification(){

        if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified){
            Toast.makeText(this, "이메일 인증이 이미 완료되었습니다", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "확인메일을 보냈습니다", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }*/
}