package com.rajancodes.Java21FeaturesExercisesByJosePaumard.B_structuredconcurrency;

import com.rajancodes.Java21FeaturesExercisesByJosePaumard.B_structuredconcurrency.C_model.Weather;

public class C_ShutdownOnSuccessScope {

    public static void main(String[] args) {

        Weather weather = Weather.readWeather();
        System.out.println("Same weather, but faster = " + weather);
    }
}
