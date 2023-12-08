package test;

import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.entity.UserRoleEnum;

public interface CommonTest {

    Long TEST_USER_ID = 1L;
    Long TEST_ANOTHER_USER_ID = 2L;
    String ANOTHER_PREFIX = "another-";

    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "password";
    String TEST_USER_EMAIL = "ootd@ootd.com";

    User TEST_USER = new User(
            TEST_USER_NAME,
            TEST_USER_PASSWORD,
            TEST_USER_EMAIL,
            UserRoleEnum.USER
    );

    User TEST_ANOTHER_USER = new User(
            ANOTHER_PREFIX + TEST_USER_NAME,
            ANOTHER_PREFIX + TEST_USER_PASSWORD,
            ANOTHER_PREFIX + TEST_USER_EMAIL,
            UserRoleEnum.USER
    );
}
