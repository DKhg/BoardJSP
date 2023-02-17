package com.BoardJSP.service;

import com.BoardJSP.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    public int maxId() throws Exception;

    public void insertData(BoardDTO dto) throws Exception;

    public int getDataCount(String searchKey, String searchValue) throws Exception;

    public List<BoardDTO> getLists(int start, int end,
                                   String searchKey, String searchValue) throws Exception;

    public BoardDTO getReadData(int id) throws Exception;

    public void updateHitCount(int id) throws Exception;

    public void updateData(BoardDTO dto) throws Exception;

    public void deleteData(int id) throws Exception;

}