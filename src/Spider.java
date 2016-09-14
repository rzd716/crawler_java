import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by rzd on 2016/9/14.
 */



public class Spider {
    private static HttpClient httpClient = new DefaultHttpClient();

    /**
     * @param path
     *            目标网页的链接
     * @return 返回布尔值，表示是否正常下载目标页面
     * @throws Exception
     *             读取网页流或写入本地文件流的IO异常
     */
    public static boolean downloadPage(String path) throws Exception {
        // 定义输入输出流
        byte[] input = null;
        OutputStream output = null;
        // 得到 post 方法
        HttpGet getMethod = new HttpGet(path);
        // 执行，返回状态对象
        HttpResponse response = httpClient.execute(getMethod);
        // 针对状态码进行处理
        // 简单起见，只处理返回值为 200 的状态码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntity=response.getEntity();
            input = EntityUtils.toByteArray(httpEntity);
            // 通过对URL的得到文件名
            String filename = path.substring(path.lastIndexOf('/') + 1)
                    + ".html";
            // 获得文件输出流
            output = new FileOutputStream(filename);
            // 输出到文件
            if (input!=null)
            {
                output.write(input);
            }
            else {
                System.out.println("There is no content.");
            }
            // 关闭输出流
            if (output != null) {
                output.close();
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            // 抓取百度首页，输出
            Spider.downloadPage("http://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
