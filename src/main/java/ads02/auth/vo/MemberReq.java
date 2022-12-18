package ads02.auth.vo;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberReq {
    private String id;
    private String password;
    private String bojToken;
}
