package com.dimpon.happycube.pieces.helpers;


@FunctionalInterface
public interface PerfectCubeChecker {

     /**
      * Checks one set of planes and returns false is needed to stop permutation findings
      *
      * @param keys codes of pieces
      * @return true of false
      */
     boolean checkAndTellNeedToSearchFurther(int[] keys);
}
