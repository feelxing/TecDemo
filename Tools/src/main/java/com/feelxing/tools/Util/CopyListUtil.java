package com.feelxing.tools.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MEV on 2017/5/27.
 */
public class CopyListUtil {
    public static void main(String[] args) {
        List<String> list= Arrays.asList("1","1","1","1","1","1","1");
        List tlist= new ArrayList<>();
        for(int i=0;i<2;i++){
            List<String> subList=new ArrayList<>();
            subList.addAll(list);

            for(int j=0;j<subList.size();j++){
                subList.remove(j);
                subList.add(j,"aa"+i);
            }
            tlist.add(subList);

        }
        System.out.println(tlist);
    }
}
