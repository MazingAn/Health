package com.zhzt.health.utils;

import java.util.Random;

public class RandomRanged {
    public static Random random = new Random(32);

    public static float nextFloat(final float min, final float max) {
        return min + ((max - min) * random.nextFloat());
    }
}
