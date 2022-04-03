package com.yologger.presentation.screen.auth.agreePolicy.showPolicy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityShowPolicyBinding
import io.noties.markwon.Markwon

class ShowInformationPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_policy)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_policy)
        binding.lifecycleOwner = this
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_black_24_outlined_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }
        val markwon = Markwon.create(this@ShowInformationPolicyActivity)
        markwon.setMarkdown(binding.textViewMarkdown, """
            Heart to Heart는 "정보통신망 이용촉진 및 정보보호에 관한 법률", "개인정보보호법", "통신비밀보호법", "전기통신사업법" 및 "전자상거래 등에서의 소비자 보호에 관한 법률"등 정보통신서비스제공자가 준수하여야할 관련 법령상의 개인정보보호규정을 준수하며, 관련 법령에 의거한 개인정보처리방침을 정하여 이용자 권익 보호에 최선을 다하겠습니다.
            
            Heart to Heart은 아래와 같은 활용 목적을 가지고 이용자의 개인정보를 수집합니다.
            - 이메일 주소
                - 서비스: 회원가입
                - 수집 목적: 서비스 이용자 식별
                - 보유 및 이용기간: 회원 탈퇴 시 까지
            - 이름
                - 서비스: 회원가입
                - 수집 목적: 서비스 이용자 식별
                - 보유 및 이용기간: 회원 탈퇴 시 까지
            - 닉네임
                - 서비스: 회원가입
                - 수집 목적: 서비스 이용자 식별
                - 보유 및 이용기간: 회원 탈퇴 시 까지
        """.trimIndent())
    }
}