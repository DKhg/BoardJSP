package com.BoardJSP.service;

import com.BoardJSP.dto.BoardDTO;
import com.BoardJSP.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //객체 생성
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;


    //경로 : BoardController -> BoardService(I) -> BoardServiceImpl(C) ->
    //		 BoardMapper(I) -> boardMapper.xml


    @Override
    public int maxId() throws Exception {
        return boardMapper.maxId();
    }

    @Override
    public void insertData(BoardDTO dto) throws Exception {
        boardMapper.insertData(dto);
    }

    @Override
    public int getDataCount(String searchKey, String searchValue) throws Exception {
        return boardMapper.getDataCount(searchKey, searchValue);
    }

    @Override
    public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue) throws Exception {
        return boardMapper.getLists(start, end, searchKey, searchValue);
    }

    @Override
    public BoardDTO getReadData(int id) throws Exception {
        return boardMapper.getReadData(id);
    }

    @Override
    public void updateHitCount(int id) throws Exception {
        boardMapper.updateHitCount(id);
    }

    @Override
    public void updateData(BoardDTO dto) throws Exception {
        boardMapper.updateData(dto);
    }

    @Override
    public void deleteData(int id) throws Exception {
        boardMapper.deleteData(id);
    }

}