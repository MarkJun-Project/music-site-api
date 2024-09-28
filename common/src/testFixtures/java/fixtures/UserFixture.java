package fixtures;

import com.music.common.code.SocialType;
import com.music.common.user.domain.User;

public class UserFixture {

    public static User create() {
        String socialId = "socialId";
        SocialType socialType = SocialType.KAKAO;
        String email = "email";
        String nickname = "nickname";

        return User.create(socialId, socialType, email, nickname);
    }
}
