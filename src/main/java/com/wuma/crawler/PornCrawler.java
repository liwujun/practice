package com.wuma.crawler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuma on 2017/4/8.
 */
public class PornCrawler {
    public static final String url = "http://pornfactor.net/page/^_^/";
    public static final int start = 1;
    public static final int end = 1967;
    public static HttpClient httpClient = new HttpClient();
    public static final Logger logger = Logger.getLogger(PornCrawler.class);
    public static final String resource_path = "/Users/phper/resources";

    public static String con_url = "jdbc:mysql://localhost:3306/porn";//characterEncoding=GBK
    public static String username = "root";
    public static String password = "Auto,#8869";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;
    public static String sql_insert = "insert into resources (img_url,title,num,`desc`,img_name) values(?,?,?,?,?)";

    static {
        // 定位驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("加载驱动成功!");
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败!");
            e.printStackTrace();
        }
        // 建立连接
        try {
            con = DriverManager.getConnection(con_url, username, password);
            stmt = con.createStatement();
            System.out.println("数据库连接成功!");
        } catch (SQLException e) {
            System.out.println("数据库连接失败!");
        }
    }

    static {
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static String getContent(String url) {
        GetMethod httpGet = new GetMethod(url);
        httpGet.setRequestHeader("Cookie", "__cfduid=d484d8709ce359eb1b85f47956187b42c1491637240; PHPSESSID=ikfv4g3alalbvbo44mgcgglmn2; _ym_uid=1491637245675087036; _ym_isad=2; _ga=GA1.2.1352539430.1491637241");
        int code = 0;
        for (int i = 0; i < 5; i++) {
            try {
                code = getHttpClient().executeMethod(httpGet);
                System.out.println("response code:" + code);

                if (code == HttpStatus.SC_OK) {
                    //获取页面源代码
                    InputStream inputStream = httpGet.getResponseBodyAsStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    StringBuffer stringBuffer = new StringBuffer();
                    String lineString = null;
                    while ((lineString = bufferedReader.readLine()) != null) {
                        stringBuffer.append(lineString);
                        stringBuffer.append("\n");
                    }
                    String content = stringBuffer.toString();
                    return content;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void downloadImg(String url, String path_file) {
        GetMethod httpget = new GetMethod(url);
        for (int i = 0; i < 5; i++) {
            try {
                File file = new File(path_file);
                if (file.exists()) {
                    return;
                }
                int status = getHttpClient().executeMethod(httpget);
                if (status == HttpStatus.SC_OK) {
                    InputStream inputStream = httpget.getResponseBodyAsStream();

                    file.getParentFile().mkdirs();
                    FileOutputStream fileout = new FileOutputStream(file);
                    /**
                     * 根据实际运行效果 设置缓冲区大小
                     */
                    byte[] buffer = new byte[1024];
                    int ch = 0;
                    while ((ch = inputStream.read(buffer)) != -1) {
                        fileout.write(buffer, 0, ch);
                    }
                    inputStream.close();
                    fileout.flush();
                    fileout.close();
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        String articleRegex = "<article class=\"short-story\">([\\s\\S]*?)</article>";
        String profileRegex = "<div class=\"short-title clr\">[\\s\\S]*?<a href=\"(.*?)\">(.*?)</a>[\\s\\S]*?<img src=\"(.*?)\"[^<]*?/>";
        String imgRegex = "<img src=\"(.*?)\"[^<]*?/>";
        String profileImgRegex = "<div class=\"news-text\">[\\s\\S]*?<img src=\"([^<>]*?)\".*?</div>([\\s\\S]*?)<noindex>";
        int count = 0;

        for (int i = 35; i < end; i++) {
            String get = url.replace("^_^", i + "");
            System.out.println("Get url:" + get);
            if (i == 1) {
                get = "http://pornfactor.net";
            }
            count=i*10;
            System.out.println("Get Success");
            String content = getContent(get);
//                    System.out.println("Get content:" + content);
            Pattern pattern = Pattern.compile(articleRegex);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                System.out.println("得到匹配");
                String article = matcher.group(1);
//                System.out.println(article);
                //得到title,url
                Pattern p2 = Pattern.compile(profileRegex);
                Matcher m2 = p2.matcher(article);
                while (m2.find()) {
                    String profile_url = m2.group(1);
                    String title = m2.group(2);
                    String img_url = m2.group(3);
                    System.out.println("profile_url:" + profile_url + "\ntitle:" + title + "\nimg:" + img_url);
                    String profile_content = getContent(profile_url);
                    Pattern p3 = Pattern.compile(profileImgRegex);
                    Matcher m3 = p3.matcher(profile_content);
                    downloadImg(img_url, resource_path + "/" + count + "_0.jpg");
                    String profile_img_url="";
                    String text_profile="";
                    while (m3.find()) {
                        profile_img_url = m3.group(1);
                        text_profile = m3.group(2);
                        downloadImg(profile_img_url, resource_path + "/" + count + "_1.jpg");
                        System.out.println("profile_img_url:" + profile_img_url + "\ntext_profile:" + text_profile);
                    }

                    PreparedStatement preStmt = null;
                    try {
                        //insert into resources (img_url,title,num,desc,img_name) values(?,?,?,?,?)
                        preStmt = con.prepareStatement(sql_insert);
                        preStmt.setString(1, img_url);
                        preStmt.setString(2, title);
                        preStmt.setInt(3, 2);
                        preStmt.setString(4, text_profile);
                        preStmt.setString(5, count + "");
                        preStmt.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                count++;
            }

            System.out.println("结束匹配");

        }
    }
}
