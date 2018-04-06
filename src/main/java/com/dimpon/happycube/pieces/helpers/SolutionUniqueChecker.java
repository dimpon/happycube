package com.dimpon.happycube.pieces.helpers;

@FunctionalInterface
public interface SolutionUniqueChecker {
    boolean check(int[][][] sol);
}
