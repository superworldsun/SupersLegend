package com.superworldsun.superslegend.util;

import net.minecraft.util.math.vector.Vector3d;

/**
 * Provides convenient vector math methods.
 */
public final class VecUtil {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    private VecUtil() {
    }

    /**
     * Calculates a reflection vector.
     *
     * @param incoming
     *            The incoming vector to calculate a reflection for.
     * @param normal
     *            The normal vector of the surface to reflect off of. Must be
     *            normalized (the length must equal 1).
     * @return The reflection vector.
     */
    public static Vector3d calculateReflection(Vector3d incoming, Vector3d normal) {
        // Most reflections will be for simple normals, so lets have shortcuts for them
        if (Math.abs(normal.x) == 1)
            return new Vector3d(-incoming.x, incoming.y, incoming.z);

        if (Math.abs(normal.y) == 1)
            return new Vector3d(incoming.x, -incoming.y, incoming.z);

        if (Math.abs(normal.z) == 1)
            return new Vector3d(incoming.x, incoming.y, -incoming.z);

        // r = d - 2(d⋅n)n
        return incoming.subtract(normal.scale(2 * normal.dot(incoming)));
    }

    /**
     * Finds the middle between the two given vector positions.
     *
     * @return The middle vector.
     */
    public static Vector3d getMiddle(Vector3d vector1, Vector3d vector2) {
        Vector3d betweenVector = vector2.subtract(vector1);
        return vector1.add(betweenVector.multiply(0.5, 0.5, 0.5));
    }
}

