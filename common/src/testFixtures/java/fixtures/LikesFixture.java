package fixtures;

import com.music.common.board.domain.Board;
import com.music.common.likes.domain.Likes;
import com.music.common.user.domain.User;

public class LikesFixture {

    public static Likes create() {
        User user = UserFixture.create();
        Board board = BoardFixture.create();

        return Likes.create(user, board);
    }
}
