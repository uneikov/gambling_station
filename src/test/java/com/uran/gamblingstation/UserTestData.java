package com.uran.gamblingstation;

import com.uran.gamblingstation.matcher.ModelMatcher;
import com.uran.gamblingstation.model.Role;
import com.uran.gamblingstation.model.User;

import java.util.Objects;

import static com.uran.gamblingstation.model.BaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID_1 = START_SEQ;
    public static final int USER_ID_2 = START_SEQ + 1;
    public static final int ADMIN_ID = START_SEQ + 2;
    public static final int STATION_ID = START_SEQ + 3;


    public static final User USER_1 = new User(USER_ID_1, "User1", "user1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_ID_2, "User2", "user2@yandex.ru", "password2", Role.ROLE_USER);
    public static final User ADMIN  = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User STATION = new User(STATION_ID, "Station", "station@gamblingstation.com", "stationpass", Role.ROLE_STATION);


    public static final ModelMatcher<User> USER_MATCHER = new ModelMatcher<>(User.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getPassword(), actual.getPassword())
                           /* && Objects.equals(expected.getRegistered(), actual.getRegistered())*/
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );
}
