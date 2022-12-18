package ads02.auth.service;

import ads02.auth.domain.Member;
import ads02.auth.dto.MemberDto;
import ads02.auth.exception.GoraeException;
import ads02.auth.repository.MemberRepository;
import ads02.auth.vo.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void createMember(MemberDto memberDto){
        if(memberRepository.findByNicknameEquals(memberDto.getNickname()).isPresent()){
            throw new GoraeException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .nickname(memberDto.getNickname())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .bojToken(memberDto.getBojToken())
                .build();
        memberRepository.save(member);
    }

    public String authenticateMember(LoginReq loginReq) {
        Member member = memberRepository.findByNicknameEquals(loginReq.getId())
                .orElseThrow(() -> new GoraeException("해당하는 아이디의 유저가 없습니다."));
        if (!passwordEncoder.matches(loginReq.getPassword(), member.getPassword()))
            throw new GoraeException("비밀번호가 일치하지 않습니다.");
        return jwtService.createJwt(member.getNickname(), member.getBojToken());
    }

    public boolean nicknameExists(String nickname) {
        return memberRepository.findByNicknameEquals(nickname).isPresent();
    }
}
