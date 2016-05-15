package com.bzh.data.meizi;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.basic.DataStoreController;
import com.bzh.data.basic.MeiZiEntity;
import com.bzh.data.repository.RetrofitManager;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.functions.Func1;

import static org.junit.Assert.*;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-29<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class MeiZiNetWorkDataStoreTest extends ApplicationTestCase {


    private IMeiZiService iMeiZiService;
    private MeiZiNetWorkDataStore meiZiNetWorkDataStore;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        iMeiZiService = RetrofitManager.getInstance().getiMeiZiService();
        meiZiNetWorkDataStore = new MeiZiNetWorkDataStore(iMeiZiService);
    }

    @Test
    public void testGetMeiZi() throws Exception {
        meiZiNetWorkDataStore.getMeiZi(0).subscribe(new Subscriber<ArrayList<MeiZiEntity>>() {
            @Override
            public void onCompleted() {
                System.out.println("IMeiZiServiceTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("IMeiZiServiceTest.onError");
                assertNull(e);
            }

            @Override
            public void onNext(ArrayList<MeiZiEntity> meiZiEntities) {
                System.out.println("IMeiZiServiceTest.onNext");
                System.out.println(gson.toJson(meiZiEntities));
                System.out.println(MeiZiNetWorkDataStore.MAX_INDEX);
            }
        });
    }
}