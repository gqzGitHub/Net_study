package com.gqz.controller.admin;

import com.gqz.entity.Article;
import com.gqz.service.ArticleService;
import com.gqz.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理-帖子控制器
 * @author ganquanzhong
 * @site www.ganquanzhong.top
 * @create 2019-01-09 下午 10:14
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询帖子信息
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public Map<String,Object> list(Article s_article,@RequestParam(value="page",required=false)Integer page, @RequestParam(value="limit",required=false)Integer limit)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        List<Article> articleList = articleService.adminList(s_article,page, limit, Sort.Direction.DESC, "includeDate");
        Long count = articleService.getAdminCount(s_article);
        resultMap.put("code", 0);
        resultMap.put("count", count);
        resultMap.put("data", articleList);
        return resultMap;
    }

    /**
     * 删除帖子信息 包括es索引
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String,Object> delete(Integer id, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            articleService.delete(id);
            articleService.deleteIndex(String.valueOf(id)); // 删除索引
        }catch(Exception e){
            e.printStackTrace();
        }
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 多选删除
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteSelected")
    public Map<String,Object> deleteSelected(String ids)throws Exception{
        String []idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            String articleId=idsStr[i];
            articleService.delete(Integer.parseInt(articleId));
            articleService.deleteIndex(String.valueOf(articleId)); // 删除索引
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 添加资源信息
     * @param article
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public Map<String,Object> add(Article article)throws  Exception{
        article.setIncludeDate(DateUtil.getCurrentDateStr());
        articleService.save(article);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        return resultMap;
    }



}
