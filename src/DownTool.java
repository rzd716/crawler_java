import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownTool {
    /**
     * 根据 URL 和网页类型生成需要保存的网页的文件名，去除 URL 中的非文件名字符
     */
    private String getFileNameByUrl(String url, String contentType) {
        // 移除 "http://" 这七个字符
        url = url.substring(7);
        // 确认抓取到的页面为 text/html 类型
        if (contentType.indexOf("html") != -1) {
            // 把所有的url中的特殊符号转化成下划线
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
        } else {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + "."
                    + contentType.substring(contentType.lastIndexOf("/") + 1);
        }
        return url;
    }

    /**
     * 保存网页字节数组到本地文件，filePath 为要保存的文件的相对地址
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(
                    new File(filePath)));
            for (int i = 0; i < data.length; i++)
                out.write(data[i]);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 用httpclient工具下载 URL 指向的网页
    public String downloadFile(String url) {
        String filePath = null;

        CloseableHttpClient httpClient=HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);

        //设置http连接超时5s，请求超时4s
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(4000).build();
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse= httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
            {
                System.err.println("connection fail");
            }
            HttpEntity httpEntity=httpResponse.getEntity();
            byte[] responseBody=EntityUtils.toByteArray(httpEntity);

         filePath="E:\\crawler_test\\"+getFileNameByUrl(url,httpEntity.getContentType().getValue());
         saveToLocal(responseBody,filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (httpResponse!=null)
                httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }
    public static void main(String[] args)
    {
DownTool downTool=new DownTool();
        downTool.downloadFile("http://www.baidu.com");
    }
}
