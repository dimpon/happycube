package com.dimpon.happycube.pieces;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MainProcessorSetsCounntTest {

    @Test
    public void testRunProcessor() throws Exception {


        log.info("");


        MainProcessor mp = MainProcessorSetsCount.builder().build();

        mp.letsRoll();


    }


}
