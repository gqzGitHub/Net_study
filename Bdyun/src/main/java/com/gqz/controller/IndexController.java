package com.gqz.controller;

import com.gqz.entity.es.ArticleInfo;
import com.gqz.service.ArticleService;
import com.gqz.util.PageUtil;
import com.gqz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 根目录Controller
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-11-30 上午 8:44
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    /**
     * 根目录请求
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("index");
        mav.addObject("title","首页");
        return mav;
    }

    /**
     * 登录请求
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    /**
     * 进入后台管理请求
     * @return
     */
    @RequestMapping("/admin")
    public String toAdmin(){
        return "/admin/main";
    }

    /**
     * 分词查询
     * @param q
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value="q",required=false) String q, @RequestParam(value="page",required=false) String page)throws Exception{
        ModelAndView mav=new ModelAndView();
        if(StringUtil.isEmpty(q)){
            mav.setViewName("index");
            mav.addObject("title","首页");
            return mav;
        }
        int pageSize=10;
        if(StringUtil.isEmpty(page)){
            page="1";
        }
        mav.addObject("q", q);
        List<ArticleInfo> articleInfoList = articleService.search(Integer.parseInt(page), pageSize, q);

        Long total=articleService.searchCount(q);
        mav.addObject("articleList", articleInfoList);
        mav.addObject("modeName","' "+q+" ' - 搜索结果");
        mav.addObject("resultTotal",total);
        mav.addObject("pageCode", PageUtil.genSearchPagination("/search", total, Integer.parseInt(page), pageSize,q));
        mav.addObject("title",q);
        mav.setViewName("result");
        return mav;
    }
}
