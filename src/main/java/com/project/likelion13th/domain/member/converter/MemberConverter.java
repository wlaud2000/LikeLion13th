package com.project.likelion13th.domain.member.converter;

import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.entity.SocialType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toEntity(MemberReqDTO.RegisterDTO dto){
        return Member.builder()
                .nickname(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .socialType(SocialType.LOCAL)
                .build();
    }

    public static MemberResDTO toDTO(Member member){
        return MemberResDTO.builder()
                .userId(member.getId())
                .email(member.getEmail())
                .name(member.getNickname())
                .createAt(member.getCreatedAt())
                .build();
    }

}
