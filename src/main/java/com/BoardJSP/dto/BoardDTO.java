package com.BoardJSP.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

    private int id;
    private String name;
    private String password;
    private String email;
    private String title;
    private String content;
    private String created;
    private int hitCount;
}
