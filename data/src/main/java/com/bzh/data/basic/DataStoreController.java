package com.bzh.data.basic;

import android.support.annotation.NonNull;

import com.bzh.common.utils.SystemUtils;
import com.bzh.data.exception.TaskException;
import com.bzh.data.film.DetailEntity;
import com.bzh.log.MyLog;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-27<br>
 * <b>描述</b>：　　　数据相关的处理方法<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DataStoreController {

    ///////////////////////////////////////////////////////////////////////////
    // Single Instance
    public static DataStoreController dataStoreController;


    public static DataStoreController getInstance() {
        DataStoreController tmp = dataStoreController;
        if (tmp == null) {
            synchronized (DataStoreController.class) {
                tmp = dataStoreController;
                if (tmp == null) {
                    tmp = new DataStoreController();
                    dataStoreController = tmp;
                }
            }
        }
        return tmp;
    }
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////

    // 公共
    private static final String TRANSLATIONNAME = "译名";
    private static final String NAME = "片名";
    private static final String YEARS = "年代";
    private static final String COUNTRY = "国家";
    private static final String CATEGORY = "类别";
    private static final String LANGUAGE = "语言";
    private static final String SHOWTIME = "片长";
    private static final String DIRECTOR = "导演";
    private static final String LEADINGPLAYERS = "主演";
    private static final String DESCRIPTION = "简介";
    private static final String AREA = "地区";

    // 电影
    private static final String SUBTITLE = "字幕";
    private static final String FILEFORMAT = "文件格式";
    private static final String VIDEOSIZE = "视频尺寸";
    private static final String FILESIZE = "文件大小";
    private static final String IMDB = "IMDb评分";

    // 电视剧
    private static final String EPISODENUMBER = "集数";
    private static final String PLAYTIME = "上映日期";

    // 日韩电视剧
    private static final String PLAYNAME = "剧名";
    private static final String SOURCE = "播送";
    private static final String TYPE = "类型";
    private static final String PREMIERE = "首播";
    private static final String PREMIERE_TIME = "首播日期";
    private static final String TIME = "时间";
    private static final String JIE_DANG = "接档";
    private static final String SCREENWRITER = "编剧";


    // 欧美电视剧
    private static final String TVSTATION = "电视台";
    private static final String TVSTATION_1 = "播放平台";
    private static final String PERFORMER = "演员";
    private static final String SOURCENAME = "原名";

    // 游戏
    private static final String GAME_NAME = "中文名称";
    private static final String GAME_TYPE = "游戏类型";
    private static final String GAME_LANGUAGE = "游戏语言";
    private static final String GAME_DESCRIPTION = "游戏简介";

    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // Charset
    private final String TO_CHARSET_NAME = "GB2312";
    ///////////////////////////////////////////////////////////////////////////

    private Gson gson = new Gson();

    ///////////////////////////////////////////////////////////////////////////
    // variable
    private Func1<ResponseBody, String> transformCharset;
    private Func1<String, ArrayList<BaseInfoEntity>> listFun;
    private Func1<String, DetailEntity> filmDetailFun;
//    private Func1<String, ArrayList<MeiZiEntity>> meiziListFun;
    ///////////////////////////////////////////////////////////////////////////

    private void fillFormat(String regular, String splitRegular, DetailEntity entity, String html) {
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String result = rejectHtmlSpaceCharacters(matcher.group());
            String[] split = result.split(splitRegular);
            for (String aSplit : split) {
                String info = rejectSpecialCharacter(aSplit);
                MyLog.d("Info = [" + info + "]");
                if (info.startsWith(NAME)) {
                    // 片名
                    info = info.substring(info.indexOf(NAME) + NAME.length());
                    entity.setName(info);
                } else if (info.startsWith(SOURCENAME)) {
                    // 原名
                    info = info.substring(info.indexOf(SOURCENAME) + SOURCENAME.length());
                    entity.setName(info);
                } else if (info.startsWith(TRANSLATIONNAME)) {
                    // 译名
                    info = info.substring(info.indexOf(TRANSLATIONNAME) + TRANSLATIONNAME.length());
                    entity.setTranslationName(info);
                } else if (info.startsWith(YEARS)) {
                    // 年代
                    info = info.substring(info.indexOf(YEARS) + YEARS.length());
                    entity.setYears(info);
                } else if (info.startsWith(COUNTRY)) {
                    // 国家
                    info = info.substring(info.indexOf(COUNTRY) + COUNTRY.length());
                    entity.setCountry(info);
                } else if (info.startsWith(AREA)) {
                    // 地区
                    info = info.substring(info.indexOf(AREA) + AREA.length());
                    entity.setCountry(info);
                } else if (info.startsWith(CATEGORY)) {
                    // 类别
                    info = info.substring(info.indexOf(CATEGORY) + CATEGORY.length());
                    entity.setCategory(info);
                } else if (info.startsWith(LANGUAGE)) {
                    // 语言
                    info = info.substring(info.indexOf(LANGUAGE) + LANGUAGE.length());
                    entity.setLanguage(info);
                } else if (info.startsWith(SUBTITLE)) {
                    // 字幕
                    info = info.substring(info.indexOf(SUBTITLE) + SUBTITLE.length());
                    entity.setSubtitle(info);
                } else if (info.startsWith(FILEFORMAT)) {
                    // 文件格式
                    info = info.substring(info.indexOf(FILEFORMAT) + FILEFORMAT.length());
                    entity.setFileFormat(info);
                } else if (info.toLowerCase().startsWith(IMDB.toLowerCase())) {
                    // IMDB评分
                    info = info.substring(info.toLowerCase().indexOf(IMDB.toLowerCase()) + IMDB.length());
                    entity.setImdb(info);
                } else if (info.startsWith(VIDEOSIZE)) {
                    // 视频尺寸
                    info = info.substring(info.indexOf(VIDEOSIZE) + VIDEOSIZE.length());
                    entity.setVideoSize(info);
                } else if (info.startsWith(FILESIZE)) {
                    // 文件大小
                    info = info.substring(info.indexOf(FILESIZE) + FILESIZE.length());
                    entity.setFileSize(info);
                } else if (info.startsWith(SHOWTIME)) {
                    // 片场
                    info = info.substring(info.indexOf(SHOWTIME) + SHOWTIME.length());
                    entity.setShowTime(info);
                } else if (info.startsWith(DIRECTOR)) {
                    // 导演
                    info = info.substring(info.indexOf(DIRECTOR) + DIRECTOR.length());
                    if (entity.getDirectors() == null) {
                        entity.setDirectors(new ArrayList<String>());
                    }
                    if (info.contains("•")) {
                        String[] strings = info.split("•");
                        entity.getDirectors().addAll(Arrays.asList(strings));
                    } else {
                        entity.getDirectors().add(info);
                    }
                } else if (info.startsWith(LEADINGPLAYERS)) {
                    // 主演
                    info = info.substring(info.indexOf(LEADINGPLAYERS) + LEADINGPLAYERS.length());
                    if (entity.getLeadingPlayers() == null) {
                        entity.setLeadingPlayers(new ArrayList<String>());
                    }
                    if (info.startsWith("<br>")) {
                        String[] leadingplayers = info.split("<br>");
                        entity.getLeadingPlayers().addAll(Arrays.asList(leadingplayers));
                    } else {
                        entity.getLeadingPlayers().add(info);
                    }
                } else if (info.startsWith(DESCRIPTION)) {
                    // 描述
                    info = info.substring(info.indexOf(DESCRIPTION) + DESCRIPTION.length());
                    entity.setDescription(info);
                }
                // 话语电视剧
                else if (info.startsWith(PLAYTIME)) {
                    // 上映日期
                    info = info.substring(info.indexOf(PLAYTIME) + PLAYTIME.length());
                    entity.setPlaytime(info);
                } else if (info.startsWith(EPISODENUMBER)) {
                    // 集数
                    info = info.substring(info.indexOf(EPISODENUMBER) + EPISODENUMBER.length());
                    entity.setEpisodeNumber(info);
                }
                // 日韩电视剧
                else if (info.startsWith(PLAYNAME)) {
                    // 剧名
                    info = info.substring(info.indexOf(PLAYNAME) + PLAYNAME.length());
                    entity.setName(info);
                } else if (info.startsWith(SOURCE)) {
                    // 来源
                    info = info.substring(info.indexOf(SOURCE) + SOURCE.length());
                    entity.setSource(info);
                } else if (info.startsWith(TYPE)) {
                    // 类型
                    info = info.substring(info.indexOf(TYPE) + TYPE.length());
                    entity.setCategory(info);
                } else if (info.startsWith(PREMIERE)) {
                    // 首播
                    info = info.substring(info.indexOf(PREMIERE) + PREMIERE.length());
                    entity.setPlaytime(info);
                } else if (info.startsWith(PREMIERE_TIME)) {
                    // 首播时间
                    info = info.substring(info.indexOf(PREMIERE_TIME) + PREMIERE_TIME.length());
                    entity.setPlaytime(info);
                } else if (info.startsWith(TIME)) {
                    // 时间
                    info = info.substring(info.indexOf(TIME) + TIME.length());
                    entity.setPlaytime(info);
                } else if (info.startsWith(JIE_DANG)) {
                    // 接档
                    info = info.substring(info.indexOf(JIE_DANG) + JIE_DANG.length());
                    entity.setJieDang(info);
                } else if (info.startsWith(SCREENWRITER)) {
                    // 编剧
                    info = info.substring(info.indexOf(SCREENWRITER) + SCREENWRITER.length());
                    if (entity.getScreenWriters() == null) {
                        entity.setScreenWriters(new ArrayList<String>());
                    }
                    if (info.contains("•")) {
                        String[] strings = info.split("•");
                        entity.getScreenWriters().addAll(Arrays.asList(strings));
                    } else {
                        entity.getScreenWriters().add(info);
                    }
                }
                // 欧美电视剧
                else if (info.startsWith(TVSTATION)) {
                    // 电视台
                    info = info.substring(info.indexOf(TVSTATION) + TVSTATION.length());
                    entity.setSource(info);
                } else if (info.startsWith(TVSTATION_1)) {
                    // 电视台
                    info = info.substring(info.indexOf(TVSTATION_1) + TVSTATION_1.length());
                    entity.setSource(info);
                } else if (info.startsWith(PERFORMER)) {
                    // 演员
                    info = info.substring(info.indexOf(PERFORMER) + PERFORMER.length());
                    if (entity.getLeadingPlayers() == null) {
                        entity.setLeadingPlayers(new ArrayList<String>());
                    }
                    if (info.contains("•")) {
                        String[] strings = info.split("•");
                        entity.getLeadingPlayers().addAll(Arrays.asList(strings));
                    } else {
                        entity.getLeadingPlayers().add(info);
                    }
                }
            }
        }
    }

    @NonNull
    public Observable<ArrayList<BaseInfoEntity>> getNewWorkObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), getListFun());
    }

    @NonNull
    public Observable<DetailEntity> getNewWorkDetailObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), getDetailFun());
    }

    /*@NonNull
    public Observable<ArrayList<MeiZiEntity>> getNewWorkMeiZiObservable(final Observable<ResponseBody> observable) {
        return getObservable(observable, getTransformCharset(), getMeiZiFun());
    }*/

    /*private Func1<String, ArrayList<MeiZiEntity>> getMeiZiFun() {
        if (meiziListFun == null) {
            meiziListFun = new Func1<String, ArrayList<MeiZiEntity>>() {
                @Override
                public ArrayList<MeiZiEntity> call(String s) {
                    Document document = Jsoup.parse(s);
                    Element pageNumbers = document.select("a.page-numbers").select(":not(.next)").last();

                    if (pageNumbers.text() != null && !"".equals(pageNumbers.text()) && MeiZiNetWorkDataStore.MAX_INDEX == -1) {
                        MeiZiNetWorkDataStore.MAX_INDEX = Integer.valueOf(pageNumbers.text());
                    }

                    Elements elements = document.select("div#comments").select("ul");
                    Elements srcs = elements.select("img[src]");

                    ArrayList<MeiZiEntity> meiZiEntities = new ArrayList<>();
                    for (Element element : srcs) {
                        MeiZiEntity meiZiEntity = new MeiZiEntity();
                        meiZiEntity.setUrl(element.attr("src"));
                        meiZiEntities.add(meiZiEntity);
                    }
                    return meiZiEntities;
                }
            };
        }
        return meiziListFun;
    }*/

    @NonNull
    private Func1<String, ArrayList<BaseInfoEntity>> getListFun() {
        if (listFun == null) {
            listFun = new Func1<String, ArrayList<BaseInfoEntity>>() {
                @Override
                public ArrayList<BaseInfoEntity> call(String s) {
                    Document document = Jsoup.parse(s);
                    Elements elements = document.select("div.co_content8").select("ul");
                    Elements hrefs = elements.select("a[href]");

                    Pattern pattern = Pattern.compile("^\\[.*\\]$");
                    ArrayList<BaseInfoEntity> filmEntities = new ArrayList<>();
                    for (Element element : hrefs) {
                        String fullName = element.text();
                        if (pattern.matcher(fullName).matches()) {
                            continue;
                        }
                        BaseInfoEntity baseInfoEntity = new BaseInfoEntity();
                        if (isFilmType(fullName)) {
                            baseInfoEntity.setName(fullName.substring(0, fullName.lastIndexOf("》") + 1));
                        } else {
                            baseInfoEntity.setName(fullName);
                        }
                        baseInfoEntity.setUrl(element.attr("href"));
                        filmEntities.add(baseInfoEntity);
                    }

                    Elements fonts = elements.select("font");
                    for (int i = 0; i < fonts.size() && filmEntities.size() >= fonts.size(); i++) {
                        String fullName = fonts.get(i).text();
                        int start = fullName.indexOf("：") + 1;
                        int end = fullName.indexOf("点击");
                        if (start > 0 && end > 0 && end > start && end < fullName.length()) {
                            fullName = fullName.substring(start, end).trim();
                            filmEntities.get(i).setPublishTime(fullName);
                        } else {
                            filmEntities.get(i).setPublishTime(fullName.trim());
                        }
                    }
                    return filmEntities;
                }
            };
        }
        return listFun;
    }

    private boolean isFilmType(String fullName) {
        return fullName.contains("》");
    }

    @NonNull
    private Func1<String, DetailEntity> getDetailFun() {
        if (filmDetailFun == null) {
            filmDetailFun = new Func1<String, DetailEntity>() {
                @Override
                public DetailEntity call(String s) {
                    DetailEntity entity = new DetailEntity();
                    Document document = Jsoup.parse(s);
                    String html = document.select("div.co_content8").select("ul").toString();
                    html = html.substring(0, html.indexOf("</table>"));

                    String publishTime = getPublishTime(html);
                    String title = document.select("div.co_area2").select("div.title_all").select("font").first().text();
                    String coverUrl = document.select("div.co_content8").select("ul").select("img").first().attr("src");
                    String previewImage = document.select("div.co_content8").select("ul").select("img").last().attr("src");
                    ArrayList<String> downloadUrls = getDownloadUrls(document);
                    ArrayList<String> downloadNames = getDownloadNames(document);

                    // "◎译　　名.*<br>"
                    String patterRegular = "◎.*<br>";
                    String splitRegular = "◎";
                    fillFormat(patterRegular, splitRegular, entity, html);

                    // [剧　名]:
                    patterRegular = "\\[.*<br>";
                    splitRegular = "\\[";
                    fillFormat(patterRegular, splitRegular, entity, html);

                    // 【译 　名】： 黑吃黑
                    patterRegular = "【.*<br>";
                    splitRegular = "【";
                    fillFormat(patterRegular, splitRegular, entity, html);

                    entity.setTitle(title);
                    entity.setPublishTime(publishTime);
                    entity.setCoverUrl(coverUrl);
                    entity.setPreviewImage(previewImage);
                    entity.setDownloadUrls(downloadUrls);
                    entity.setDownloadNames(downloadNames);
                    return entity;
                }
            };
        }
        return filmDetailFun;
    }

    private ArrayList<String> getDownloadNames(Document document) {
        ArrayList<String> strings = new ArrayList<>();
        Elements elements = document.select("div.co_content8").select("ul").select("a");

        for (Element e : elements) {
            String href = e.attr("href");
            MyLog.d("href = [" + href + "]");
            if (href.startsWith("ftp") || (href.startsWith("http") && !href.contains("www.ygdy8.net") && !href.contains("www.dytt8.net"))) {
                href = href.substring(href.indexOf("]") + 1, href.length()).replaceAll(".rmvb", "").replaceAll(".mkv", "").replaceAll(".mp4", "");
                strings.add(href);
            }
        }
        return strings;
    }


    private ArrayList<String> getDownloadUrls(Document document) {
        ArrayList<String> strings = new ArrayList<>();
        Elements elements = document.select("div.co_content8").select("ul").select("a");

        for (Element e : elements) {
            String href = e.attr("href");
            MyLog.d("href = [" + href + "]");
            if (href.startsWith("ftp") || (href.startsWith("http") && !href.contains("www.ygdy8.net"))) {
                strings.add(href);
            }
        }
        return strings;
    }

    @NonNull
    private Func1<ResponseBody, String> getTransformCharset() {
        if (transformCharset == null) {
            transformCharset = new Func1<ResponseBody, String>() {
                @Override
                public String call(ResponseBody responseBody) {
                    try {
                        return new String(responseBody.bytes(), TO_CHARSET_NAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new TaskException(TaskException.ERROR_HTML_PARSE);
                    }
                }
            };
        }
        return transformCharset;
    }

    @NonNull
    private String getPublishTime(String html) {
        String publishTime;
        Pattern pattern = Pattern.compile("发布时间：.*&");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            publishTime = matcher.group();
            publishTime = publishTime.substring(publishTime.indexOf("：") + 1, publishTime.length() - 1);
        } else {
            publishTime = "";
        }
        return rejectHtmlSpaceCharacters(publishTime);
    }

    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "(\\s|　|&nbsp;)*|\t|\r|\n";//定义空格回车换行符
    private static final String regEx_regular = "(\\]|:|】|：)*";

    private String rejectHtmlSpaceCharacters(String str) {
        Pattern p_html = Pattern.compile(regEx_html);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll(""); // 过滤html标签
        Pattern p_space = Pattern.compile(regEx_space);
        Matcher m_space = p_space.matcher(str);
        str = m_space.replaceAll(""); // 过滤空格回车标签
        return str.trim(); // 返回文本字符串
    }

    private String rejectSpecialCharacter(String str) {
        Pattern compile = Pattern.compile(regEx_regular);
        Matcher matcher = compile.matcher(str);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        return str;
    }

    private <Entity> Observable<Entity> getObservable(final Observable<ResponseBody> observable, final Func1<ResponseBody, String> transformCharset, final Func1<String, Entity> transformToEntity) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override
            public void call(final Subscriber<? super Entity> subscriber) {
                observable
                        .map(transformCharset)
                        .map(transformToEntity)
                        .subscribe(new Action1<Entity>() {
                            @Override
                            public void call(Entity entity) {
                                MyLog.json(gson.toJson(entity));
                                Observable.just(entity)
                                        .subscribe(subscriber);
                            }
                        }, getOnErrorProcess(subscriber));
            }
        });
    }

    @NonNull
    private Action1<Throwable> getOnErrorProcess(final Subscriber subscriber) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (SystemUtils.getNetworkType() == SystemUtils.NETWORK_TYPE_NONE) {
                    subscriber.onError(new TaskException(TaskException.ERROR_NONE_NETWORK));
                } else {
                    subscriber.onError(new TaskException(TaskException.ERROR_UNKNOWN));
                }
            }
        };
    }

}
