package com.example.demo.model.service;

import lombok.*; // 어노테이션 자동 생성

import com.example.demo.model.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성

public class AddMemberRequest {
    @NotBlank // 없어도 되는지 확인
    @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "한글과 영어만 가능")
    private String name;
    @NotBlank
    @Email
    private String email;
    @Size(min = 8, message = "8글자 이상만 가능")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "숫자, 영어만 가능")
    private String password;
    private String age;
    private String mobile;
    private String address;

    public Member toEntity() { // Member 생성자를 통해 객체 생성
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .mobile(mobile)
                .address(address)
                .build();
    }
}