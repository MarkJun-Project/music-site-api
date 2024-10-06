package fixtures;

import com.music.common.admin.domian.Admin;

public class AdminFixture {

    public static Admin create() {
        String serviceId = "AdminServiceId1";
        String password = "AdminPassword1";

        return Admin.create(serviceId, password);
    }
}
