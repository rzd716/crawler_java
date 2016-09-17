import java.util.Set;


public class BfsSpider {
    /**
     * 使用种子初始化URL队列
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            SpiderQueue.addUnvisitedUrl(seeds[i]);
    }

    public void crawling(String[] seeds) {

        // 初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        // 循环条件：待抓取的链接不空且抓取的网页不多于 30
        while (!SpiderQueue.unVisitedUrlsEmpty()
                && SpiderQueue.getVisitedUrlNum() <= 30) {
            // 队头 URL 出队列
            String visitUrl = (String) SpiderQueue.unVisitedUrlDeQueue();

            DownTool downLoader = new DownTool();
            // 下载网页
            String filePath=downLoader.downloadFile(visitUrl);
            // 该 URL 放入已访问的 URL 中
            SpiderQueue.addVisitedUrl(visitUrl);
            // 提取出下载网页中的 URL
            Set<String> links = HtmlParserTool.extracLinks(filePath);
            // 新的未访问的 URL 入队
            for (String link : links) {

                SpiderQueue.addUnvisitedUrl(link);
            }
        }
    }

    // main 方法入口
    public static void main(String[] args) {
        BfsSpider crawler = new BfsSpider();
        crawler.crawling(new String[] { "http://www.baidu.com" });
    }
}
