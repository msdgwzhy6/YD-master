package com.bzh.data.variety;

import com.bzh.data.repository.RetrofitManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
public class IVarietyServiceTest {

    private IVarietyService varietyService;
    private Subscriber<ResponseBody> subscriber;

    @Before
    public void setUp() throws Exception {
        varietyService = RetrofitManager.getInstance().getVarietyService();
        subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                System.out.println("IVarietyServiceTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("IVarietyServiceTest.onError");
                System.out.println("e = [" + e + "]");
                assertNull(e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                assertNotNull(responseBody);
                try {
                    assertNotNull(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet2013NewestChineseVariety() throws Exception {
        varietyService.get2013NewestChineseVariety(1).subscribe(subscriber);
    }

    @Test
    public void testGet2013ChineseVariety() throws Exception {
        varietyService.get2013ChineseVariety(1).subscribe(subscriber);
    }

    @Test
    public void testGet2013HKTVariety() throws Exception {
        varietyService.get2013HKTVariety(1).subscribe(subscriber);
    }

    @Test
    public void testGet2013OtherVariety() throws Exception {
        varietyService.get2013OtherVariety(1).subscribe(subscriber);
    }

    @Test
    public void testGet2009ChineseVariety() throws Exception {
        varietyService.get2009ChineseVariety(1).subscribe(subscriber);
    }
}