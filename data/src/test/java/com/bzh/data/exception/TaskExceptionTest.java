package com.bzh.data.exception;

import com.bzh.data.ApplicationTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-17<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */

@RunWith(MockitoJUnitRunner.class)
public class TaskExceptionTest extends ApplicationTestCase {

    @Mock
    TaskException dataLayerException;


    @Test
    public void test1() {

        when(dataLayerException.getMessage()).thenReturn("没有网络连接");

        assertEquals(dataLayerException.getMessage(), "没有网络连接");

    }

}