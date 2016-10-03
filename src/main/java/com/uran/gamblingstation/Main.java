package com.uran.gamblingstation;

import com.uran.gamblingstation.repository.HorseRepository;
import com.uran.gamblingstation.repository.jpa.JpaHorseRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        System.out.format("Hello! This is a Gambling Staion Web Progect.\n");
        System.out.format("Developed in scope of the TopJava webinar");
        HorseRepository repository = new JpaHorseRepositoryImpl();
        /*"SELECT h FROM Horse h  ORDER BY h.name";*/
        repository.getAll();
    }
}
