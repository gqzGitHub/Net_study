package com.gqz.entity.es;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 资源ES索引实体
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-31 下午 2:15
 */
@Document(indexName = "test2",type = "my")
public class ArticleInfo implements Serializable {

    private Long id; // 编号

    private String name; // 资源名称

    private String content; // 资源目录

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
