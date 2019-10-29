package com.gqz.service.impl;

import com.gqz.entity.Article;
import com.gqz.entity.es.ArticleInfo;
import com.gqz.repository.ArticleRepository;
import com.gqz.service.ArticleService;
import com.gqz.util.StringUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 帖子Service实现类
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-14 下午 4:00
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<Article> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> pageArticle = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                /*
                    抓取资源信息成功
                 */
                predicate.getExpressions().add(cb.equal(root.get("state"), 1));
                // es索引添加成功
                predicate.getExpressions().add(cb.equal(root.get("isIndex"), true));
                return predicate;
            }
        }, pageRequest);
        return pageArticle.getContent();
    }

    @Override
    public List<Article> adminList(Article s_article, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Article> pageArticle = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(s_article!=null){
                    if(s_article.getId()!=null){
                        predicate.getExpressions().add(cb.equal(root.get("id"),s_article.getId()));
                    }
                    if(StringUtil.isNotEmpty(s_article.getName())){
                        predicate.getExpressions().add(cb.like(root.get("name"), "%"+s_article.getName().trim()+"%"));
                    }
                }
                return predicate;
            }
        }, pageRequest);
        return pageArticle.getContent();
    }

    @Override
    public Long getAdminCount(Article s_article) {
        return articleRepository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(s_article!=null){
                    if(s_article.getId()!=null){
                        predicate.getExpressions().add(cb.equal(root.get("id"),s_article.getId()));
                    }
                    if(StringUtil.isNotEmpty(s_article.getName())){
                        predicate.getExpressions().add(cb.like(root.get("name"), "%"+s_article.getName().trim()+"%"));
                    }
                }
                return predicate;
            }
        });
    }

    @Override
    public Long getCount() {
        return articleRepository.count(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("state"), 1)); // 抓取资源信息成功
                predicate.getExpressions().add(cb.equal(root.get("isIndex"), true)); // es索引添加成功
                return predicate;
            }
        });
    }

    @Override
    public void deleteIndex(String id){
        QueryBuilder queryBuilder=QueryBuilders.termQuery("id",id);
        DeleteQuery deleteQuery=new DeleteQuery();
        deleteQuery.setIndex("test2");
        deleteQuery.setType("my");
        deleteQuery.setQuery(queryBuilder);
        elasticsearchTemplate.delete(deleteQuery);
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public Article get(Integer id) {
        return articleRepository.getOne(id);
    }


    @Override
    public List<ArticleInfo> search(Integer page, Integer pageSize, String searchContent) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name",searchContent))
                .should(QueryBuilders.matchQuery("content",searchContent));

        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                 .withIndices("test2")
                .withHighlightFields(new HighlightBuilder.Field("content"),new HighlightBuilder.Field("name"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<font style='color:red'>").postTags("</font>")).build();

        AggregatedPage<ArticleInfo> articleInfos = elasticsearchTemplate.queryForPage(nativeSearchQuery,
                ArticleInfo.class, new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        ArrayList<ArticleInfo> articleInfos = new ArrayList<ArticleInfo>();
                        SearchHits hits = response.getHits();
                        for (SearchHit searchHit : hits) {
                            if (hits.getHits().length <= 0) {
                                return null;
                            }
                            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                            String name= (String) sourceAsMap.get("name");
                            String content= (String) sourceAsMap.get("content");
                            String id= sourceAsMap.get("id")+"";
                            System.out.println(name);
                            System.out.println(content);
                            ArticleInfo articleInfo = new ArticleInfo();
                            HighlightField contentHighlightField = searchHit.getHighlightFields().get("content");
                            if(contentHighlightField==null){
                                articleInfo.setContent(content);
                            }else{
                                String highLightMessage = searchHit.getHighlightFields().get("content").fragments()[0].toString();
                                articleInfo.setContent(highLightMessage.replaceAll("br","").replaceAll("&nbsp;","").replaceAll("_",""));
                            }
                            HighlightField nameHighlightField =searchHit.getHighlightFields().get("name");
                            if(nameHighlightField==null){
                                articleInfo.setName(name);
                            }else{
                                articleInfo.setName(searchHit.getHighlightFields().get("name").fragments()[0].toString());
                            }
                            articleInfo.setId(Long.valueOf(id));

                            articleInfos.add(articleInfo);
                        }
                        if (articleInfos.size() > 0) {
                            return new AggregatedPageImpl<T>((List<T>) articleInfos);
                        }
                        return null;

                    }
                });
        return articleInfos==null?null:articleInfos.getContent();
    }

    @Override
    public List<ArticleInfo> searchNoHighlight(Integer page, Integer pageSize, String searchContent) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name",searchContent))
                .should(QueryBuilders.matchQuery("content",searchContent));

        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .withIndices("test2").build();

        AggregatedPage<ArticleInfo> articleInfos = elasticsearchTemplate.queryForPage(nativeSearchQuery, ArticleInfo.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                ArrayList<ArticleInfo> articleInfos = new ArrayList<ArticleInfo>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    String name= (String) sourceAsMap.get("name");
                    String id= sourceAsMap.get("id")+"";
                    ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.setName(name);
                    articleInfo.setId(Long.valueOf(id));

                    articleInfos.add(articleInfo);
                }
                if (articleInfos.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) articleInfos);
                }
                return null;

            }
        });
        return articleInfos==null?null:articleInfos.getContent();
    }

    @Override
    public Long searchCount(String searchContent){
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name",searchContent))
                .should(QueryBuilders.matchQuery("content",searchContent));

        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withIndices("test2")
                .build();

        return elasticsearchTemplate.count(nativeSearchQuery);
    }

    @Override
    public void delete(Integer id) {
        Article article = articleRepository.getOne(id);
        articleRepository.delete(article);
    }

}
