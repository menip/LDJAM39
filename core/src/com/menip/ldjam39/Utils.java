package com.menip.ldjam39;

import com.badlogic.gdx.math.Vector2;

/**
 * Created on 7/29/17.
 */
public class Utils {
    public static final int UNIT_SIZE = 64;
    public static final int BASE_SIZE = 128;
    public static final int BULLET_RADIUS = 5;

    public static void normalize(Vector2 vector2){
        float magnitude = (float)Math.sqrt(((vector2.x * vector2.x) + (vector2.y * vector2.y)));
        vector2.x /= magnitude;
        vector2.y /= magnitude;
    }

    public static float distance (Vector2 first, Vector2 second){
        float xDiff = Math.abs(second.x - first.x);
        float yDiff = Math.abs(second.y - first.y);

        float distance = (float)Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

        return distance;
    }
}