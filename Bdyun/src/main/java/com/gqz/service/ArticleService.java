package com.gqz.service;

import com.gqz.entity.Article;
import com.gqz.entity.es.ArticleInfo;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 帖子Service接口
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-14 下午 3:47
 */
public interface ArticleService {

    /**
     * 分页查询帖子信息
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Article> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 根据条件分页查询帖子信息
     * @param s_article
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Article> adminList(Article s_article,Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 获取总记录数
     * @param s_article
     * @return
     */
    public Long getAdminCount(Article s_article);

    /**
     * 获取总记录数
     * @return
     */
    public Long getCount();

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public Article get(Integer id);

    /**
     * 根据条件分词查询
     * @param page
     * @param pageSize
     * @param searchContent
     * @return
     */
    public List<ArticleInfo> search(Integer page, Integer pageSize, String searchContent);

    /**
     * 根据条件分词查询 （无高亮）
     * @param page
     * @param pageSize
     * @param searchContent
     * @return
     */
    public List<ArticleInfo> searchNoHighlight(Integer page, Integer pageSize, String searchContent);

    /**
     * 根据条件分词查询总记录数
     * @param searchContent
     * @return
     */
    public Long searchCount(String searchContent);

    /**
     * 根据id删除实体
     * @param id
     */
    public void delete(Integer id);

    /**
     * 删除指定id的索引
     * @param id
     */
    public void deleteIndex(String id);

    /**
     * 添加资源
     * @param article
     */
    public void save(Article article);

}
