package com.BoardJSP.mapper;

import com.BoardJSP.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    public int maxId() throws Exception;

    public void insertData(BoardDTO dto) throws Exception;

    public int getDataCount(String searchKey, String searchValue) throws Exception;

    public List<BoardDTO> getLists(int start,int end,
                                   String searchKey, String searchValue) throws Exception;

    public BoardDTO getReadData(int id) throws Exception;

    public void updateHitCount(int id) throws Exception;

    public void updateData(BoardDTO dto) throws Exception;

    public void deleteData(int id) throws Exception;

}
