package com.project.likelion13th.domain.member.service.command;

import com.project.likelion13th.domain.member.converter.MemberConverter;
import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.exception.MemberErrorCode;
import com.project.likelion13th.domain.member.exception.MemberException;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResDTO createMember(MemberReqDTO.RegisterDTO dto) {
        Member member = MemberConverter.toMember(passwordEncoder, dto);
        memberRepository.save(member);

        return MemberConverter.toMemberResponseDTO(member);
    }

    @Override
    public MemberResDTO updateProfile(String email, MemberReqDTO.UpdateProfileDTO dto) {
        // 회원 정보 조회
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 프로필 업데이트
        member.updateProfile(dto.getNickname());

        return MemberConverter.toProfileDTO(member);
    }

    @Override
    public void updatePassword(String email, MemberReqDTO.PasswordResetDTO dto) {
        // 회원 정보 조회
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 실제 구현에서는 비밀번호 암호화 로직 필요
        member.updatePassword(dto.getPassword());
    }

    @Override
    public void deleteMember(String email) {
        // 회원 정보 조회
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // soft delete 처리
        member.delete();
    }
}
