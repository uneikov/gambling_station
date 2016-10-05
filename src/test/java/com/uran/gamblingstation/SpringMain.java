package com.uran.gamblingstation;

import com.uran.gamblingstation.controller.horse.HorseRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static com.uran.gamblingstation.HorseTestData.HORSE_1_ID;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-database.xml")) {

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            HorseRestController horseRestController = appCtx.getBean(HorseRestController.class);
            horseRestController.update(HorseTestData.HORSE_ADDED, HORSE_1_ID);
            System.out.println(horseRestController.getAll().toString());
            System.out.println(horseRestController.get(HORSE_1_ID));

        /*    StakeRestController stakeController = appCtx.getBean(StakeRestController.class);*/

            /*List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/
        }
    }
}
