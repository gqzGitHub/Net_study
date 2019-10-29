package com.gqz.service.impl;

import com.gqz.entity.HotSearch;
import com.gqz.repository.HotSearchRepository;
import com.gqz.service.HotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 热门搜索Service实现类
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-02 下午 2:43
 */
@Service("hotSearchService")
public class HotSearchServiceImpl implements HotSearchService {

    @Autowired
    private HotSearchRepository hotSearchRepository;

    @Override
    public List<HotSearch> listAll(Direction direction, String...properties) {
        return hotSearchRepository.findAll(new Sort(direction,properties));
    }

    @Override
    public void save(HotSearch hotSearch) {
        hotSearchRepository.save(hotSearch);
    }

    @Override
    public List<HotSearch> list(Integer page, Integer pageSize, Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<HotSearch> pageHotSearch = hotSearchRepository.findAll(pageRequest);
        return pageHotSearch.getContent();
    }

    @Override
    public Long getCount() {
        return hotSearchRepository.count();
    }

    @Override
    public void delete(Integer id) {
        HotSearch hotSearch = hotSearchRepository.getOne(id);
        hotSearchRepository.delete(hotSearch);
    }

    @Override
    public HotSearch getById(Integer id) {
        return hotSearchRepository.findById(id).get();
    }
}
