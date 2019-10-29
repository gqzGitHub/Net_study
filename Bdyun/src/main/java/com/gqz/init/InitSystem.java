package com.gqz.init;

import com.gqz.entity.HotSearch;
import com.gqz.service.HotSearchService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 初始化加载数据
 *      说明：通过实现ServletContextListener, ApplicationContextAware接口
 *          setApplicationContext方法自动注入applicationContext
 *
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-02 下午 2:49
 */
@Component
public class InitSystem implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        InitSystem.applicationContext=applicationContext;
    }

    //初始化hotSearch数据列表
    public void loadData(ServletContext application){
        HotSearchService hotSearchService= (HotSearchService) applicationContext.getBean("hotSearchService");
        List<HotSearch> hotSearchList = hotSearchService.listAll(Sort.Direction.ASC,"sort");
        application.setAttribute("hotSearchList",hotSearchList);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loadData(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
