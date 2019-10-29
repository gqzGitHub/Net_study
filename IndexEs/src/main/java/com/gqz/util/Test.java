package com.gqz.util;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;


 /**
  *
  * @description:   测试elasticsearch
  * @author ganquanzhong
  * @date   2019/10/29 16:27
  */
public class Test {

    /**
     * 配置elasticsearch的信息 服务器地址 端口
     */

    private static String host="49.235.210.101";
    private static int port=9300;

    public static void main(String[] args) throws Exception{
        //设置集群cluster名
        Settings settings = Settings.builder().put("cluster.name","elasticsearch")
                 //.put("client.transport.sniff", true)
                 .build();

        // 创建client
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                //.addTransportAddresses(new TransportAddress(InetAddress.getByName(host), port));
                .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(host), port));


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",1);
        map.put("name","news");
        map.put("content","测试ES集群  gqzdev");
        IndexResponse indexRequestBuilder = client.prepareIndex("test2", "my")
                .setSource(map)
                .get();
        System.out.println(client);

       /* AnalyzeRequest analyzeRequest = new AnalyzeRequest("test2")
                .text("中华人民共和国国歌")
                .analyzer("ik_max_word");

        List<AnalyzeResponse.AnalyzeToken> tokens = client.admin().indices()
                .analyze(analyzeRequest)
                .actionGet()
                .getTokens();

        for (AnalyzeResponse.AnalyzeToken token : tokens) {
            System.out.println(token.getTerm());
        }*/
        client.close();
    }
}
