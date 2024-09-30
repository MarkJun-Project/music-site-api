package fixtures;

import com.music.common.attachment.domain.Attachment;
import com.music.common.board.domain.Board;
import com.music.common.code.MusicCategory;
import com.music.common.user.domain.User;

public class BoardFixture {

    public static Board create() {
        User user = UserFixture.create();
        Attachment attachment = AttachmentFixture.create();
        String title = "title";
        String description = "description";
        String songTitle = "songTitle";
        MusicCategory musicCategory = MusicCategory.ROCK;

        return Board.create(user, attachment, title, description, songTitle, musicCategory);
    }
}
