package com.example.greatbook.utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.beans.BookDesBean;
import com.example.greatbook.beans.BookDetailBean;
import com.example.greatbook.beans.BookKindBean;
import com.example.greatbook.beans.BookKindListBean;
import com.example.greatbook.beans.NewBookBean;
import com.example.greatbook.beans.leancloud.LBookDesCatalogue;
import com.example.greatbook.beans.leancloud.LBookKindBean;
import com.example.greatbook.beans.leancloud.LBookKindListBean;
import com.example.greatbook.constants.Url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class JsoupUtils {
    public static List<BookKindListBean> getBookKindList(String html){
        List<BookKindListBean> bookKindList=new ArrayList<BookKindListBean>();
        Document document=null;
        document= Jsoup.parse(html);
        /**
         * 此行语句执行后获得的Elements实际只有一个Element。
         * elements.first().select(("a[href]"))通过此，获取这个
         * 单一Elements下的多个<a><a/>，然后遍历得到的Elements
         * 数据结构：
         * <div class="mzBox">
         *     <a href="/mingzhu/waiguowenxuemingzhu/" target="_blank">外国文学名著</a>
         *     ......
         */

        Elements elements=document.getElementsByClass("mzBox");
        Elements links=elements.first().select(("a[href]"));
        for (Element element:links){
            String url=element.select("a").attr("href");

            if (url.contains("waiguowenxuemingzhu")){
                BookKindListBean bookKindListBean =new BookKindListBean();
                /**
                 * 此处解析出来的url和Retrofit中的baseurl。
                 * 如果直接拼接会有俩个//
                 * (“http://www.sbkk8.cn/“+“/mingzhu/renwuchuanji/”)。
                 * 所以请求的url就是错误的，因此在此，对String进行截取。
                 */
                bookKindListBean.setUrl(url.substring(9, url.length()-1));
                bookKindListBean.setTitle(element.select("a").text());
                bookKindList.add(bookKindListBean);
            }else if (url.contains("gushihui")){
                BookKindListBean bookKindListBean =new BookKindListBean();
                bookKindListBean.setUrl(url);
                bookKindListBean.setTitle(element.select("a").text());
                bookKindList.add(bookKindListBean);
            }else if (url.contains("renwuchuanji")){
                BookKindListBean bookKindListBean =new BookKindListBean();
                bookKindListBean.setUrl(url.substring(9, url.length()-1));
                bookKindListBean.setTitle(element.select("a").text());
                bookKindList.add(bookKindListBean);
            }else if (url.contains("zhongguoxiandaiwenxuemingzhu")){
                BookKindListBean bookKindListBean =new BookKindListBean();
                bookKindListBean.setUrl(url.substring(9, url.length()-1));
                bookKindListBean.setTitle(element.select("a").text());
                bookKindList.add(bookKindListBean);
            }else if (url.contains("lizhishu")){
                BookKindListBean bookKindListBean =new BookKindListBean();
                bookKindListBean.setUrl(url);
                bookKindListBean.setTitle(element.select("a").text());
                bookKindList.add(bookKindListBean);
            }else if (url.contains("wuxia")){
                BookKindListBean bookKindListBean =new BookKindListBean();
                bookKindListBean.setUrl(url);
                bookKindListBean.setTitle(element.select("a").text());
                bookKindList.add(bookKindListBean);
            }

        }
        return bookKindList;
    }
    public static List<BookKindBean> getBookKind(String html){
        List<BookKindBean> datas=new ArrayList<BookKindBean>();
        Document document=Jsoup.parse(html);
        Elements elements=document.select("ul.bookIndex > li");
        for (Element element:elements){
            String url=element.select("a").attr("href");
            BookKindBean bookKindBean=new BookKindBean();
            /**
             * 两个网站文章链接不同，所以进行判断。
             */
            if (StringUtil.isEquals(url.substring(0,1),"/")) {
                bookKindBean.setUrl(Url.HOST + url);
            }else{
                bookKindBean.setUrl(url);
            }
            //这是一个不合群的链接，先把她去掉
            if (url.contains("taiger")||url.contains("wufengchaoyangdao")
                    ||url.contains("tiancaihunhun")||url.contains("longxiangji")
                    ||url.contains("yupeiyinling")||url.contains("cuoluanjianghu"))
                continue;
            bookKindBean.setUrlPhoto(Url.HOST+element.select("img").attr("src"));
            bookKindBean.setTitle(element.select("a").text());
            datas.add(bookKindBean);
        }
        return datas;
    }

    public static List<BookKindBean> getWuXiaBookKindOne(String html){
        List<BookKindBean> datas=new ArrayList<BookKindBean>();
        Document document=Jsoup.parse(html);
        Element ul=document.select("div.book_list > ul").first();
        Elements elements=ul.select("ul >li");
        for (Element element:elements){
            String url=element.select("a").attr("href");
            BookKindBean bookKindBean=new BookKindBean();
                bookKindBean.setUrl(url);
            bookKindBean.setUrlPhoto(Url.HOST+element.select("img").attr("src"));
            bookKindBean.setTitle(element.select("a").text());
            datas.add(bookKindBean);
        }
        return datas;
    }

    public static List<BookKindBean> getWuXiaBookKindTwo(String html){
        List<BookKindBean> datas=new ArrayList<BookKindBean>();
        Document document=Jsoup.parse(html);
        Elements elements=document.select("ul.bookIndex > li");
        for (Element element:elements){
            String url=element.select("a").attr("href");
            BookKindBean bookKindBean=new BookKindBean();
            bookKindBean.setUrl(Url.HOST+url);
            bookKindBean.setUrlPhoto(Url.HOST+element.select("img").attr("src"));
            bookKindBean.setTitle(element.select("a").text());
            datas.add(bookKindBean);
        }
        return datas;
    }
    public static BookDesBean getBookDes(String html){
        BookDesBean bookDesBean =new BookDesBean();
        List<BookDesBean.Catalogue> catalogueList=new ArrayList<BookDesBean.Catalogue>();
        Document document=Jsoup.parse(html);
        //解析目录(Catalogue)信息
        //解析描述(Des)信息
        if (!StringUtil.isEmpty(document.select("p.des").first().text())) {
            bookDesBean.setDes(document.select("p.des").first().text());
        }else{
            bookDesBean.setDes("本书暂没有简介。");
        }
        Elements elements = document.select("ul.leftList >li");
        for (Element element : elements) {
            BookDesBean.Catalogue catalogue = bookDesBean.new Catalogue();
            String url = element.select("a").attr("href");
            catalogue.setUrl(Url.HOST + url);
            catalogue.setTitle(element.select("a").text());
            catalogueList.add(catalogue);
        }
        bookDesBean.setCatalogueList(catalogueList);

        return bookDesBean;
    }

    public static List<NewBookBean> getNewBook(String html){
        List<NewBookBean> datas=new ArrayList<NewBookBean>();
        Document document=Jsoup.parse(html);
        Elements elements = document.select("div.rightList >span");
        for (Element element : elements) {
            NewBookBean newBookBean=new NewBookBean();
            String url = element.select("a").attr("href");
            newBookBean.setUrl(Url.HOST + url);
            newBookBean.setTitle(element.select("a").text());
            datas.add(newBookBean);
        }

        return datas;
    }

    public static BookDesBean getDataFromGuiStory(String html){
        BookDesBean bookDesBean =new BookDesBean();
        List<BookDesBean.Catalogue> catalogueList=new ArrayList<BookDesBean.Catalogue>();
        bookDesBean.setDes("这本书暂时没有简介。");
        Document document=Jsoup.parse(html);
        Elements elements = document.select("ul.infoListUL >li");
        for (Element element : elements) {
            BookDesBean.Catalogue catalogue = bookDesBean.new Catalogue();
            String url = element.select("a").attr("href");
            catalogue.setUrl(Url.HOST + url);
            catalogue.setTitle(element.select("a").text());
            catalogueList.add(catalogue);
        }
        bookDesBean.setCatalogueList(catalogueList);
        return bookDesBean;
    }

    public static BookDetailBean getBookDetail(String html){
        BookDetailBean bookDetailBean=new BookDetailBean();
        Document document=Jsoup.parse(html);
        //通过id查找
        bookDetailBean.setTitle(document.select("div#f_title1 >h1").text());
        Elements elements=document.select("div.f_article");
        Element main=elements.first();
        bookDetailBean.setCss(main.select("link").attr("href"));
        //bookDetailBean.setContent(orderText(main.text()));
        bookDetailBean.setContent(main.select("p").html());
        return bookDetailBean;
    }
    public static BookDetailBean getBookDetailGuiStory(String html){
        BookDetailBean bookDetailBean=new BookDetailBean();
        Document document=Jsoup.parse(html);
        //通过id查找
        bookDetailBean.setTitle(document.select("div.shareBox >h1").text());
        Elements elements=document.select("div.articleText");
        Element main=elements.first();
        bookDetailBean.setCss(main.select("link").attr("href"));
        bookDetailBean.setContent(orderText(main.text()));
        return bookDetailBean;
    }

    public static String orderText(String text){
        String oneString=text.substring(0,text.indexOf("【"));
        String firstString=oneString.replace(" ","\n    ");
        return firstString+"\n"+"本章已经结束，祝您阅读愉快。";
    }
}
