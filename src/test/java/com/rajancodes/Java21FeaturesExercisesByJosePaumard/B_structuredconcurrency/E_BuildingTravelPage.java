package com.rajancodes.Java21FeaturesExercisesByJosePaumard.B_structuredconcurrency;

import com.rajancodes.Java21FeaturesExercisesByJosePaumard.B_structuredconcurrency.E_model.TravelPage;

public class E_BuildingTravelPage {

    public static void main(String[] args) {

        TravelPage travelPage = TravelPage.readTravelPage();
        System.out.println("Final travel page = " + travelPage);
    }
}
