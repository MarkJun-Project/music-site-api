package fixtures;

import com.music.common.board.domain.Board;
import com.music.common.comment.domain.Comment;
import com.music.common.user.domain.User;

public class CommentFixture {
    public static Comment createComment() {
        User user = UserFixture.create();
        Board board = BoardFixture.create();
        String comment = "댓글입니다.";

        return Comment.createComment(user, board, comment);
    }

    public static Comment createReply() {
        User user = UserFixture.create();
        Board board = BoardFixture.create();
        String comment = "답글입니다.";

        return Comment.createReply(user, board, comment, createComment());
    }
}
