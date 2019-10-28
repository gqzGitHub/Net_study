package com.gqz.action;

import com.gqz.model.Article;
import com.gqz.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ganquanzhong
 * @company Java知识分享网
 * @create 2019-01-12 下午 5:02
 */
public class Main {

    private static DbUtil dbUtil=new DbUtil();

    public static void main(String[] args) {

        //获取分享资源信息
        String sql="SELECT id,share_url,PASSWORD FROM t_article WHERE state=0 LIMIT 0,100";
        Connection con=null;
        PreparedStatement pstmt =null;
        try{
            con = dbUtil.getCon();
            pstmt = con.prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                String shareUrl=rs.getString("share_url");
                String password=rs.getString("password");
                Integer id=rs.getInt("id");
                Article article=new Article();
                article.setShare_url(shareUrl);
                article.setPassword(password);
                article.setId(id);
                GenTreeInfo genTreeInfo=new GenTreeInfo();
                System.out.println("【id="+article.getId()+"】开始生成信息");
                genTreeInfo.gen(article);
                System.out.println("【id="+article.getId()+"】开始生成完成");
                if(article.getState()==null){
                    article.setState(2);
                }
                updateData(article);
                if(article.getState()==1){
                    System.out.println("【"+article.getName()+"】更新成功"+" id="+article.getId());
                }else{
                    System.out.println("【"+article.getName()+"】更新失败"+" id="+article.getId());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 更新数据
     * @param article
     */
    public static void updateData(Article article){
        // 插入数据库
        Connection con=null;
        PreparedStatement pstmt =null;
        try{
            con = dbUtil.getCon();
            String sql="update t_article set name=?,content=?,include_date=now(),share_user=?,share_date=?,state=? where id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,article.getName());
            pstmt.setString(2,article.getContent());
            pstmt.setString(3,article.getShare_user());
            pstmt.setString(4,article.getShare_date());
            pstmt.setInt(5,article.getState());
            pstmt.setInt(6,article.getId());
            pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
