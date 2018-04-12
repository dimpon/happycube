package com.dimpon.happycube.pieces.helpers;

import com.dimpon.happycube.pieces.PiecesContainer;
import com.dimpon.happycube.utils.PerfectCubeCheckerUtils;
import com.dimpon.happycube.write.SolutionWriter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dimpon.happycube.utils.Matrix3dUtils.foldColoredCube;

/**
 * Checks is cube perfect, does additional checking and writes to file
 */
@Slf4j
@Builder
public class PerfectCubeCheckerImpl implements PerfectCubeChecker {

    private PiecesContainer container;

    private boolean findFirstSolutionOnly = false;

    private boolean findUniqueSolutionsOnly = true;

    private SolutionWriter writer;

    private SolutionUniqueChecker uniqueChecker;


    @Override
    public boolean checkAndTellNeedToSearchFurther(int[] keys) {

        log.debug("check...");

        List<int[][]> matrices = Arrays.stream(keys)
                .mapToObj(container::getPiecePositionByKey)
                .collect(Collectors.toList());


        boolean isPerfect = PerfectCubeCheckerUtils.isCubePerfect(matrices);

        if (isPerfect) {
            log.debug("perfect!");
            processPerfectCube(keys, matrices);
        }


        return !(isPerfect && findFirstSolutionOnly);
    }


    private void processPerfectCube(int[] keys, List<int[][]> matrices) {
        if (findUniqueSolutionsOnly) {
            checkUniquenessAndWriteToFile(keys, matrices);
        } else {
            writer.writeSolutionToFile(matrices);
        }
    }


    private void checkUniquenessAndWriteToFile(int[] keys, List<int[][]> matrices) {
        int[] colors = Arrays.stream(keys).map(i -> i / 10).toArray();

        int[][][] coloredCube = foldColoredCube(matrices, colors);

        boolean isUnique = uniqueChecker.check(coloredCube);
        if (isUnique) {
            writer.writeSolutionToFile(matrices);
        }

    }
}
