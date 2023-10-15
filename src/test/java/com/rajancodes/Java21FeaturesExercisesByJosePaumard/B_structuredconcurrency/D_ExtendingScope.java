package com.rajancodes.Java21FeaturesExercisesByJosePaumard.B_structuredconcurrency;

import com.rajancodes.Java21FeaturesExercisesByJosePaumard.B_structuredconcurrency.D_model.Quotation;

public class D_ExtendingScope {

    public static void main(String[] args) {

        Quotation quotation = Quotation.readQuotation();
        System.out.println("The best quotation = " + quotation);
    }
}
