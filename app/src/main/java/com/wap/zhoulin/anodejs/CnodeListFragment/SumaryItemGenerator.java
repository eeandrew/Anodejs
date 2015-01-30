package com.wap.zhoulin.anodejs.CnodeListFragment;

import java.util.ArrayList;
/**
 * Created by zhoulin on 2014/12/23.
 */
public class SumaryItemGenerator {

    public static ArrayList<SumaryItem> getSumaryItems(){
        ArrayList<SumaryItem> items = new ArrayList<SumaryItem>();
        for(int i=0;i<10;i++){
            SumaryItem item = new SumaryItem();
            item.setLoginname("andrew"+i);
            item.setContent("This is Test Content");
            item.setVisit_count(i*10);
        }
        return items;
    }
}
