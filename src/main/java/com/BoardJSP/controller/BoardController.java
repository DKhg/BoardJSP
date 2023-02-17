package com.BoardJSP.controller;

import com.BoardJSP.dto.BoardDTO;
import com.BoardJSP.service.BoardService;
import com.BoardJSP.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;


@Controller // 스프링부트에 jsp를 파싱해주기위해서 써야한다
public class BoardController {

    @Resource
    private BoardService boardService; // Resource를 호출하면 BoardServiceImpl이 딸려온다

    @Autowired
    MyUtil myUtil;

    @RequestMapping(value = "/")
    public ModelAndView index() throws Exception{

        ModelAndView mav = new ModelAndView();

        mav.setViewName("index");

        return mav;
    }


    @RequestMapping(value = "/created.action", method = RequestMethod.GET)
    public ModelAndView created() throws Exception{

        ModelAndView mav = new ModelAndView();

        mav.setViewName("bbs/created");

        return mav;
    }

    @RequestMapping(value = "/created.action", method = RequestMethod.POST)
    public ModelAndView created_ok(BoardDTO dto, HttpServletRequest request) throws Exception{

        ModelAndView mav = new ModelAndView();

        int maxId = boardService.maxId();

        dto.setId(maxId + 1);


        boardService.insertData(dto);

        mav.setViewName("redirect:/list.action");

        return mav;

    }

    @RequestMapping(value = "/list.action",
            method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView list(BoardDTO dto, HttpServletRequest request) throws Exception{

        String pageNum = request.getParameter("pageNum");

        int currentPage = 1;

        if(pageNum!=null)
            currentPage = Integer.parseInt(pageNum);

        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");

        if(searchValue==null) {
            searchKey = "content";
            searchValue = "";
        }else {
            if(request.getMethod().equalsIgnoreCase("GET")) {
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }
        }

        int dataCount = boardService.getDataCount(searchKey, searchValue);

        int numPerPage = 5;
        int totalPage = myUtil.getPageCount(numPerPage, dataCount);

        if(currentPage>totalPage)
            currentPage = totalPage;

        int start = (currentPage-1)*numPerPage+1; // 1 6 11 16
        int end = currentPage*numPerPage;

        List<BoardDTO> lists = boardService.getLists(start, end, searchKey, searchValue);

        String param = "";

        if(searchValue!=null&&!searchValue.equals("")) {
            param = "searchKey=" + searchKey;
            param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
        }

        String listUrl = "/list.action";

        if(!param.equals("")) {
            listUrl += "?" + param;
        }

        String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);

        String articleUrl = "/article.action?pageNum=" + currentPage;

        if(!param.equals("")) {
            articleUrl += "&" + param;
        }

		/*
		request.setAttribute("lists", lists);
		request.setAttribute("articleUrl", articleUrl);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);

		return "bbs/list";
		*/
        //ModelAndView로 전송
        ModelAndView mav = new ModelAndView();

        mav.addObject("lists", lists);
        mav.addObject("articleUrl", articleUrl);
        mav.addObject("pageIndexList", pageIndexList);
        mav.addObject("dataCount", dataCount);

        mav.setViewName("bbs/list");

        return mav;
    }

    @RequestMapping(value = "/article.action",
            method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView article(HttpServletRequest request) throws Exception{

        int id = Integer.parseInt(request.getParameter("id"));
        String pageNum = request.getParameter("pageNum");

        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");

        if(searchValue!=null) {
            searchValue = URLDecoder.decode(searchValue, "UTF-8");
        }

        boardService.updateHitCount(id);

        BoardDTO dto = boardService.getReadData(id);

        if(dto==null) {

            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:/list.action?pageNum=" + pageNum);
            return mav;

        }

        int lineSu = dto.getContent().split("\n").length;


        String param = "pageNum=" + pageNum;
        if(searchValue!=null&&!searchValue.equals("")) {

            param += "&searchKey=" + searchKey;
            param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
        }

        //모델엔뷰는 String을 받아내지 못한다.	ModelAndView mav = new ModelAndView();

        ModelAndView mav = new ModelAndView();

        mav.addObject("dto", dto);
        mav.addObject("params", param);
        mav.addObject("lineSu", lineSu);
        mav.addObject("pageNum", pageNum);

        mav.setViewName("bbs/article");

        return mav;

    }

    @RequestMapping(value = "/updated.action",
            method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView updated(HttpServletRequest request) throws Exception{

        int id = Integer.parseInt(request.getParameter("id"));
        String pageNum = request.getParameter("pageNum");


        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");

        if(searchValue!=null) {
            searchValue = URLDecoder.decode(searchValue, "UTF-8");
        }

        BoardDTO dto = boardService.getReadData(id);

        if(dto==null) {

            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:/list.action?pageNum=" + pageNum);
            return mav;
            //return "redirect:/list.action"; 반환값이 String 일때 이렇게 써주고 모델엔뷰니깐 위처럼
        }

        String param = "pageNum=" + pageNum;

        if(searchValue!=null&&!searchValue.equals("")) {
            param += "&searchKey=" +searchKey;
            param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
        }

		/*
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("params", param);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);

		return "bbs/updated";
		*/
        //모델앤뷰 전송방식
        ModelAndView mav = new ModelAndView();

        mav.addObject("dto", dto);
        mav.addObject("pageNum", pageNum);
        mav.addObject("params", param);
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);

        mav.setViewName("bbs/updated");

        return mav;

    }

    @RequestMapping(value = "/updated_ok.action",
            method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView updated_ok(BoardDTO dto, HttpServletRequest request) throws Exception{

        String pageNum = request.getParameter("pageNum");

        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");

        dto.setContent(dto.getContent().replaceAll( "<br/>", "\r\n"));

        boardService.updateData(dto);

        String param = "?pageNum=" + pageNum;

        if(searchValue!=null&&!searchValue.equals("")) {
            param += "&searchKey=" + searchKey;
            param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
        }

        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/list.action" + param);

        return mav;
    }


    @RequestMapping(value = "/deleted_ok.action",
            method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleted_ok(HttpServletRequest request) throws Exception{

        int id = Integer.parseInt(request.getParameter("id"));
        String pageNum = request.getParameter("pageNum");

        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");

        boardService.deleteData(id);

        String param = "?pageNum=" + pageNum;

        if(searchValue!=null&&!searchValue.equals("")) {
            param += "&searchKey=" + searchKey;
            param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
        }
        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/list.action" + param);

        return mav;

    }

}

