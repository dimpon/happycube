package com.dimpon.happycube;

import com.dimpon.happycube.loader.DataLoaderImpl;
import com.dimpon.happycube.loader.DataLoader;
import com.dimpon.happycube.pieces.*;
import com.dimpon.happycube.pieces.helpers.SolutionUniqueCheckerImpl;
import com.dimpon.happycube.write.SolutionWriter;
import com.dimpon.happycube.write.SolutionWriterImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.dimpon.happycube.HappyCube.Action.SETS;
import static com.dimpon.happycube.HappyCube.Action.SETSCOUNT;
import static com.dimpon.happycube.HappyCube.Action.SOLUTIONS;
import static com.dimpon.happycube.pieces.SetsCountData.cubePixels;
import static com.dimpon.happycube.pieces.SetsCountData.rules;

@Slf4j
public class HappyCube {


    enum Action {
        SOLUTIONS,
        SETS,
        SETSCOUNT
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        if (args.length < 1) {
            usage();
        }

        if (args[0].equals(SOLUTIONS.name())) {
            solutions(args);
        } else if (args[0].equals(SETS.name())) {
            sets(args);
        } else if (args[0].equals(SETSCOUNT.name())) {
            setsCount(args);
        } else {
            usage();
        }

        log.info("Time:" + (System.currentTimeMillis() - start));
    }


    private static void setsCount(String[] args) {

        long combinations = MainProcessorSetsCount.builder()
                .cubePixels(cubePixels)
                .rules(rules)
                .build().letsRoll();

        //divide on 24 for detect unique. One cube can be rotated 24 times.

        long l =  combinations / 24;

        log.info("Valid combinations number is "+l);
    }


    private static void solutions(String[] args) {

        if (args.length < 9) {
            usage();
        }

        Objects.requireNonNull(args[1], "specify input files directory [1]");
        Objects.requireNonNull(args[2], "specify input files prefix [21]");
        Objects.requireNonNull(args[3], "specify input files extension [3]");

        Objects.requireNonNull(args[4], "specify solutions files directory [4]");
        Objects.requireNonNull(args[5], "specify solutions files prefix [5]");
        Objects.requireNonNull(args[6], "specify solutions files extension [6]");

        Objects.requireNonNull(args[7], "specify findFirstSolutionOnly (true/false) [7]");

        Objects.requireNonNull(args[8], "specify findUniqueSolutionsOnly (true/false) [8]");

        log.info(args[1] + "/" + args[2] + "*." + args[3] + " >> " + args[4] + "/" + args[5] + "*." + args[6]);
        log.info("FindFirstSolutionOnly=" + args[7]);
        log.info("findUniqueSolutionsOnly=" + args[8]);
        log.info("");

        DataLoader loader = DataLoaderImpl.builder()
                .path(args[1])
                .prefix(args[2])
                .extension(args[3])
                .build();

        SolutionWriter writer = SolutionWriterImpl.builder()
                .path(args[4])
                .prefix(args[5])
                .extension(args[6])
                .build();

        PiecesContainer<int[]> container = new PiecesContainerSolutions();
        container.createInitialPieces(loader);

        MainProcessor processor = MainProcessorSolutions.builder()
                .checkSolutionUnique(new SolutionUniqueCheckerImpl())
                .container(container)
                .writer(writer)
                .findFirstSolutionOnly(Boolean.parseBoolean(args[7]))
                .findUniqueSolutionsOnly(Boolean.parseBoolean(args[8]))
                .build();

        processor.letsRoll();

    }

    private static void sets(String[] args) {

        if (args.length < 4) {
            usage();
        }

        Objects.requireNonNull(args[1], "specify solutions files directory [1]");
        Objects.requireNonNull(args[2], "specify solutions files prefix [2]");
        Objects.requireNonNull(args[3], "specify solutions files extension [3]");

        log.info(" >> " + args[1] + "/" + args[2] + "*." + args[3]);

        log.info("");

        SolutionWriter writer = SolutionWriterImpl.builder()
                .path(args[1])
                .prefix(args[2])
                .extension(args[3])
                .build();


        PiecesContainer<int[][][]> container = new PiecesContainerSets();
        container.createInitialPieces(DataLoader.STUB);

        MainProcessor processor = MainProcessorSets.builder()
                .writer(writer)
                .container(container)
                .build();

        processor.letsRoll();


    }

    private static void usage() {
        log.info("Usage: <SOLUTIONS/SETS> <input files directory> <input files prefix> <input files extension> <solutions directory> <solutions files prefix> <solutions files extension> <findFirstSolutionOnly (true/false)> <findUniqueSolutionsOnly (true/false)>");
        System.exit(1);
    }

}
