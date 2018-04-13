package com.dimpon.happycube.pieces;

import com.dimpon.happycube.write.SolutionWriter;
import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.Stream;

import static com.dimpon.happycube.utils.Data3dRealPlanes.*;
import static com.dimpon.happycube.utils.Data3dRealPlanes.backPlaneReal;
import static com.dimpon.happycube.utils.Data3dRealPlanes.bottomPlaneReal;
import static org.mockito.Mockito.*;

public class MainProcessorSetsTest {

    private static final boolean makeLock = true;

    @Test
    public void testRollIt() throws Exception {

        final Object lock = new Object();

        //Arrange
        SolutionWriter sw = mock(SolutionWriter.class);

        @SuppressWarnings("unchecked")
        PiecesContainer<int[][][]> container = (PiecesContainer<int[][][]>) mock(PiecesContainer.class);

       /* when(container.getPiecePositionByKey(10)).thenReturn(leftPlaneReal);
        when(container.getPiecePositionByKey(20)).thenReturn(topPlaneReal);
        when(container.getPiecePositionByKey(30)).thenReturn(rightPlaneReal);
        when(container.getPiecePositionByKey(40)).thenReturn(frontPlaneReal);
        when(container.getPiecePositionByKey(50)).thenReturn(bottomPlaneReal);
        when(container.getPiecePositionByKey(60)).thenReturn(backPlaneReal);
*/


        doAnswer(invocationOnMock -> {
            synchronized (lock) {
                if (makeLock)
                    lock.notifyAll();
            }

            return null;
        }).when(sw).writeSolutionToFile(anyList());


        int[][][] left = new int[][][]{
                leftPlaneReal,
                topPlaneReal,
                rightPlaneReal
        };

        int[][][] top = new int[][][]{
                topPlaneReal,
                rightPlaneReal
        };

        int[][][] right = new int[][][]{
                rightPlaneReal,
                topPlaneReal
        };

        int[][][] front = new int[][][]{
                frontPlaneReal
        };

        int[][][] bottom = new int[][][]{
                bottomPlaneReal,
                frontPlaneReal
        };

        int[][][] back = new int[][][]{
                backPlaneReal,
                bottomPlaneReal,
                topPlaneReal
        };


        when(container.getObjectsForCombinations()).thenReturn(Stream.of(left, top, right, front, bottom, back));


        //Act
        MainProcessor processor = MainProcessorSets.builder()
                .writer(sw)
                .container(container)
                .build();

        processor.letsRoll();


        synchronized (lock) {
            if (makeLock)
                lock.wait();
        }


        //Assert
        verify(sw, times(1)).writeSolutionToFile(anyList());


    }

}
