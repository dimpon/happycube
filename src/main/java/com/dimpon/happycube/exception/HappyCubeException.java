package com.dimpon.happycube.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class HappyCubeException extends RuntimeException {

    @AllArgsConstructor
    public enum ExceptionsType{
        WRONG_EDGE_ARRAY("Edge array must have 16 symbols"),
        LOADER_NOT_FOUND("Loader is required"),
        WRITER_NOT_FOUND("Writer is required"),
        WRONG_INIT_DATA("Init piece(s) has a gap in the middle or hanging corner"),
        PIECE_POSITION_NOT_FOUND("The piece's position is not found by key");

        private String message;
    }

    private ExceptionsType type;

    public HappyCubeException(ExceptionsType type) {
        super(type.message);
        this.type = type;
    }
}
