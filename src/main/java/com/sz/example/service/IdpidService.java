package com.sz.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz.common.dao.BaseDao;
import com.sz.example.dao.mapper.IdpidMapper;
import com.sz.example.pojo.Idpid;
import com.sz.example.pojo.IdpidExample;

@Transactional
@Service
public class IdpidService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private IdpidMapper idpidMapper;

	public List<Idpid> getIdpid(){
		List<Idpid> list=new ArrayList<Idpid>();
		List<Idpid> all=idpidMapper.selectByExample(new IdpidExample());
		List<Idpid> root=this.getRoot(all);//取得根节点
		for(Idpid idpid : root){
			list.add(idpid);
			this.doIdpid(list, all, idpid);
		}
		
		System.out.println(list.size());
		return list;
	}
	
	/**
	 * 取得根节点
	 * @param all
	 * @return
	 */
	public List<Idpid> getRoot(List<Idpid> all){
		List<Idpid> root=new ArrayList<Idpid>();
		for(Idpid idpid : all){
			Integer pid=idpid.getPid();
			if(pid==null){
				root.add(idpid);
			}
		}
		return root;
	}
	
	/**
	 * 递归核心方法
	 * @param list
	 * @param all
	 * @param idpid
	 */
	public void doIdpid(List<Idpid> list,List<Idpid> all,Idpid idpid){
		int id=idpid.getId();
		List<Idpid> children=this.getChildren(all, id);
		for(Idpid c : children){
			list.add(c);
			doIdpid(list,all,c);
		}
	}
	
	/**
	 * 取得子节点
	 * @param all
	 * @param id
	 * @return
	 */
	public List<Idpid> getChildren(List<Idpid> all,int id){
		List<Idpid> children=new ArrayList<Idpid>();
		for(Idpid idpid : all){
			if(idpid.getPid()!=null && id==idpid.getPid().intValue()){
				children.add(idpid);
			}
		}
		return children;
	}
	
	
	
	public static void main(String[] args){
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
		IdpidService s=(IdpidService)context.getBean("idpidService");
		s.getIdpid();
		System.out.println("main执行结束");
	}
}
