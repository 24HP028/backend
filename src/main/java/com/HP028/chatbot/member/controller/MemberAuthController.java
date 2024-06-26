package com.HP028.chatbot.member.controller;

import com.HP028.chatbot.common.response.ApiResponse;
import com.HP028.chatbot.member.dto.MemberSignUpResponse;
import com.HP028.chatbot.member.dto.MemberSignUpRequest;
import com.HP028.chatbot.member.service.MemberAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.HP028.chatbot.common.response.ApiSuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/auth")
@Tag(name = "회원 인증 API", description = "회원 가입, 로그인 관련 API")
public class MemberAuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<MemberSignUpResponse>> signUp(@RequestBody @Valid MemberSignUpRequest request) {
        MemberSignUpResponse response = memberAuthService.signUp(request);
        return ApiResponse.success(SIGNUP_SUCCESS, response);
    }

}
