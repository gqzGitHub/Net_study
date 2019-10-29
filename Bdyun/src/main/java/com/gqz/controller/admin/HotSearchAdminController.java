package com.gqz.controller.admin;

import com.gqz.entity.HotSearch;
import com.gqz.init.InitSystem;
import com.gqz.service.HotSearchService;
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
 * 后台管理-热门搜索标签控制器
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2019-01-08 下午 7:05
 */
@RestController
@RequestMapping("/admin/hotSearch")
public class HotSearchAdminController {

    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
    private InitSystem initSystem;

    /**
     * 添加或者修改热门搜索标签
     * @param hotSearch
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public Map<String,Object> save(HotSearch hotSearch, HttpServletRequest request)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        hotSearchService.save(hotSearch);
        initSystem.loadData(request.getServletContext());
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 分页查询热门搜索信息
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public Map<String,Object> list(@RequestParam(value="page",required=false)Integer page, @RequestParam(value="limit",required=false)Integer limit)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        List<HotSearch> hostSearchList = hotSearchService.list(page, limit, Sort.Direction.ASC, "sort");
        Long count = hotSearchService.getCount();
        resultMap.put("code", 0);
        resultMap.put("count", count);
        resultMap.put("data", hostSearchList);
        return resultMap;
    }

    /**
     * 删除热门搜索
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String,Object> delete(Integer id, HttpServletRequest request)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        hotSearchService.delete(id);
        initSystem.loadData(request.getServletContext());
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据id查询热门搜索
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public Map<String,Object> findById(Integer id)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        HotSearch hotSearch = hotSearchService.getById(id);
        resultMap.put("hotSearch", hotSearch);
        resultMap.put("success", true);
        return resultMap;
    }

}
