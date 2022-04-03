package com.yologger.presentation.screen.auth.agreePolicy.showTerms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityShowTermsBinding
import io.noties.markwon.Markwon

class ShowTermsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_terms)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_terms)
        binding.lifecycleOwner = this
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_black_24_outlined_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }
        val markwon = Markwon.create(this@ShowTermsActivity)
        markwon.setMarkdown(binding.textViewMarkdown, """
            안녕하세요? Heart to Heart을 이용해주셔서 감사합니다. 소셜 네트워크 서비스인 Heart to Heart의 약관을 읽어주시면 감사하겠습니다.
                                    
            **계정 관련**
            
            Heart to Heart는 이메일로 계정을 생성할 수 있습니다. 다만 계정 생성 과정에서 이메일 인증을 해야합니다.
            
            **게시물의 관리**
            
            1. 사용자의 게시물이 "정보통신망법" 및 "저작권법" 등 관련법에 위반되는 내용을 포함하는 경우, 권리자는 관련법이 정한 절차에 따라 해당 게시물의 게시 중단 및 삭제 등을 요청할 수 있으며, 관리자는 이에 따른 조치를 취할 수 있습니다. 
            2. 사용자는 다른 사용자의 게시물을 차단하거나 신고할 수 있으며, 관리자는 검토 후 위반사항이 확인되면 게시글을 삭제하거나 사용자를 차단/정지시킬 수 있습니다.
            
            **이용계약 해지(탈퇴)**
            
            사용자가 서비스 이용을 원치 않을 때에는 언제든지 해지할 수 있습니다. 해지 시 법령 및 개인정보처리방침에 따라 사용자의 모든 데이터가 삭제됩니다. 
            
            **손해배상**
            
            1. Heart to Heart는 일상을 공유하는 단순한 소셜 네트워크 서비스입니다. 사용자 간 금전거래로 발생하는 사용자 손해는 책임을 부담하지 않습니다.
            2. Heart to Heart는 사용자 실수로 업로드된 개인정보, 사진 등으로 발생하는 손해에 대해 책임을 부담하지 않습니다. 민감한 정보는 서비스에 업로드하면 안됩니다.
            
            - 공고일자: 2022년 04월 03일
            - 시행일자: 2022년 04월 03일
            
        """.trimIndent())
    }
}