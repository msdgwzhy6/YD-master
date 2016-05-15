package com.bzh.data.meizi;

import com.bzh.data.basic.DataStoreController;
import com.bzh.data.basic.MeiZiEntity;

import java.util.ArrayList;

import rx.Observable;

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
public class MeiZiNetWorkDataStore implements IMeiZiDataStore {

    public static int MAX_INDEX = -1;

    private IMeiZiService iMeiZiService;

    public MeiZiNetWorkDataStore(IMeiZiService iMeiZiService) {

        this.iMeiZiService = iMeiZiService;
    }

    @Override
    public Observable<ArrayList<MeiZiEntity>> getMeiZi(int index) {

        return DataStoreController.getInstance().
                getNewWorkMeiZiObservable(iMeiZiService.getMeiZi(index));
    }
}
