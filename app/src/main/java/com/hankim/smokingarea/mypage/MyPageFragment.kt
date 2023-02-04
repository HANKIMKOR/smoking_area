package com.hankim.smokingarea.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.hankim.smokingarea.R
import com.hankim.smokingarea.databinding.FragmentMypageBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private var binding: FragmentMypageBinding? = null
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val naverClientId = getString(R.string.naver_login_client_id)
    private val naverClientSecret = getString(R.string.naver_login_client_secret)
    private val naverClientName = getString(R.string.naver_login_client_name)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Naver login module initialize
        NaverIdLoginSDK.initialize(
            requireContext(),
            naverClientId,
            naverClientSecret,
            naverClientName
        )

        val fragmentMyPageBinding = FragmentMypageBinding.bind(view)
        binding = fragmentMyPageBinding

        fragmentMyPageBinding.naverLoginButton.setOnClickListener {
            startNaverLogin()
        }
        fragmentMyPageBinding.signInOutButton.setOnClickListener {
            binding?.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                if (auth.currentUser == null) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                successSignIn()
                            } else {
                                Toast.makeText(
                                    context,
                                    "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    auth.signOut()
                    binding.emailEditText.text.clear()
                    binding.emailEditText.isEnabled = true
                    binding.passwordEditText.text.clear()
                    binding.passwordEditText.isEnabled = true

                    binding.signInOutButton.text = "로그인"
                    binding.signInOutButton.isEnabled = false
                    binding.signUpButton.isEnabled = false
                }

            }
        }

        fragmentMyPageBinding.signUpButton.setOnClickListener {
            binding?.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "회원가입에 성공했습니다. 로그인 버튼을 눌러주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "회원가입에 실패했습니다. 이미 가입한 이메일일 수 있습니다.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

            }
        }

        fragmentMyPageBinding.emailEditText.addTextChangedListener {
            binding?.let { binding ->
                val enable =
                    binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
                binding.signInOutButton.isEnabled = enable
                binding.signUpButton.isEnabled = enable
            }
        }

        fragmentMyPageBinding.passwordEditText.addTextChangedListener {
            binding?.let { binding ->
                val enable =
                    binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
                binding.signInOutButton.isEnabled = enable
                binding.signUpButton.isEnabled = enable
            }
        }

    }


    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            binding?.let { binding ->
                binding.emailEditText.text.clear()
                binding.emailEditText.isEnabled = true
                binding.passwordEditText.text.clear()
                binding.passwordEditText.isEnabled = true

                binding.signInOutButton.text = "로그인"
                binding.signInOutButton.isEnabled = false
                binding.signUpButton.isEnabled = false
            }

        } else {
            binding?.let { binding ->
                binding.emailEditText.setText(auth.currentUser!!.email)
                binding.emailEditText.isEnabled = false
                binding.passwordEditText.setText("************")
                binding.passwordEditText.isEnabled = false

                binding.signInOutButton.text = "로그아"
                binding.signInOutButton.isEnabled = true
                binding.signUpButton.isEnabled = false

            }
        }
    }

    private fun successSignIn() {
        if (auth.currentUser == null) {
            Toast.makeText(context, "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        binding?.emailEditText?.isEnabled = false
        binding?.passwordEditText?.isEnabled = false
        binding?.signUpButton?.isEnabled = false
        binding?.signInOutButton?.text = "로그아웃"
    }

    // firebase init
    private fun resourceInit() {
        var db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
    }

    private fun startNaverLogin() {
        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                var db = FirebaseFirestore.getInstance()
                //var response: NidProfileResponse ?= null
                var naverToken: String? = NaverIdLoginSDK.getAccessToken()

                FirebaseApp.initializeApp(baseContext)

                val userId = result?.profile?.id
                Toast.makeText(baseContext, "ID : ${userId}", Toast.LENGTH_SHORT).show()

                var data = hashMapOf(
                    "token" to naverToken
                )

                Toast.makeText(this@MyPageFragment, "네이버 로그인 성공", Toast.LENGTH_SHORT).show()
                db.collection("Users").document(userId.toString()).get()
                    .addOnCompleteListener { task ->
                        var document: DocumentSnapshot = task.getResult()
                        if (document.exists()) {
                            var userInfo = UserInfo()

                            userInfo?.uid = userId
                            fbFirestore?.collection("Users")?.document(userId.toString())
                                ?.update(userInfo)
                        } else {

                        }
                    }
                db.collection("Tokens").document(userId.toString()).set(data)
                finish()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(
                    this@MyPageFragment, "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
    }

}

