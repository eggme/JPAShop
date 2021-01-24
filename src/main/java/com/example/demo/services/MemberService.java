package com.example.demo.services;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository repository;

    // 회원가입
    public Long joinMember(Member member){
        validateDuplicateMember(member);
        Member saveMember = repository.save(member);
        return saveMember.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = repository.findByNames(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return repository.findAll();
    }

    public Member findOne(Long id){
        return repository.findById(id).get();
    }

}
