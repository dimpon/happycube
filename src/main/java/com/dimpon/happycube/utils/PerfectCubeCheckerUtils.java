package com.dimpon.happycube.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.dimpon.happycube.utils.Matrix3dUtils.*;

@Slf4j
@UtilityClass
public class PerfectCubeCheckerUtils {

    public static boolean isCubePerfectByPlanes(List<int[][]> unfolded) {

        if (!isMakeSenseToCheckFurther(unfolded)) {
            return false;
        }

        int[][][] cube = foldTheCube(unfolded);
        return Matrix3dUtils.isCubePerfect(cube);
    }


    public static boolean isCubePerfectByEdges(List<Map<MatrixUtils.Edge, Integer>> edges) {

        if (!isMakeSenseToCheckFurtherUsingMagicNumbers(edges)) {
            return false;
        }
        return isCubePerfectUsingEdges(edges);
    }


  }
