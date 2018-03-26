package com.dimpon.happycube.write;

import com.dimpon.happycube.utils.Data3dRealPlanes;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import static com.dimpon.happycube.utils.Data3d.*;
import static com.dimpon.happycube.utils.Data3dRealPlanes.*;

public class SolutionWriterImplTest {

    @Test
    public void testWriteSolutionToFile() throws Exception {


        //Arrange
        SolutionWriter sw = SolutionWriterImpl.builder()
                .path("src/test/resources")
                .prefix("solution")
                .extension("sol")
                .build();

        Stream<int[][]> planes = Stream.of(Data3dRealPlanes.leftPlaneReal, topPlaneReal, rightPlaneReal, frontPlaneReal, bottomPlaneReal, backPlaneReal);

        //Act
        sw.writeSolutionToFile(planes.collect(Collectors.toList()));


        //Assert
        try (Stream<String> stream1 = Files.lines(Paths.get("src/test/resources/solution1.sol"))) {
            try (Stream<String> stream2 = Files.lines(Paths.get("src/test/resources/solution_sample.piece"))) {
                Iterator<String> iter1 = stream1.iterator(), iter2 = stream2.iterator();
                while(iter1.hasNext() && iter2.hasNext())
                    Assert.assertEquals(rTrim(iter1.next()), rTrim(iter2.next()));
            }
        }
    }

    private final static Pattern LTRIM = Pattern.compile("\\s+$");

    private static String rTrim(String s) {
        return LTRIM.matcher(s).replaceAll("");
    }

}
