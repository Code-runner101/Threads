package Lesson_5.lvl2;

import java.util.Arrays;

public class Threads {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] mass = new float[SIZE];

    public static void fillArray(){
        Arrays.fill(mass, 1);
    }

    public static void cutArray(){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < mass.length; i++) {
            mass[i] = (float) (mass[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        long averageTime = end - startTime;
        System.out.println("Working time by 1 thread: " + averageTime + "ms");
        System.out.println(mass[HALF]);
    }

    public static void cutArrayByTwo(){
        long start = System.currentTimeMillis();
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        System.arraycopy(mass, 0, a1, 0, HALF);
        System.arraycopy(mass, HALF, a2, 0, HALF);

        Thread t1 = new Thread((Runnable) () -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread((Runnable) () -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, mass, 0, HALF);
        System.arraycopy(a2, 0, mass, HALF, HALF);

        long end = System.currentTimeMillis();
        long averageTime = end - start;

        System.out.println("Working time by 1 thread: " + averageTime + "ms");
        System.out.println(mass[HALF]);
    }

    public static void main(String[] args) {
        fillArray();
        cutArray();

        fillArray();
        cutArrayByTwo();
    }
}
