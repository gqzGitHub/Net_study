package com.gqz.repository;

import com.gqz.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 帖子Repository接口
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-14 下午 3:41
 */
public interface ArticleRepository extends JpaRepository<Article,Integer>, JpaSpecificationExecutor<Article> {
}
