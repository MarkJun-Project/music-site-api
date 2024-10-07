package fixtures;

import com.music.common.follow.domain.Follow;
import com.music.common.user.domain.User;

public class FollowFixture {
    public static Follow create() {
        User followee = UserFixture.create();
        User follower = UserFixture.create();

        return Follow.create(follower,followee);
    }
}
