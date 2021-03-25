package fudan.se.lab2.service;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;

public class MyTest {
    public static void main(String[] args) {
        //test();

    }

// HttpGet方式

    public static void test(String param) {
        try {
            String urlStr = "http://10.176.122.172:8012/LoanProduct?"; // 请求http地址

            //String param = "username=BA2103154881&password=imbus123"; // 请求参数

// 模拟(创建)一个浏览器用户
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(urlStr + param);

// httpclient进行连接

            CloseableHttpResponse response = client.execute(httpGet);

// 获取内容

            HttpEntity entity = response.getEntity();


// 将内容转化成IO流

            InputStream content = entity.getContent();

// 输入流转换成字符串

            byte[] byteArr = new byte[content.available()];

            content.read(byteArr); // 将输入流写入到字节数组中

            String result = new String(byteArr);

            System.out.println("Successfully：" + result);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
