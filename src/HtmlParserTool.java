import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class HtmlParserTool {

    // 获取一个网站上的链接，filter 用来过滤链接
    public static Set<String> extracLinks(String filePath) {
        Set<String> links = new HashSet<String>();

        File input=new File(filePath);
        try {
            Document doc= Jsoup.parse(input,"UTF-8");
            Elements alinks=doc.select("a[href]");
            for (Element element:alinks)
            {
                links.add(element.attr("abs:href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }
}
