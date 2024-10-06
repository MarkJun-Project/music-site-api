package fixtures;

import com.music.common.admin.domian.Admin;
import com.music.common.notice.domian.Notice;

public class NoticeFixture {

    public static Notice create() {
        Admin admin = AdminFixture.create();
        String title = "Test Notice Title";
        String description = "This is a test description for the notice.";

        return Notice.create(admin, title, description);
    }
}
