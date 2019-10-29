package com.gqz;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

 /**
  * web应用  默认有一个ServletInitializer类 实现SpringBootServletInitializer
  *
  * @author ganquanzhong
  * @date   2019/10/29 15:55
  */

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BdyunApplication.class);
    }

}
