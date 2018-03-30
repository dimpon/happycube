package com.dimpon.happycube.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.dimpon.happycube.utils.Matrix3dUtils.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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


     /**
     * @param unfolded
     * @return
     */

    private static boolean isCubePerfectUsingEdges(List<Map<MatrixUtils.Edge, Integer>> unfolded) {


        //1-3
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.BOTTOM_REVERSE)
        )) {
            log.debug("1-3");
            return false;
        }

        //0-1
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(5).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(0).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(1).get(MatrixUtils.Edge.LEFT),
                unfolded.get(3).get(MatrixUtils.Edge.TOP_REVERSE)
        )) {
            log.debug("0-1");
            return false;
        }

        //1-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(5).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(2).get(MatrixUtils.Edge.LEFT),
                unfolded.get(1).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(3).get(MatrixUtils.Edge.TOP)
        )) {
            log.debug("1-2");
            return false;
        }

        //1-5
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.TOP_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.TOP),
                unfolded.get(5).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(2).get(MatrixUtils.Edge.TOP_REVERSE)
        )) {
            log.debug("1-5");
            return false;
        }

        //3-4
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(4).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.BOTTOM)
        )) {
            log.debug("4-3");
            return false;
        }


        //4-5
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.TOP),
                unfolded.get(4).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(5).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.TOP)

        )) {
            log.debug("4-5");
            return false;
        }


        /**
         * <blockquote><pre>
         * 000 111 222
         * 000 111 222
         *
         *     333
         *     333
         *
         *     444
         *     444
         *
         *     555
         *     555
         *
         * </pre></blockquote>
         */


        //4-0
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(3).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(4).get(MatrixUtils.Edge.LEFT),
                unfolded.get(0).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.LEFT_REVERSE)

        )) {
            log.debug("4-0");
            return false;
        }

        //4-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(3).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(4).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(2).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.RIGHT_REVERSE)

        )) {
            log.debug("4-2");
            return false;
        }


        //3-0
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.LEFT),
                unfolded.get(0).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(4).get(MatrixUtils.Edge.TOP_REVERSE)

        )) {
            log.debug("3-0");
            return false;
        }

        //3-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM_REVERSE),
                unfolded.get(3).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(2).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(4).get(MatrixUtils.Edge.TOP)

        )) {
            log.debug("3-2");
            return false;
        }

        //5-0
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(4).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.LEFT),
                unfolded.get(0).get(MatrixUtils.Edge.TOP),
                unfolded.get(1).get(MatrixUtils.Edge.LEFT_REVERSE)

        )) {
            log.debug("5-0");
            return false;
        }

        //5-2
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(4).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(5).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(2).get(MatrixUtils.Edge.TOP_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.RIGHT_REVERSE)

        )) {
            log.debug("5-2");
            return false;
        }


        return true;
    }
}
