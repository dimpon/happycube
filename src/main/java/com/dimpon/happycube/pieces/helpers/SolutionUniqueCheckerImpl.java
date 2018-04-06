package com.dimpon.happycube.pieces.helpers;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

import static com.dimpon.happycube.utils.Matrix3dUtils.mirrorCube;
import static com.dimpon.happycube.utils.Matrix3dUtils.planeOneToTop;
import static com.dimpon.happycube.utils.Matrix3dUtils.rotateCubeZ;

@Slf4j
public class SolutionUniqueCheckerImpl implements SolutionUniqueChecker {

    private final List<int[][][]> coloredCubes = new ArrayList<>();

    @Override
    public synchronized boolean check(int[][][] sol) {
        sol = planeOneToTop(sol);
        log.debug("top:" + sol[0][2][2]);

        for (int[][][] u : coloredCubes) {
            if (check(u, sol)) {
                return false;
            }
        }

        coloredCubes.add(sol);//all unique cubes are stored with 1 plane on top
        return true;
    }


    private boolean checkOne(int[][][] u, int[][][] n) {

        if (Arrays.deepEquals(u, n)) {
            return true;
        }

        if (Arrays.deepEquals(u, rotateCubeZ(n))) {
            return true;
        }

        if (Arrays.deepEquals(u, rotateCubeZ(rotateCubeZ(n)))) {
            return true;
        }

        if (Arrays.deepEquals(u, rotateCubeZ(rotateCubeZ(rotateCubeZ(n))))) {
            return true;
        }

        return false;
    }

    private boolean check(int[][][] u, int[][][] n) {

        if (checkOne(u, n)) {
            return true;
        }

        if (checkOne(u, mirrorCube(n))) {
            return true;
        }

        return false;
    }


}
