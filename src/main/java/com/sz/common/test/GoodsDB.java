package com.sz.common.test;

import java.util.ArrayList;
import java.util.List;

public class GoodsDB {

	public static List<Goods> getAllGoods(String title){
		List<Goods> list=new ArrayList<Goods>();
		for(int i=1;i<=199;i++){
			Goods goods=new Goods();
			goods.setId(i);
			goods.setTitle("标题"+i);
			goods.setMemo("testtest"+i);
			if(title!=null && !title.isEmpty()){
				if(goods.getTitle().indexOf(title)<0){
					continue;
				}
			}
			list.add(goods);
		}
		return list;
	}
}
