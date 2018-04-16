package com.dimpon.happycube.pieces;

import com.dimpon.happycube.pieces.helpers.CartesianProductFinder;
import com.dimpon.happycube.pieces.helpers.CartesianProductFinderLight;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Builder
public class MainProcessorSetsCount implements MainProcessor {

     /*

     <blockquote><pre>

     The class has vector (or array) structure describes pixels of the 3d cube edges.
     Basically it is 2d cube representation.
     The 2d representation looks like:


     [ 0] [1] [2] [3] [4] [5] [6] [7] [8] [9][10][11][12][13][14][15]
     [32]            [35]            [38]            [41]
     [33]     1      [36]      2     [39]      3     [42]     4
     [34]            [37]            [40]            [43]
     [16][17][18][19][20][21][22][23][24][25][26][27][28][29][30][31]

                                       6

     Every edge pixel can have 2 variants of color, corner pixel can have 3 colors.
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

    private static final List<int[]> testEdges = new ArrayList<int[]>(20) {
        {
            add(new int[]{1, 5, 4});//0
            add(new int[]{1, 5});
            add(new int[]{1, 5, 2});//2
            add(new int[]{2, 5});
            add(new int[]{2, 5, 3});//4
            add(new int[]{3, 5});
            add(new int[]{3, 5, 4});//6
            add(new int[]{4, 5});

            add(new int[]{1, 4, 6});//8
            add(new int[]{1, 6});
            /*add(new int[]{1, 2, 6});//10
            add(new int[]{2, 6});
            add(new int[]{2, 3, 6});//12
            add(new int[]{3, 6});
            add(new int[]{3, 4, 6});//14
            add(new int[]{4, 6});

            add(new int[]{1, 4});//16

            add(new int[]{1, 2});

            add(new int[]{2, 3});

            add(new int[]{3, 4});//19*/
        }
    };


    private static final List<int[][]> testEdgesIndexes = new ArrayList<int[][]>(12) {
        {
            add(new int[][]{{1}, {0, 1, 2}});
            add(new int[][]{{5}, {0, 1, 2}});

            add(new int[][]{{2}, {2, 3, 4}});
            add(new int[][]{{5}, {2, 3, 4}});


            add(new int[][]{{3}, {4, 5, 6}});
            add(new int[][]{{5}, {4, 5, 6}});

            add(new int[][]{{4}, {6, 7, 0}});
            add(new int[][]{{5}, {6, 7, 0}});

        }
    };


  /*  private static final List<int[]> testSet = new ArrayList<int[]>(10) {
        {
            add(new int[]{1, 5});
            add(new int[]{1, 2, 5});
            add(new int[]{2, 5});
            add(new int[]{8, 9});
        }
    };*/


    private static final List<int[]> edgesIndexes = new ArrayList<int[]>(12) {
        {
            add(new int[]{0, 1, 2, 3, 4});
            add(new int[]{4, 5, 6, 7, 8});
            add(new int[]{8, 9, 10, 11, 12});
            add(new int[]{12, 13, 14, 15, 0});

            add(new int[]{16, 17, 18, 19, 20});
            add(new int[]{20, 21, 22, 23, 24});
            add(new int[]{24, 25, 26, 27, 28});
            add(new int[]{28, 29, 30, 31, 16});

            add(new int[]{0, 32, 33, 34, 16});
            add(new int[]{4, 35, 36, 37, 20});
            add(new int[]{8, 38, 39, 40, 24});
            add(new int[]{12, 41, 42, 43, 28});
        }
    };


    @Override
    public void letsRoll() {


        //total number of combinations
        long count = combinationsCount(testEdges);

        log.info("count:" + count);


        IntStream.rangeClosed(0, 11)
                .forEach(i -> {
                });

        final AtomicLong good = new AtomicLong(0);
        final AtomicLong bad = new AtomicLong(0);


        CartesianProductFinderLight cfl = CartesianProductFinderLight.builder()
                .input(testEdges)
                .task(ints -> {
                    if (check(ints)) {
                        good.incrementAndGet();
                        log.info(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")));
                    } else {
                        bad.incrementAndGet();
                        log.info(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")) + " *");
                    }
                })
                .build();

        cfl.combinations();

        log.info("good:" + good.toString());
        log.info("bad:" + bad.toString());
        log.info("bad more:" + flatEdgeCombinationsCount(testEdges, testEdgesIndexes));


        List<int[]> collect = IntStream.range(0, testEdges.size()).mapToObj(i -> (i < 8) ? new int[]{5} : testEdges.get(i)).collect(Collectors.toList());
        log.info("5 5 5 5:" + combinationsCount(collect));

        List<String> combinations = combinations(collect).collect(Collectors.toList());

        List<int[]> collect1 = IntStream.range(0, testEdges.size()).mapToObj(i -> (i < 7) ? new int[]{5} : testEdges.get(i)).collect(Collectors.toList());
        log.info("5 5 5 x:" + combinationsCount(collect1));

        List<String> combinations1 = combinations(collect1).collect(Collectors.toList());

        List<int[]> collect2 = IntStream.range(0, testEdges.size()).mapToObj(i -> (i < 5) ? new int[]{5} : testEdges.get(i)).collect(Collectors.toList());
        log.info("5 5 x x:" + combinationsCount(collect2));

        List<int[]> collect22 = IntStream.range(0, testEdges.size()).mapToObj(i -> (i < 7 && i>1) ? new int[]{5} : testEdges.get(i)).collect(Collectors.toList());
        log.info("x 5 5 x:" + combinationsCount(collect22));

        List<String> combinations2 = combinations(collect2).collect(Collectors.toList());

        List<int[]> collect3 = IntStream.range(0, testEdges.size()).mapToObj(i -> (i < 3) ? new int[]{5} : testEdges.get(i)).collect(Collectors.toList());
        log.info("5 x x x:" + combinationsCount(collect3));

        List<String> combinations3 = combinations(collect3).collect(Collectors.toList());


        boolean b1 = CollectionUtils.containsAll(combinations1, combinations);

        boolean b2 = CollectionUtils.containsAll(combinations2, combinations1);
        boolean b3 = CollectionUtils.containsAll(combinations3, combinations2);



        /*String c = cpf.combinations().map(ints -> Arrays.stream(ints)
                .boxed().map(String::valueOf)
                .collect(Collectors.joining("-")))
                //.filter(s -> !s.startsWith("5-5-5-5-5"))
                .collect(Collectors.joining("\n"));*/

        log.info("\n");

       /* cpf.combinations(ints -> {
            log.info(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")));
        });*/

        //List<int[]> combinations = cpf.combinations().collect(Collectors.toList());


        //4+4+4+4

       /* cpf.combinations()
                //.filter(ints -> !check(ints))
                .forEach(ints -> {
                    log.info(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining(",")));
                });*/

        //.count();


        //long combinations = cpf.combinations();

        //log.info("count:" + cpf.combinations().count());


    }


    private boolean check(int[] arr) {

        if (arr[0] == arr[1] && arr[2] == arr[1])
            return false;

        if (arr[2] == arr[3] && arr[4] == arr[3])
            return false;

        if (arr[4] == arr[5] && arr[6] == arr[5])
            return false;

        if (arr[6] == arr[7] && arr[0] == arr[7])
            return false;

        return true;
    }


    private long combinationsCount(List<int[]> pixels) {
        return pixels.stream().mapToLong(v -> v.length)
                .reduce((left, right) -> left * right).orElse(0);
    }

    private Stream<String> combinations(List<int[]> pixels) {

        List<String> res = new ArrayList<>();

        CartesianProductFinderLight.builder()
                .input(pixels)
                .task(ints -> {
                    res.add(Arrays.stream(ints).boxed().map(String::valueOf).collect(Collectors.joining("-")));
                })
                .build().combinations();

        return res.stream();
    }


    private long flatEdgeCombinationsCount(List<int[]> all, List<int[][]> edgesDescr) {

        for (int[][] anEdgesDescr : edgesDescr) {
            List<int[]> all1 = new ArrayList<>(all);
            int[] ints = anEdgesDescr[1];
            for (int anInt : ints) {
                all1.set(anInt, anEdgesDescr[0]);
            }
            log.info("co: " + combinationsCount(all1));
        }
        return 0L;
    }

}
