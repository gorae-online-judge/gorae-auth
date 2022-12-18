package ads02.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String bojToken;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public Member(String nickname, String password, String bojToken) {
        this.nickname = nickname;
        this.password = password;
        this.bojToken = bojToken;
    }
}
