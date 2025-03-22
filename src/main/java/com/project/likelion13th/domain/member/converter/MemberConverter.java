package com.project.likelion13th.domain.member.converter;

import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.entity.SocialType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.RegisterDTO dto){
        return Member.builder()
                .nickname(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // 실제 구현에서는 암호화 필요
                .socialType(SocialType.LOCAL)
                .build();
    }

    public static MemberResDTO toMemberResponseDTO(Member member){
        return MemberResDTO.builder()
                .userId(member.getId())
                .email(member.getEmail())
                .name(member.getNickname())
                .createAt(member.getCreatedAt())
                .build();
    }

    // 프로필 업데이트 결과 DTO
    public static MemberResDTO toProfileDTO(Member member) {
        return MemberResDTO.builder()
                .userId(member.getId())
                .name(member.getNickname())
                .email(member.getEmail())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

}
