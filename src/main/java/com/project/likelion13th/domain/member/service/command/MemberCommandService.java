package com.project.likelion13th.domain.member.service.command;

import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {
    MemberResDTO createMember(MemberReqDTO.RegisterDTO dto);
    MemberResDTO updateProfile(Long memberId, MemberReqDTO.UpdateProfileDTO dto);
    void updatePassword(Long memberId, MemberReqDTO.PasswordResetDTO dto);
    void deleteMember(Long memberId);
}
