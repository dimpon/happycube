package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PiecesContainer;
import com.dimpon.happycube.write.SolutionWriter;
import org.junit.Assert;
import org.junit.Test;

import static com.dimpon.happycube.utils.Data3dRealPlanes.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

public class PerfectCubeCheckerSolutionsTest {

    @Test
    public void testChecker() throws Exception {

        PiecesContainer container = mock(PiecesContainer.class);
        SolutionWriter writer = mock(SolutionWriter.class);
        SolutionUniqueChecker uniqueChecker = mock(SolutionUniqueChecker.class);

        when(uniqueChecker.check(any())).thenReturn(true);

        when(container.getPiecePositionByKey(10)).thenReturn(leftPlaneReal);
        when(container.getPiecePositionByKey(20)).thenReturn(topPlaneReal);
        when(container.getPiecePositionByKey(30)).thenReturn(rightPlaneReal);
        when(container.getPiecePositionByKey(40)).thenReturn(frontPlaneReal);
        when(container.getPiecePositionByKey(50)).thenReturn(bottomPlaneReal);
        when(container.getPiecePositionByKey(60)).thenReturn(backPlaneReal);



        PerfectCubeChecker<int[]> checker = PerfectCubeCheckerSolutions.builder()
                .findUniqueSolutionsOnly(true)
                .findFirstSolutionOnly(false)
                .container(container)
                .writer(writer)
                .uniqueChecker(uniqueChecker)
                .build();


        boolean doNext = checker.checkAndTellNeedToSearchFurther(new int[]{10,20,30,40,50,60});

        verify(writer,times(1)).writeSolutionToFile(anyList());
        verify(uniqueChecker,times(1)).check(any());

        Assert.assertTrue(doNext);


    }

}
