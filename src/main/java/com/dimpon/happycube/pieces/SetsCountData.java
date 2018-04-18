package com.dimpon.happycube.pieces;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the data for calculation the total number of combination of all pieces variants.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetsCountData {



    /*
     <blockquote><pre>

     The class has vector (or array)  describes pixels of the 3d cube edges.
     Basically it is 2d flat representation.
     The 2d representation looks like:

                                      5

     [ 0] [1] [2] [3] [4] [5] [6] [7] [8] [9][10][11][12][13][14][15]
     [32]            [35]            [38]            [41]
  4  [33]     1      [36]      2     [39]      3     [42]     4
     [34]            [37]            [40]            [43]
     [16][17][18][19][20][21][22][23][24][25][26][27][28][29][30][31]

                                       6

              1
      [0] [1] [2][3] [4]
     [15]            [5]
   4 [14]     5      [6] 2
     [13]            [7]
     [12][11][10][9] [8]
              3


              1
     [16][17][18][19][20]
     [31]            [21]
   4 [30]     6      [22] 2
     [29]            [23]
     [28][27][26][25][24]
              3

     Every edge pixel can have 2 variants of color, vertex pixel can have 3 colors .
     pixels in centers 3x3 have colors of planes, intact.
     Based on this assumption I will find combinations. Having colors variants for every pixel we can calculate the total number of variants
     just multiply the array's length in every pixel cell: e.g. 3 x 2 x 2 x 2 x 3 x 2 x 2 x 2 x 3.....
     The same approach is used for calculation the number of "bad" combinations.



                                      5

     [ 0] [1] [2] [3] [4] [5] [6] [7] [8] [9][10][11][12][13][14][15]
     [32]            [35]            [38]            [41]
   4 [33]     1      [36]      2     [39]      3     [42]     4
     [34]            [37]            [40]            [43]
     [16][17][18][19][20][21][22][23][24][25][26][27][28][29][30][31]

                                       6


     </pre></blockquote>
    */

    /**
     * Description of "bad" combinations.
     * e.g add(new MainProcessorSetsCount.CubeRule().add(5, 0).add(5, 1).add(5, 2).add(5, 3).add(5, 4));
     * Says that 5 pixels of top plane has same color, e.g. flat edge.
     */
    public static final List<MainProcessorSetsCount.CubeRule> rules = new ArrayList<MainProcessorSetsCount.CubeRule>(8) {
        {

            //vertexes. every vertex have 3 variants. Totally 24 rules

            //plane 4
            add(new MainProcessorSetsCount.CubeRule().add(3, 41).add(4, 12).add(5, 13));
            add(new MainProcessorSetsCount.CubeRule().add(5, 15).add(4, 0).add(1, 32));
            add(new MainProcessorSetsCount.CubeRule().add(1, 34).add(4, 16).add(6, 31));
            add(new MainProcessorSetsCount.CubeRule().add(6, 29).add(4, 28).add(3, 43));

            //plane 3
            add(new MainProcessorSetsCount.CubeRule().add(2, 38).add(3, 8).add(5, 9));
            add(new MainProcessorSetsCount.CubeRule().add(5, 11).add(3, 12).add(4, 41));
            add(new MainProcessorSetsCount.CubeRule().add(4, 43).add(3, 28).add(6, 27));
            add(new MainProcessorSetsCount.CubeRule().add(6, 25).add(3, 24).add(2, 40));

            //plane 2
            add(new MainProcessorSetsCount.CubeRule().add(1, 35).add(2, 4).add(5, 5));
            add(new MainProcessorSetsCount.CubeRule().add(5, 7).add(2, 8).add(3, 38));
            add(new MainProcessorSetsCount.CubeRule().add(3, 40).add(2, 24).add(6, 23));
            add(new MainProcessorSetsCount.CubeRule().add(6, 21).add(2, 20).add(1, 37));

            //plane 1
            add(new MainProcessorSetsCount.CubeRule().add(4, 32).add(1, 0).add(5, 1));
            add(new MainProcessorSetsCount.CubeRule().add(5, 3).add(1, 4).add(2, 35));
            add(new MainProcessorSetsCount.CubeRule().add(2, 37).add(1, 20).add(6, 19));
            add(new MainProcessorSetsCount.CubeRule().add(6, 17).add(1, 16).add(4, 34));

            //plane 6
            add(new MainProcessorSetsCount.CubeRule().add(4, 31).add(6, 16).add(1, 17));
            add(new MainProcessorSetsCount.CubeRule().add(1, 19).add(6, 20).add(2, 21));
            add(new MainProcessorSetsCount.CubeRule().add(2, 23).add(6, 24).add(3, 25));
            add(new MainProcessorSetsCount.CubeRule().add(3, 27).add(6, 28).add(4, 29));

            //plane 5
            add(new MainProcessorSetsCount.CubeRule().add(4, 15).add(5, 0).add(1, 1));
            add(new MainProcessorSetsCount.CubeRule().add(1, 3).add(5, 4).add(2, 5));
            add(new MainProcessorSetsCount.CubeRule().add(2, 7).add(5, 8).add(3, 9));
            add(new MainProcessorSetsCount.CubeRule().add(3, 11).add(5, 12).add(4, 13));


            //flat edges of the plane 5
            add(new MainProcessorSetsCount.CubeRule().add(5, 0).add(5, 1).add(5, 2).add(5, 3).add(5, 4));
            add(new MainProcessorSetsCount.CubeRule().add(1, 0).add(1, 1).add(1, 2).add(1, 3).add(1, 4));

            add(new MainProcessorSetsCount.CubeRule().add(5, 4).add(5, 5).add(5, 6).add(5, 7).add(5, 8));
            add(new MainProcessorSetsCount.CubeRule().add(2, 4).add(2, 5).add(2, 6).add(2, 7).add(2, 8));

            add(new MainProcessorSetsCount.CubeRule().add(5, 8).add(5, 9).add(5, 10).add(5, 11).add(5, 12));
            add(new MainProcessorSetsCount.CubeRule().add(3, 8).add(3, 9).add(3, 10).add(3, 11).add(3, 12));

            add(new MainProcessorSetsCount.CubeRule().add(5, 12).add(5, 13).add(5, 14).add(5, 15).add(5, 0));
            add(new MainProcessorSetsCount.CubeRule().add(4, 12).add(4, 13).add(4, 14).add(4, 15).add(4, 0));

            //flat edges of the plane 6
            add(new MainProcessorSetsCount.CubeRule().add(5, 16).add(5, 17).add(5, 18).add(5, 19).add(5, 20));
            add(new MainProcessorSetsCount.CubeRule().add(1, 16).add(1, 17).add(1, 18).add(1, 19).add(1, 20));

            add(new MainProcessorSetsCount.CubeRule().add(5, 20).add(5, 21).add(5, 22).add(5, 23).add(5, 24));
            add(new MainProcessorSetsCount.CubeRule().add(2, 20).add(2, 21).add(2, 22).add(2, 23).add(2, 24));

            add(new MainProcessorSetsCount.CubeRule().add(5, 24).add(5, 25).add(5, 26).add(5, 27).add(5, 28));
            add(new MainProcessorSetsCount.CubeRule().add(3, 20).add(3, 21).add(3, 22).add(3, 23).add(3, 24));

            add(new MainProcessorSetsCount.CubeRule().add(5, 28).add(5, 29).add(5, 30).add(5, 31).add(5, 16));
            add(new MainProcessorSetsCount.CubeRule().add(4, 28).add(4, 29).add(4, 30).add(4, 31).add(4, 16));

            //flat edges of vertical edges
            add(new MainProcessorSetsCount.CubeRule().add(4, 0).add(4, 32).add(4, 33).add(4, 34).add(4, 16));
            add(new MainProcessorSetsCount.CubeRule().add(1, 0).add(1, 32).add(1, 33).add(1, 34).add(1, 16));

            add(new MainProcessorSetsCount.CubeRule().add(1, 4).add(1, 35).add(1, 36).add(1, 37).add(1, 20));
            add(new MainProcessorSetsCount.CubeRule().add(2, 4).add(2, 35).add(2, 36).add(2, 37).add(2, 20));

            add(new MainProcessorSetsCount.CubeRule().add(2, 8).add(2, 38).add(2, 39).add(2, 40).add(2, 24));
            add(new MainProcessorSetsCount.CubeRule().add(3, 8).add(3, 38).add(3, 39).add(3, 40).add(3, 24));

            add(new MainProcessorSetsCount.CubeRule().add(3, 12).add(3, 41).add(3, 42).add(3, 43).add(3, 28));
            add(new MainProcessorSetsCount.CubeRule().add(4, 12).add(4, 41).add(4, 42).add(4, 43).add(4, 28));


        }
    };

    public static final List<int[]> cubePixels = new ArrayList<int[]>(16 + 16 + 12) {{

        add(new int[]{1, 4, 5});//0
        add(new int[]{1, 5});
        add(new int[]{1, 5});
        add(new int[]{1, 5});
        add(new int[]{1, 2, 5});//4
        add(new int[]{2, 5});
        add(new int[]{2, 5});
        add(new int[]{2, 5});
        add(new int[]{2, 3, 5});//8
        add(new int[]{3, 5});
        add(new int[]{3, 5});
        add(new int[]{3, 5});
        add(new int[]{3, 4, 5});//12
        add(new int[]{4, 5});
        add(new int[]{4, 5});
        add(new int[]{4, 5});

        add(new int[]{1, 4, 6});//16
        add(new int[]{1, 6});
        add(new int[]{1, 6});
        add(new int[]{1, 6});
        add(new int[]{1, 2, 6});//20
        add(new int[]{2, 6});
        add(new int[]{2, 6});
        add(new int[]{2, 6});
        add(new int[]{2, 3, 6});//24
        add(new int[]{3, 6});
        add(new int[]{3, 6});
        add(new int[]{3, 6});
        add(new int[]{3, 4, 6});//28
        add(new int[]{4, 6});
        add(new int[]{4, 6});
        add(new int[]{4, 6});

        add(new int[]{1, 4});//32
        add(new int[]{1, 4});
        add(new int[]{1, 4});

        add(new int[]{1, 2});//35
        add(new int[]{1, 2});
        add(new int[]{1, 2});

        add(new int[]{2, 3});//38
        add(new int[]{2, 3});
        add(new int[]{2, 3});

        add(new int[]{3, 4});//41
        add(new int[]{3, 4});
        add(new int[]{3, 4});

    }};


}
