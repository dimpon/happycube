package com.dimpon.happycube.pieces;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the data for calculation the total number of variants of pieces.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetsCountData {

    /*
     <blockquote><pre>

     The class has vector (or array) structure describes pixels of the 3d cube edges.
     Basically it is 2d cube representation.
     The 2d representation looks like:

                                      5

     [ 0] [1] [2] [3] [4] [5] [6] [7] [8] [9][10][11][12][13][14][15]
     [32]            [35]            [38]            [41]
     [33]     1      [36]      2     [39]      3     [42]     4
     [34]            [37]            [40]            [43]
     [16][17][18][19][20][21][22][23][24][25][26][27][28][29][30][31]

                                       6

     Every edge pixel can have 2 variants of color, vertex pixel can have 3 colors .
     Figures in centers are colors of planes.
     Based on this assumption I will find combinations.

     </pre></blockquote>
    */

    private static final List<int[]> edges = new ArrayList<int[]>(16 + 16 + 12) {{

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
