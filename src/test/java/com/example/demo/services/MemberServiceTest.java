package com.example.demo.services;

import com.example.demo.domain.Member;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService service;

    @Test
    @DisplayName("회원가입 테스트")
    public void joinTest(){
        Member member = new Member();
        member.setName("이승준");
        Long saveId = service.joinMember(member);
        assertEquals(member.getName(), service.findOne(saveId).getName());
    }

    @Test(expected = IllegalStateException.class)
    @DisplayName("중복회원 예외")
    public void duplicatedUser(){
        Member member = new Member();
        member.setName("이승준");
        Long saveId = service.joinMember(member);
        fail("똑같은 유저 중복");
    }

}