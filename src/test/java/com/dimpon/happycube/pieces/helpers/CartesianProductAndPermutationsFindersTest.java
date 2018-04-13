package com.dimpon.happycube.pieces.helpers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.dimpon.happycube.utils.Data3dRealPlanes.*;
import static com.dimpon.happycube.utils.Data3dRealPlanes.backPlaneRealLilac;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Slf4j
public class CartesianProductAndPermutationsFindersTest {


    @Test
    public void findCombinations2() throws Exception {

        //Assert
        List<int[]> input = new ArrayList<int[]>(3) {{
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
            add(new int[]{1, 0});
        }};

        //Act
        CartesianProductFinder finder = new CartesianProductFinder(input);
        List<int[]> combinations = finder.combinations().collect(Collectors.toList());


        //Assert
        Assert.assertEquals((int) Math.pow(2, 16), combinations.size());


        /*Set<String> collect = combinations.stream()
                .map(e -> Arrays.stream(e)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.toSet());


        collect.forEach(log::debug);*/

    }


    static final int[][][] set1 = new int[][][]{
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            },
            leftPlaneReal,
            leftPlaneRealLilac,
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            }
    };

    static final int[][][] set2 = new int[][][]{
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            },
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            },
            topPlaneReal,
            topPlaneRealLilac
    };

    static final int[][][] set3 = new int[][][]{
            {
                    {1, 1, 0, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 0, 1}
            },
            rightPlaneReal,
            rightPlaneRealLilac,
            {
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 0, 1, 1}
            }
    };

    static final int[][][] set4 = new int[][][]{
            frontPlaneReal,
            frontPlaneRealLilac
    };

    static final int[][][] set5 = new int[][][]{
            bottomPlaneReal,
            bottomPlaneRealLilac
    };

    static final int[][][] set6 = new int[][][]{
            backPlaneReal,
            backPlaneRealLilac
    };

    @Test
    public void findCombinationsAdvanced() throws Exception {
        //Assert
        List<int[][][]> input = new ArrayList<int[][][]>(3) {{
            add(set1);
            add(set2);
            add(set3);
            add(set4);
            add(set5);
            add(set6);
        }};

        @SuppressWarnings("unchecked")
        Consumer<int[][][]> consumer = (Consumer<int[][][]>) mock(Consumer.class);

        //final AtomicInteger count = new AtomicInteger(0);
        //Act
        CartesianProductFinderAdvanced cpFinder = new CartesianProductFinderAdvanced(input);
        cpFinder.combinationsWithoutSaving(consumer);

        //Arrange
        verify(consumer, times(2)).accept(any());
        verify(consumer, times(1)).accept(new int[][][]{leftPlaneReal, topPlaneReal, rightPlaneReal, frontPlaneReal, bottomPlaneReal, backPlaneReal});
        verify(consumer, times(1)).accept(new int[][][]{leftPlaneRealLilac, topPlaneRealLilac, rightPlaneRealLilac, frontPlaneRealLilac, bottomPlaneRealLilac, backPlaneRealLilac});


    }

    @Test
    public void findCombinations() throws Exception {

        //Assert
        List<int[]> input = new ArrayList<int[]>(3) {{
            add(new int[]{11, 12, 13, 14, 15, 16});
            add(new int[]{21, 22, 23});
            add(new int[]{31, 32});
        }};

        //Act
        CartesianProductFinder finder = new CartesianProductFinder(input);
        List<int[]> combinations = finder.combinations().collect(Collectors.toList());

        //Assert
        Assert.assertEquals(36, combinations.size());

        Set<String> collect = combinations.stream()
                .map(e -> Arrays.stream(e)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining("-")))
                .collect(Collectors.toSet());

        //checkAndTellNeedToSearchFurther that after conversion to string and placing in set the size is the same
        Assert.assertEquals(36, collect.size());

        //CartesianProduct with arrays looping
        for (int a : input.get(0)) {
            for (int b : input.get(1)) {
                for (int c : input.get(2)) {
                    Assert.assertTrue(collect.contains(a + "-" + b + "-" + c));
                }
            }
        }
    }

    @Test
    public void findPermutations() throws Exception {

        //Arrange
        @SuppressWarnings("unchecked")
        PerfectCubeChecker<int[]> checker = (PerfectCubeChecker<int[]>) mock(PerfectCubeChecker.class);

        //when(checker.checkOnePermutation(any())).thenReturn(false);
        when(checker.checkAndTellNeedToSearchFurther(any())).thenReturn(true);

        PermutationsFinder finder = new PermutationsFinder(checker);

        //Act & Assert
        finder.permutations(new int[]{11, 12, 13, 14});
        finder.permutations(new int[]{11, 12, 13, 14, 15});
        finder.permutations(new int[]{11, 12, 13, 14, 15, 16});

        verify(checker, times((24 + 120 + 720))).checkAndTellNeedToSearchFurther(any());
    }

    @Test
    public void testFindFirstPermutations() throws Exception {

        //Arrange
        PerfectCubeChecker<int[]> obj = PerfectCubeCheckerSolutions.builder()
                .findFirstSolutionOnly(true)
                .build();

        PerfectCubeChecker checker = spy(obj);

        doReturn(false).when(checker).checkAndTellNeedToSearchFurther(any());

        PermutationsFinder finder = new PermutationsFinder(checker);

        //Act
        finder.permutations(new int[]{11, 12, 13, 14});

        //Assert
        verify(checker, times(1)).checkAndTellNeedToSearchFurther(any());
    }

    @Test
    public void testStopSearchInTheMiddle() throws Exception {

        //Arrange
        PerfectCubeChecker<int[]> obj = PerfectCubeCheckerSolutions.builder()
                .findFirstSolutionOnly(true)
                .build();

        PerfectCubeChecker checker = spy(obj);

        doReturn(true).when(checker).checkAndTellNeedToSearchFurther(any());
        doReturn(false).when(checker).checkAndTellNeedToSearchFurther(eq(new int[]{11, 13, 14, 12}));

        PermutationsFinder finder = new PermutationsFinder(checker);

        //Act & Assert
        finder.permutations(new int[]{11, 12, 13, 14});

        verify(checker, times(4)).checkAndTellNeedToSearchFurther(any());

    }
}