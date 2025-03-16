package com.project.likelion13th.domain.member.service.command;

import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {
    MemberResDTO createMember(MemberReqDTO.RegisterDTO dto);
}
