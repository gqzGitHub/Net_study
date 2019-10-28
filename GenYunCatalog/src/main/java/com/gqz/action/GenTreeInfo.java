package com.gqz.action;

import com.gqz.model.Article;
import com.gqz.util.DateUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * 根据百度云分享地址和密码生成所有信息
 * @author ganquanzhong
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2019-01-12 下午 2:55
 */
public class GenTreeInfo {


    /**
     * 生成所有信息
     * @param article
     * @return
     */
    public void gen(Article article){
        /**
         * 使用google驱动  默认安装在用户的路劲下面  使用自己对应的应用位置
         */
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        WebDriver driver=new ChromeDriver();
        driver.get(article.getShare_url());

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.tagName("body")).isDisplayed();
                return loadcomplete;
            }
        });

        boolean hasPasword=false; // 是否有密码
        String title=driver.getTitle();
        if("百度网盘 请输入提取码".equals(title)){ // 判断是否有密码
            // 打开有密码的百度云
            hasPasword=true;
        }else if("百度网盘-链接不存在".equals(title)){ // 判断是否失效
            article.setState(2);
        }
        if(hasPasword){
            WebElement btn = driver.findElement(By.cssSelector(".g-button-right"));
            WebElement element = driver.findElement(By.cssSelector(".QKKaIE.LxgeIt"));
            element.sendKeys(article.getPassword());
            btn.click();
        }


        try {
            genPageDate(driver,article); // 生成页面的数据
            STest3Level.gen3Level(driver,article);
        } catch (Exception e) {
            e.printStackTrace();
        }



        driver.close();

    }

    /**
     * 生成页面的数据
     * @param article
     * @return
     */
    public void genPageDate(WebDriver driver,Article article)throws Exception{
        // 判断 类型 EgMMec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement fileNameEle = driver.findElement(By.cssSelector(".file-name"));
        article.setName(fileNameEle.getText());
        WebElement shareDateEle = driver.findElement(By.cssSelector(".share-file-info span"));
        article.setShare_date(shareDateEle.getText());
        WebElement shareUserEle = driver.findElement(By.cssSelector(".share-person-data-top a.share-person-username.global-ellipsis"));
        article.setShare_user(shareUserEle.getAttribute("textContent"));
        article.setContent(article.getName()); // 内容暂时设置成标题
        article.setState(1);
        article.setInclude_date(DateUtil.getCurrentDateStr());
    }




}
