package com.dimpon.happycube.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.dimpon.happycube.utils.Matrix3dUtils.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerfectCubeChecker {

    public static boolean isCubePerfect(List<int[][]> unfolded) {

        if (!isMakeSenseToCheckFurther(unfolded)) {
            return false;
        }

        int[][][] cube = foldTheCube(unfolded);
        return Matrix3dUtils.isCubePerfect(cube);
    }

    /**
     *
     *012
     * 3
     * 4
     * 5
     *
     * @param unfolded
     * @return
     */

    public static boolean isCubePerfectUsingEdges(List<Map<MatrixUtils.Edge, Integer>> unfolded) {

        //1-3
       /* if (!MatrixUtils.checkOneEdge(
                unfolded.get(0).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(1).get(MatrixUtils.Edge.BOTTOM),
                unfolded.get(3).get(MatrixUtils.Edge.TOP),
                unfolded.get(2).get(MatrixUtils.Edge.LEFT_REVERSE)
        )) {
            return false;
        }

        //0-1
        if (!MatrixUtils.checkOneEdge(
                unfolded.get(5).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(3).get(MatrixUtils.Edge.LEFT_REVERSE),
                unfolded.get(0).get(MatrixUtils.Edge.RIGHT),
                unfolded.get(1).get(MatrixUtils.Edge.LEFT)
        )) {
            return false;
        }*/

        //1-2
       if (!MatrixUtils.checkOneEdge(
                unfolded.get(5).get(MatrixUtils.Edge.RIGHT_REVERSE),
                unfolded.get(3).get(MatrixUtils.Edge.RIGHT_REVERSE),

                unfolded.get(2).get(MatrixUtils.Edge.LEFT),
                unfolded.get(1).get(MatrixUtils.Edge.RIGHT)
        )) {
            return false;
        }





                /*edge(unfolded.get(0), Edge.BOTTOM_REVERSE),
                edge(unfolded.get(3), Edge.LEFT),


                edge(unfolded.get(2), Edge.BOTTOM_REVERSE),
                edge(unfolded.get(3), Edge.RIGHT_REVERSE),


                edge(unfolded.get(1), Edge.BOTTOM),
                edge(unfolded.get(3), Edge.TOP)*/



        return true;
}
}
