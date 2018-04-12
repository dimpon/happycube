package com.dimpon.happycube.pieces;

import com.dimpon.happycube.write.SolutionWriter;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dimpon.happycube.utils.Data3dRealPlanes.*;
import static com.dimpon.happycube.utils.Data3dRealPlanes.backPlaneReal;
import static com.dimpon.happycube.utils.Data3dRealPlanes.bottomPlaneReal;
import static org.mockito.Mockito.*;

public class MainSetsProcessorTest {

    @Test
    public void testRollIt() throws Exception {

        final Object lock = new Object();

        //Arrange
        SolutionWriter sw = mock(SolutionWriter.class);
        PiecesContainer container = mock(PiecesContainer.class);


        List<int[]> codes = new ArrayList<int[]>(6) {{
            add(new int[]{10});
            add(new int[]{20});
            add(new int[]{30});
            add(new int[]{40});
            add(new int[]{50});
            add(new int[]{60});
        }};


        doAnswer(invocationOnMock -> {
            synchronized (lock) {
                lock.notifyAll();
            }

            return null;
        }).when(sw).writeSolutionToFile(anyList());

        when(container.getPiecesCodesGroupedByOrigins()).thenReturn(codes.stream());

        when(container.getPiecePositionByKey(10)).thenReturn(leftPlaneReal);
        when(container.getPiecePositionByKey(20)).thenReturn(topPlaneReal);
        when(container.getPiecePositionByKey(30)).thenReturn(rightPlaneReal);
        when(container.getPiecePositionByKey(40)).thenReturn(frontPlaneReal);
        when(container.getPiecePositionByKey(50)).thenReturn(bottomPlaneReal);
        when(container.getPiecePositionByKey(60)).thenReturn(backPlaneReal);


        //Act
        MainProcessor processor = MainSetsProcessor.builder()
                .writer(sw)
                .container(container)
                .build();

        processor.letsRoll();

        synchronized (lock) {
            lock.wait();
        }


        //Assert
        verify(sw, times(1)).writeSolutionToFile(anyList());


    }

}
