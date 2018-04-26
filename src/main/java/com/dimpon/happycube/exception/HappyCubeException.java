package com.dimpon.happycube.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class HappyCubeException extends RuntimeException {

    @AllArgsConstructor
    public enum ExceptionsType{
        WRONG_EDGE_ARRAY("Edge array must have 16 symbols"),
        LOADER_NOT_FOUND("Loader is required"),
        WRITER_NOT_FOUND("Writer is required"),
        WRONG_INIT_DATA("Init piece(s) has a gap in the middle or hanging corner"),
        PIECE_POSITION_NOT_FOUND("The piece's position is not found by key"),
        RULES_CANNOT_EXIST_TOGETHER("The rules cannot exist together"),
        WRONG_INITIAL_RULES("The rule %1$s cannot contain another rule %2$s"),
        ;

        private String message;
    }

    private ExceptionsType type;

    public HappyCubeException(ExceptionsType type) {
        super(type.message);
        this.type = type;
    }

    public HappyCubeException(ExceptionsType type, Object ... args) {
        super(String.format(type.message,args));
        this.type = type;
    }
}
