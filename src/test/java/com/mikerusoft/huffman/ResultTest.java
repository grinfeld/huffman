package com.mikerusoft.huffman;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    @Test
    void decodeWithEndline() throws Exception {
        List<String> codes = Arrays.asList("q 000001", "d 100000", "a 100100", "b 100101", "c 110001", "[newline] 111111", "p 111110");
        String encoded = "111110000001100100111111100101110001111110100000";
        assertThat(Result.decode(codes, encoded)).isNotEmpty().isEqualTo("pqa\nbcpd");
    }

    @Test
    void decodeWithSpace() throws Exception {
        List<String> codes = Arrays.asList("e 011", "l 100", "i 000", "n 001", "d 010", "h 1010", "o 1011", "m 1100", "y 1101", "f 1110", "r 1111");
        String encoded = "10100111001001011110011011110111 1000011001010";
        assertThat(Result.decode(codes, encoded)).isNotEmpty().isEqualTo("hellomy friend");
    }
}