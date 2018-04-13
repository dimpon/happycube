package com.dimpon.happycube.pieces.helpers;


@FunctionalInterface
public interface PerfectCubeChecker<T> {

     /**
      * Checks one set of planes and returns false is needed to stop permutation findings
      *
      * @param keys  based on objects the unwrapped plan is done
      * @return true of false
      */
     boolean checkAndTellNeedToSearchFurther(T keys);
}
