package tech.smartkit.istorybook.utils;

import java.util.Random;

public class NumberUtil {
    // create instance of Random class
    static Random rand = new Random();
    public static int nextRandomInt(){
        return rand.nextInt(Integer.MAX_VALUE);
    }

    public static double nextRandomDouble(){
        return rand.nextDouble();
    }

}
