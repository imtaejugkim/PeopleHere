package com.example.people_here.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.people_here.Remote.AuthService
import com.example.people_here.Remote.SignInView
import com.example.people_here.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity(),SignInView {
    lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)

        //여기 엮기 뭐랑?아이디랑
        //TODO: 로그인 로직 구현하기
        //TODO:뒤로가서 로그인을 해주세요에 이름 넣고 로그인 버튼에 로그아웃 넣기->intent랑 바인딩 적절히써서 body에서 받아온거로 하는거 생각 resp써서
        //TODO:닉네임이랑 토큰 따로 저장해주기(SharedPreference에 )
        //TODO: 저장되어 있다면 로그인, 로그아웃 누르면 NULL로 로그아웃 누르면 토큰값0

        binding.emailSignInButton.setOnClickListener{
            val id=binding.email.text.toString()//아이디보냄
            val pw=binding.password.text.toString()//텍스트보soa
            val authService= AuthService()//여기로 넘김
            authService.setSignInView(this)//자신이 상속해서 자신 넣어주기
            authService.signin(id,pw)//메소드 호출 따라서 엑티비에서 requset로 넘김
            Log.d("id",id)
            //ok
            //이거도 auth랑 연동해서+ RoomDB에서 token이랑 아이디 바뀌게 하고 login부분도 로그아웃으로
        }

        setContentView(binding.root)

    }

    override fun SignInLoading() {
        TODO("Not yet implemented")
    }

    override fun SignInSuccess() {
        //텍스트 전달해주기 login logout쪽
        Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()//흐름 꼭 이해하기 로직
        onBackPressed()//뒤 화면으로 가지게 해서 바뀌게
    }

    override fun SignInFailure(code: Int, msg: String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()//흐름 꼭 이해하기 로직

    }


}