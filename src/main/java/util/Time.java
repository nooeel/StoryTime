package util;

public class Time {

    public static float timeStarted = System.nanoTime();

    public static float getTime() {         // Zeit wie lange application läuft
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }



}
