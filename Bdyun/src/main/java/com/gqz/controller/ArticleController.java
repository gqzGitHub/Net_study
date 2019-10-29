package com.gqz.controller;

import com.gqz.entity.Article;
import com.gqz.entity.es.ArticleInfo;
import com.gqz.service.ArticleService;
import com.gqz.util.PageUtil;
import com.gqz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * ArticleController
 * @site www.ganquanzhong.top
 * @create 2018-12-14 下午 4:37
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/list/{page}")
    public ModelAndView list(@PathVariable(value="page",required=false) Integer page)throws Exception{
        ModelAndView mav=new ModelAndView();
        List<Article> articleList = articleService.list(page, 20, Sort.Direction.DESC, "includeDate");
        mav.addObject("articleList",articleList);
        Long total=articleService.getCount();
        mav.addObject("pageCode", PageUtil.genPagination("/article/list", total, page, 20));
        mav.setViewName("newest");
        mav.addObject("modeName","最新资源收录列表");
        mav.addObject("title","最新资源收录列表");
        return mav;
    }

    /**
     * 根据id查询帖子详细信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/{id}")
    public ModelAndView view(@PathVariable("id")Integer id)throws Exception{
        ModelAndView mav=new ModelAndView();
        Article article=articleService.get(id);
        mav.addObject("article",article);
        mav.setViewName("view");
        mav.addObject("modeName",article.getName());
        mav.addObject("title",article.getName());
        return mav;
    }


    /**
     * 加载相关资源
     * @param q
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/loadRelatedResource")
    public List<ArticleInfo> loadRelatedResource(String q)throws Exception{
        if(StringUtil.isEmpty(q)){
            return null;
        }
        List<ArticleInfo> articleInfoList = articleService.searchNoHighlight(1, 20, q);
        return  articleInfoList;
    }

}
