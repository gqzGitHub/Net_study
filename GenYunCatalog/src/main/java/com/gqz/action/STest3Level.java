package com.gqz.action;

import com.gqz.model.Article;
import com.gqz.model.TreeLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-11-05 下午 1:29
 */
public class STest3Level {

    private static int currentLevel=1; // 当前的层次

    private static StringBuffer treeInfo=null; // 树形结构内容信息

    private static Map<String, TreeLevel> allTreeInfo=new HashMap<String, TreeLevel>();

    private static boolean forward=true;

    private static boolean hasPasword=false;

    public static Article gen3Level(WebDriver driver, Article article)throws Exception{
       // deal(driver);
        treeInfo=new StringBuffer();
        try{
            WebElement element = driver.findElement(By.cssSelector(".EgMMec"));
            System.out.println("是目录");
            dealCatalog(driver);
        }catch(Exception e){
            System.out.println("是文件");
            dealFile(driver);
        }
        article.setContent(treeInfo.toString());
        return article;
    }


    /**
     * 处理文件
     * @param driver
     */
    public static void dealFile(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.cssSelector(".textContent")).isDisplayed();
                return loadcomplete;
            }
        });
        WebElement fileNameEle = driver.findElement(By.cssSelector(".file-name"));
        treeInfo.append(fileNameEle.getText());
    }


    /**
     * 处理目录
     * @param driver
     */
    public static void dealCatalog(WebDriver driver){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.cssSelector(".g-clearfix.AuPKyz")).isDisplayed();
                return loadcomplete;
            }
        });
        List<WebElement> allAlements = driver.findElements(By.cssSelector(".g-clearfix.AuPKyz"));
        List<WebElement> elements = driver.findElements(By.cssSelector(".filename"));

        int catalogNumber=0;
        List<WebElement> fileEleList=new LinkedList<WebElement>();
        for(int i=0;i<allAlements.size();i++){
            WebElement webElement = allAlements.get(i);
            WebElement element=null;
            try{
                element = webElement.findElement(By.cssSelector(".JS-fileicon.dir-small"));
            }catch(Exception e){
                // e.printStackTrace();
            }finally {
                if(element==null||currentLevel==3){
                    WebElement webElement1 = elements.get(i);
                    if(forward||currentLevel==3){
                        printLine(currentLevel);
                        treeInfo.append(webElement1.getText()+"</br>");
                        System.out.println(webElement1.getText());
                    }
                    // elements.remove(i);
                    fileEleList.add(webElement1);
                }else if(currentLevel==1){

                    catalogNumber++;
                }else{
                    catalogNumber++;
                }
                continue;
            }
        }

        for(WebElement ele:fileEleList){
            elements.remove(ele);
        }

        if(catalogNumber==0 && currentLevel==1){
            return;
        }
        if(catalogNumber==0){
            allTreeInfo.remove(String.valueOf(currentLevel));
            --currentLevel; // 层次减1
            forward=false;
            driver.navigate().back();
            dealCatalog(driver); // 递归调用
        }else{
            if(allTreeInfo.get(String.valueOf(currentLevel))==null){
                List<String> allInfo=new LinkedList<String>();
                for(WebElement ele:elements){
                    String text=ele.getText();
                    allInfo.add(text);
                }
                allTreeInfo.put(String.valueOf(currentLevel),new TreeLevel(0,allInfo));
            }
        }
        TreeLevel treeLevel = allTreeInfo.get(String.valueOf(currentLevel));
        if(treeLevel==null){
            return;
        }
        Integer currentIndex = treeLevel.getCurrentIndex();
        if(currentIndex<elements.size()){
            WebElement webElement = elements.get(currentIndex);
            printLine(currentLevel);
            treeInfo.append(webElement.getText()+"</br>");
            System.out.println(webElement.getText());
            String winHandleBefore = driver.getWindowHandle();
            webElement.click(); // 点击元素
            for(String winHandle : driver.getWindowHandles()) {
                if (winHandle.equals(winHandleBefore)) {
                    continue;
                }
                driver.switchTo().window(winHandle);
                break;
            }
            treeLevel.setCurrentIndex(currentIndex+1);
            allTreeInfo.put(String.valueOf(currentLevel),treeLevel);
            ++currentLevel; // 层次加1
            forward=true;
            dealCatalog(driver); // 递归调用
        }else{
            allTreeInfo.remove(String.valueOf(currentLevel));
            if(currentLevel==1){
                return;
            }else{
                --currentLevel; // 层次减1
                forward=false;
                driver.navigate().back();
                dealCatalog(driver); // 递归调用
            }
        }
    }

    private static void printLine(int n){
        for(int i=2;i<=n;i++){
            if(i<=n-1){
                System.out.print("     ");
                treeInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            }else{
                System.out.print("|____");
                treeInfo.append("|____");
            }
        }
    }
}
