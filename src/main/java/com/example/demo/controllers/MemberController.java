package com.example.demo.controllers;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    /** 회원 가입 페이지 이동 */
    @GetMapping("/member/signup")
    public String signup(){
        return "member/signup";
    }
    /** 회원 목록 페이지 이동 */
    @GetMapping("/member/list")
    public String findMemberList(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "member/memberList";
    }

    /** 회원 가입 */
    @PostMapping("/member/createMember")
    public String createMember(Member member, Address address){
        member.setAddress(address);
        memberService.joinMember(member);
        return "redirect:/member/list";
    }
}
