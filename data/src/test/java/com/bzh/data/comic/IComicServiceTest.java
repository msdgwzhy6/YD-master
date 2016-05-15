package com.bzh.data.comic;

import com.bzh.data.repository.RetrofitManager;
import com.bzh.data.variety.IVarietyService;

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
public class IComicServiceTest {

    private IComicService iComicService;
    private Subscriber<ResponseBody> subscriber;

    @Before
    public void setUp() throws Exception {
        iComicService = RetrofitManager.getInstance().getComicService();
        subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                System.out.println("IComicServiceTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("IComicServiceTest.onError");
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
    public void testGetComic() throws Exception {
        iComicService.getComic(1).subscribe(subscriber);
    }

    @Test
    public void testGetNewComic() throws Exception {
        iComicService.getNewComic(1).subscribe(subscriber);
    }

    @Test
    public void testGetSSComic() throws Exception {
        iComicService.getSSComic(1).subscribe(subscriber);
    }

    @Test
    public void testGetOtherComic() throws Exception {
        iComicService.getOtherComic(1).subscribe(subscriber);
    }

    @Test
    public void testGetGCComic() throws Exception {
        iComicService.getGCComic(1).subscribe(subscriber);
    }

    @Test
    public void testGetHZWComic() throws Exception {
        iComicService.getHZWComic(1).subscribe(subscriber);
    }

    @Test
    public void testGetHYComic() throws Exception {
        iComicService.getHYComic(1).subscribe(subscriber);
    }
}