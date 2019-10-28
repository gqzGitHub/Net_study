package com.gqz.main;

import com.gqz.util.DbUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-29 下午 3:21
 */
public class EsIndex {

//    private static String host="192.168.1.108"; // 服务器地址
    private static String host="49.235.210.101"; // 服务器地址
    private static int port=9300; // 端口

    public static void main(String[] args) {

        while(true){
            DbUtil dbUtil=new DbUtil();
            String sql="SELECT id,name,content FROM t_article WHERE state=1 AND is_index=FALSE limit 0,100";
            Connection con =null;
            // 创建client
            TransportClient client=null;
            try {
                con = dbUtil.getCon();

                // 创建client
                client = new PreBuiltTransportClient(Settings.EMPTY)
                        .addTransportAddresses(new TransportAddress(InetAddress.getByName(host), port));
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs=pstmt.executeQuery();
                while (rs.next()){
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String content = rs.getString("content");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id",id);
                    map.put("name",name);
                    map.put("content",content);
                    IndexResponse indexRequestBuilder = client.prepareIndex("test2", "my")
                            .setSource(map)
                            .get();
                    int status = indexRequestBuilder.status().getStatus();
                    if(status==201){
                        System.out.println("【执行成功】=="+name);
                    }else{
                        System.out.println("【执行失败失败】==id:"+id);
                    }
                    String updateSql="update t_article set is_index=true where id="+id;
                    PreparedStatement updatePstmt = con.prepareStatement(updateSql);
                    updatePstmt.executeUpdate();
                }
                String countSql="select count(id) as total from t_article where state=1 AND is_index=FALSE";
                PreparedStatement countPstmt = con.prepareStatement(countSql);
                ResultSet resultSet = countPstmt.executeQuery();
                if(resultSet.next()){
                    int total = resultSet.getInt("total");
                    if(total==0){
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                client.close();
                try {
                    dbUtil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
