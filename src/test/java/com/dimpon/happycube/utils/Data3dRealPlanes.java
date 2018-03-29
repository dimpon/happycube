package com.dimpon.happycube.utils;

/**
 * The planes are taken from unfolded image provided by 360T
 */
public class Data3dRealPlanes {



    /*

    <blockcode><pre>
  o    o o o o
 ooo ooooo ooo
ooooo ooo ooooo
 ooo ooooo ooo
  o   o oo  o
     o o
     ooooo
      ooo
     ooooo
      o o
     o o o
     ooooo
      ooo
     ooooo
     o o o
      o o
     oooo
      oooo
     oooo
     oo o
 </blockcode></pre>
     */

    public static final int[][] topPlaneReal = new int[][]{
            {0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 0, 1, 1}
    };

    public static final int[][] frontPlaneReal = new int[][]{
            {1, 0, 1, 0, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 0, 1, 0}
    };

    public static final int[][] backPlaneReal = new int[][]{
            {0, 1, 0, 1, 0},
            {1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1},
            {1, 1, 1, 1, 0},
            {1, 1, 0, 1, 0}
    };

    public static final int[][] rightPlaneReal = new int[][]{
            {0, 1, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
    };

    public static final int[][] leftPlaneReal = new int[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
    };

    public static final int[][] bottomPlaneReal = new int[][]{
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1}
    };


    public static final int[][][] coloredCube = new int[][][]{
            {
                    {6, 6, 2, 6, 2},
                    {2, 2, 2, 2, 2},
                    {1, 2, 2, 2, 3},
                    {2, 2, 2, 2, 2},
                    {4, 2, 4, 2, 2}},
            {
                    {6, 6, 6, 6, 3},
                    {1, 0, 0, 0, 3},
                    {1, 0, 0, 0, 3},
                    {1, 0, 0, 0, 3},
                    {4, 4, 4, 4, 4}
            }

    };

}
