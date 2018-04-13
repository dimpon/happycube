package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PiecesContainer;
import com.dimpon.happycube.utils.PerfectCubeCheckerUtils;
import com.dimpon.happycube.write.SolutionWriter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
public class PerfectCubeCheckerSets implements PerfectCubeChecker<List<int[][]>> {

    private PiecesContainer<int[][][]> container;

    private SolutionWriter writer;


    @Override
    public boolean checkAndTellNeedToSearchFurther(List<int[][]> matrices) {

        log.debug("set check...");


        boolean isPerfect = PerfectCubeCheckerUtils.isCubePerfect(matrices);

        if (isPerfect) {
            log.debug("perfect!");
            save(matrices);
        }

        return true;
    }


    private void save(List<int[][]> matrices) {
        writer.writeSolutionToFile(matrices);
    }


}
