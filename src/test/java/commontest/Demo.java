package commontest;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Demo implements PageProcessor {
	private Site site = new Site().setRetryTimes(3).setSleepTime(100)  
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效  
            .setDomain("rong.36kr.com")  
            //添加抓包获取的cookie信息  
            .addCookie("Hm_lvt_e8ec47088ed7458ec32cde3617b23ee3", "1469520646,1469586395,1469676138,1469692406")
            .addCookie("aliyungf_tc", "AQAAAKXSVimBewsAsWa13HIq5IL93BYb")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的  
            .addHeader("User-Agent",  
                    "ozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")  
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")  
            .addHeader("Accept-Encoding", "gzip, deflate, sdch").addHeader("Accept-Language", "zh-CN,zh;q=0.8")  
            .addHeader("Connection", "keep-alive").addHeader("Referer", "http://www.imooc.com/");  
  

    public void process(Page page) {  
        System.out.println("***********");
    	page.putField("data", page.getJson());
    }  
  

    public Site getSite() {  
        return site;  
    } 
    public static void main(String[] args) {  
        Spider.create(new Demo())  
                // 从"http://www.imooc.com/user/setprofile"开始抓  
                .addUrl("https://rong.36kr.com/api/dict/industry/AUTO?type=e").addPipeline(new ConsolePipeline())  
                // 开启5个线程抓取  
                .thread(5)  
                // 启动爬虫  
                .run();
    }  
}