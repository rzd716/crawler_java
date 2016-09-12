import java.util.ArrayList;

/**
 * Created by rzd on 16-9-12.
 */
public class Main {

    public static void main(String[] args) {
        // 定义即将访问的链接
        String url = "http://www.zhihu.com/explore/recommendations";
        // 访问链接并获取页面内容
        String content = Spider.SendGet(url);
        // 获取该页面的所有的知乎对象
        ArrayList<Zhihu> myZhihu = Spider.GetZhihu(content);
        // 打印结果
        System.out.println(myZhihu);
    }
}