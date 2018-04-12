package com.dimpon.happycube.loader;

import com.dimpon.happycube.pieces.helpers.CartesianProductFinder;
import com.dimpon.happycube.pieces.helpers.PerfectCubeChecker;
import com.dimpon.happycube.pieces.helpers.PerfectCubeCheckerImpl;
import com.dimpon.happycube.pieces.helpers.PermutationsFinder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        PerfectCubeChecker checker = mock(PerfectCubeChecker.class);

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
        PerfectCubeChecker obj = PerfectCubeCheckerImpl.builder()
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
        PerfectCubeChecker obj = PerfectCubeCheckerImpl.builder()
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