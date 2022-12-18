package ads02.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long id;
    private String nickname;
    private String password;
    private String bojToken;

    @Builder
    public MemberDto(Long id, String nickname, String password, String bojToken) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.bojToken = bojToken;
    }
}
