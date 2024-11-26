package com.example.demo.model.service;

import lombok.*; // 어노테이션 자동 생성

import com.example.demo.model.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성

public class AddMemberRequest {
    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "한글, 영어만 가능합니다.")
    private String name;
    @NotBlank
    @Email
    private String email;
    @Size(min = 8, message = "8글자 이상이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어, 숫자만 가능합니다.")
    private String password;

    @Min(value = 19, message = "19세 이상만 가능합니다.")
    @Max(value = 90, message = "90세 이하만 가능합니다.")
    @Pattern(regexp = "^[0-9]*$", message = "숫자만 가능합니다.")
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