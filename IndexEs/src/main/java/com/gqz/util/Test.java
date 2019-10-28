package com.gqz.util;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author java1234_小锋
 * @site www.gqz.com
 * @company Java知识分享网
 * @create 2018-12-23 下午 3:33
 */
public class Test {

    private static String host="192.168.1.108"; // 服务器地址
    private static int port=9300; // 端口

    public static void main(String[] args) throws Exception{
        // 创建client
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddresses(new TransportAddress(InetAddress.getByName(host), port));


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",1);
        map.put("name","Java1234");
        map.put("content","Java知识分享网");
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
