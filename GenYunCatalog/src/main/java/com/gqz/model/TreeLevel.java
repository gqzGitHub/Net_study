package com.gqz.model;

import java.util.List;

/**
 * 层次结构实体类
 * @author java1234_小锋
 * @site www.gqz.com
 * @company 南通小锋网络科技有限公司
 * @create 2018-11-06 下午 3:55
 */
public class TreeLevel {

    private Integer currentIndex; // 当前遍历的层次索引
    private List<String> levelCatalog; // 遍历层次的所有目录节点名称

    public TreeLevel() {
    }

    public TreeLevel(Integer currentIndex, List<String> levelCatalog) {
        this.currentIndex = currentIndex;
        this.levelCatalog = levelCatalog;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<String> getLevelCatalog() {
        return levelCatalog;
    }

    public void setLevelCatalog(List<String> levelCatalog) {
        this.levelCatalog = levelCatalog;
    }
}
