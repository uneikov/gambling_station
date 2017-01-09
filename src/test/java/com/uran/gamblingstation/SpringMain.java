package com.uran.gamblingstation;

import com.uran.gamblingstation.controller.stake.StakeRestController;
import com.uran.gamblingstation.controller.user.AdminRestController;
import com.uran.gamblingstation.model.Stake;
import com.uran.gamblingstation.model.User;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.uran.gamblingstation.TestUtil.mockAuthorize;
import static com.uran.gamblingstation.UserTestData.USER_1;
import static com.uran.gamblingstation.UserTestData.USER_ID_1;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.DB_ACTIVE, Profiles.DB_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-database.xml", "spring/spring-mvc.xml");
            appCtx.refresh();

            mockAuthorize(USER_1);

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            final User user = adminUserController.get(USER_ID_1);
            System.out.println(user.toString());

            StakeRestController stakeController = appCtx.getBean(StakeRestController.class);
            List<Stake> stakes = stakeController.getAll();
            stakes.forEach(System.out::println);
        }
    }
}
