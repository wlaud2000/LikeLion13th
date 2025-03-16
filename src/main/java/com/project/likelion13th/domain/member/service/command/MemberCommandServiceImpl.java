package com.project.likelion13th.domain.member.service.command;

import com.project.likelion13th.domain.member.converter.MemberConverter;
import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDTO createMember(MemberReqDTO.RegisterDTO dto) {
        Member member = MemberConverter.toEntity(dto);
        memberRepository.save(member);

        return MemberConverter.toDTO(member);
    }
}
