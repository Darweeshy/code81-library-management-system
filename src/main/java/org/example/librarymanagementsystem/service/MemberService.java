package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.MemberDto;
import org.example.librarymanagementsystem.model.Member;
import org.example.librarymanagementsystem.repo.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto createMember(MemberDto memberDto) {
        Member member = new Member();
        member.setFullName(memberDto.getFullName());  // ✅ fixed
        member.setEmail(memberDto.getEmail());

        Member savedMember = memberRepository.save(member);
        return convertToDto(savedMember);
    }

    @Transactional(readOnly = true)
    public List<MemberDto> findAll() {
        return memberRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return convertToDto(member);
    }

    @Transactional
    public MemberDto updateMember(Long id, MemberDto memberDto) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        existingMember.setFullName(memberDto.getFullName());  // ✅ fixed
        existingMember.setEmail(memberDto.getEmail());

        Member updatedMember = memberRepository.save(existingMember);
        return convertToDto(updatedMember);
    }

    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    private MemberDto convertToDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setFullName(member.getFullName());  // ✅ fixed
        dto.setEmail(member.getEmail());
        return dto;
    }
}
