package com.dimpon.happycube;

import com.dimpon.happycube.loader.DataLoaderImpl;
import com.dimpon.happycube.loader.DataLoader;
import com.dimpon.happycube.pieces.OnePiece;
import com.dimpon.happycube.pieces.OnePieceImpl;
import com.dimpon.happycube.pieces.MainProcessorImpl;
import com.dimpon.happycube.write.SolutionWriter;
import com.dimpon.happycube.write.SolutionWriterImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class HappyCube {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Objects.requireNonNull(args[0], "specify input files directory [0]");
        Objects.requireNonNull(args[1], "specify input files prefix [1]");
        Objects.requireNonNull(args[2], "specify input files extension [2]");

        Objects.requireNonNull(args[3], "specify solutions files directory [3]");
        Objects.requireNonNull(args[4], "specify solutions files prefix [4]");
        Objects.requireNonNull(args[5], "specify solutions files extension [5]");

        Objects.requireNonNull(args[6], "specify findFirstSolutionOnly (true/false) [6]");

        log.info(args[0]+"/"+args[1]+"*."+args[2]+" >> "+args[3]+"/"+args[4]+"*."+args[5]);
        log.info("FindFirstSolutionOnly="+args[6]);
        log.info("");

        DataLoader loader = DataLoaderImpl.builder()
                .path(args[0])
                .prefix(args[1])
                .extension(args[2])
                .build();

        SolutionWriter writer  = SolutionWriterImpl.builder()
                .path(args[3])
                .prefix(args[4])
                .extension(args[5])
                .build();

        Stream<OnePiece> sets = IntStream.range(0,6).mapToObj(OnePieceImpl::new);

        MainProcessorImpl processor = MainProcessorImpl.builder()
                .checkSolutionUnique((a, a1) -> true)//right now don't check solution Uniqueness. print all solutions.
                .loader(loader)
                .writer(writer)
                .findFirstSolutionOnly(Boolean.parseBoolean(args[6]))
                .positionsSets(sets.collect(Collectors.toList()))
                .build();

        processor.prepareData();
        processor.letsRoll();

        log.info("Time:"+(System.currentTimeMillis()-start));


    }

}
