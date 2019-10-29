package com.gqz.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 资源实体
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-02 下午 5:10
 */
@Entity
@Table(name="t_article")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(length=100)
    private String name; // 资源名称

    @Column(length=100)
    private String shareUser; // 分享人

    @Column(length=100)
    private String shareDate; // 分享日期

    @Column(length=200)
    private String shareUrl; // 分享地址

    @Column(length=20)
    private String password; // 分享地址密码

    @Lob
    @Column(columnDefinition="longtext")
    private String content; // 资源目录

    @Column(length=100)
    private String includeDate; //收录日期

    private boolean isIndex=false; // 是否建立es索引

    private Integer state=0; // 资源状态 0 默认初始状态  1 解析完成  2  解析失败



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getShareDate() {
        return shareDate;
    }

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIncludeDate() {
        return includeDate;
    }

    public void setIncludeDate(String includeDate) {
        this.includeDate = includeDate;
    }



    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean index) {
        isIndex = index;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }



    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }
}
