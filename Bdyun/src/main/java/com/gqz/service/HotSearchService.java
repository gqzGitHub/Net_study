package com.gqz.service;

import com.gqz.entity.HotSearch;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 热门搜索Service接口
 * @site www.gqz.com
 * @create 2018-12-02 下午 2:37
 */
public interface HotSearchService {

     /**
      *  查询所有的热门搜索信息
      * @param
      * @method
      * @description:
      * @return
      * @author ganquanzhong
      * @date   2019/10/29 17:31
      */
    public List<HotSearch> listAll(Sort.Direction direction, String...properties);


    /**
     * 添加或者修改热门搜索
     * @param hotSearch
     */
    public void save(HotSearch hotSearch);



    /**
     * 分页查询热门搜索信息
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<HotSearch> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties);



    /**
     * 获取总记录数
     * @return
     */
    public Long getCount();



    /**
     * 根据id删除热门搜索
     * @param id
     */
    public void delete(Integer id);


    /**
     * 根据Id获取实体
     * @param id
     * @return
     */
    public HotSearch getById(Integer id);
}
