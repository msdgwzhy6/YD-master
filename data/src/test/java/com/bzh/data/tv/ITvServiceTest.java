package com.bzh.data.tv;

import com.bzh.data.repository.RetrofitManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.ResponseBody;
import rx.Subscriber;

import static org.junit.Assert.*;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-27<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class ITvServiceTest {

    private ITvService tvService;
    private Subscriber<ResponseBody> subscriber;

    @Before
    public void setUp() throws Exception {
        tvService = RetrofitManager.getInstance().getTvService();
        subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
                assertNull(e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                assertNotNull(responseBody);
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetChineseDomesticTv() throws Exception {
        tvService.getChineseDomesticTv(1).subscribe(subscriber);
    }


    @Test
    public void testGetHKTTv() throws Exception {
        tvService.getHKTTv(1).subscribe(subscriber);
    }

    @Test
    public void testGetChineseTv() throws Exception {
        tvService.getChineseTv(1).subscribe(subscriber);
    }

    @Test
    public void testGetJapanSouthKoreaTV() throws Exception {
        tvService.getJapanSouthKoreaTV(1).subscribe(subscriber);
    }

    @Test
    public void testGetEuropeAmericaTV() throws Exception {
        tvService.getEuropeAmericaTV(1).subscribe(subscriber);
    }

}