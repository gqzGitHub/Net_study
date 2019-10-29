package com.gqz.repository;

import com.gqz.entity.HotSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 热门搜索Repository接口
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-02 下午 2:33
 */
public interface HotSearchRepository extends JpaRepository<HotSearch,Integer>, JpaSpecificationExecutor<HotSearch> {

}
