package ads02.auth.controller;

import ads02.auth.dto.MemberDto;
import ads02.auth.interceptor.NoAuth;
import ads02.auth.service.MemberService;
import ads02.auth.vo.LoginReq;
import ads02.auth.vo.MemberReq;
import ads02.auth.vo.MemberRes;
import ads02.auth.vo.TokenRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    @NoAuth
    public ResponseEntity<MemberRes> createMember(@RequestBody MemberReq memberReq){
        MemberDto memberDto = MemberDto.builder()
                .nickname(memberReq.getId())
                .password(memberReq.getPassword())
                .bojToken(memberReq.getBojToken())
                .build();
        memberService.createMember(memberDto);

        MemberRes memberRes = new MemberRes(memberReq.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberRes);
    }

    @NoAuth
    @PostMapping("/login")
    public ResponseEntity<TokenRes> login(@RequestBody LoginReq loginReq){
        String token = memberService.authenticateMember(loginReq);
        return ResponseEntity.ok(new TokenRes(token));
    }
}
