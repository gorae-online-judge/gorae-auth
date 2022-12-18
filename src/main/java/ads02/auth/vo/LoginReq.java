package ads02.auth.vo;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginReq {
    private String id;
    private String password;
}
