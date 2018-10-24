package com.sz.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.sz.common.constant.Consts;
import com.sz.common.controller.BaseController;
import com.sz.common.csrf.CsrfAnnotation;
import com.sz.common.datatables.DataTables;
import com.sz.common.datatables.DataTablesParameter;
import com.sz.common.job.ExampleJob;
import com.sz.common.util.CommonUtils;
import com.sz.common.util.PageModel;
import com.sz.common.util.QuartzManager;
import com.sz.common.util.RedisUtils;
import com.sz.common.util.StringEscapeEditor;
import com.sz.example.pojo.Idpid;
import com.sz.example.pojo.Item;
import com.sz.example.service.ExampleService;
import com.sz.example.service.IdpidService;
import com.sz.example.service.TestService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/exp")
public class ExampleController extends BaseController {
	@Resource
	private TestService testService;
	@Resource
	private IdpidService idpidService;
	@Resource
	private ExampleService exampleService;
	@Resource
	private RedisUtils redisUtils;
	
	@InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        // 防止XSS攻击
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }
	
	@RequestMapping("/pagination/index")
	public String paginationIndex(HttpServletRequest request) {
		return this.getJspPath("pagination/pagination");
	}
	
	@RequestMapping(value="/pagination/list")
	public String paginationPageGoods(HttpServletRequest request,String title){
		try{
			int pageNo=this.getPageNo(request, 1);
			int pageSize=this.getPageSize(request, 10);
			PageModel page=testService.testPageGoods(pageNo, pageSize,title);
			request.setAttribute("goodsPage", page);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return this.getJspPath("pagination/pagination_data");
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="/zpage/index")
	public String sPageIndex(){
		return this.getJspPath("zpage/zPage");
	}
	
	@RequestMapping(value="/zpage/list")
	public String zPageGoods(HttpServletRequest request,String title,int pageNo,int pageSize){
		try{
			PageModel page=testService.testPageGoods(pageNo, pageSize,title);
			request.setAttribute("goodsPage", page);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return this.getJspPath("zpage/zPage_data");
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("/treegrid/index")
	public String treegridIndex(HttpServletRequest request) {
		List<Idpid> list=idpidService.getIdpid();
		request.setAttribute("treegrid", list);
		return this.getJspPath("tree/treegrid");
	}
	
	@RequestMapping("/treetable/index")
	public String treetableIndex(HttpServletRequest request) {
		List<Idpid> list=idpidService.getIdpid();
		request.setAttribute("treegrid", list);
		return this.getJspPath("tree/treetable");
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	@RequestMapping("/datatables/index")
	@RequiresPermissions("admin:admin")
	public String datatablesIndex(HttpServletRequest request) {
		return this.getJspPath("datatables/datatables");
	}
	
	
	@RequestMapping(value="/datatables/list")
	@ResponseBody
	public DataTables dataTablesPageItem(HttpServletRequest request,String title){
		DataTablesParameter dataTablesParameter=new DataTablesParameter(request);
		return exampleService.dataTablesPageItem(dataTablesParameter,title);
	}
	
	
	@RequestMapping(value="/item/info")
	@ResponseBody
	public Item getItemInfo(String id){
		return exampleService.getItemInfo(id);
	}
	
	@CsrfAnnotation
	@RequestMapping(value="/item/merge",method=RequestMethod.POST)
	@ResponseBody
	public String mergeItem(Item item){
		String result="";
		try{
			exampleService.mergeItem(item);
			result="200";
		}catch(Exception ex){
			ex.printStackTrace();
			result="500 "+ex.getMessage();
		}
		return result;
	}
	
	@RequestMapping(value="/item/del")
	@ResponseBody
	public String deleteItem(String id){
		String result="";
		try{
			exampleService.deleteItem(id);
			result="200";
		}catch(Exception ex){
			ex.printStackTrace();
			result="500 "+ex.getMessage();
		}
		return result;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/fileupload/index")
	public String fileuploadIndex(){
		return this.getJspPath("file/fileupload");
	}
	
	@RequestMapping(value="/file/upload",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject fileupload(HttpServletRequest request,MultipartFile file){
		JSONObject json=new JSONObject();
		try{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			MultipartFile file=multipartRequest.getFileMap().get("file");
			json=validImg(file);
			if(json.get("code").equals("500")){
				return json;
			}
			String fileUploadUrl=Consts.FILE_UPLOAD_URL;
			String fileUploadPath=Consts.FILE_UPLOAD_PATH;
//			String realPath=request.getSession().getServletContext().getRealPath("/");
//			String filePath=realPath+imgUploadPath;
					
			File fileDoc =new File(fileUploadPath);  
			if (!fileDoc.exists()  && !fileDoc.isDirectory()){  
				fileDoc.mkdir();    
			}
			
			String fileName=file.getOriginalFilename();
			String newFileName=UUID.randomUUID().toString().replaceAll("-", "")+fileName.substring(fileName.lastIndexOf("."));
			String fileUrl=fileUploadUrl+newFileName;
			
			//保存文件
			file.transferTo(new File(fileUploadPath+newFileName));
			
			json.put("fileName", newFileName);
			json.put("fileUrl", fileUrl);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return json;
	}
	
	private JSONObject validImg(MultipartFile file){
		String[] suffixArray={"jpg","png"};//后缀格式
		final long maxSize=200*1024;//限制大小
		JSONObject json=new JSONObject();
		String fileName=file.getOriginalFilename();
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
		long fileSize=file.getSize();
		if(!Arrays.asList(suffixArray).contains(suffix)){
			json.put("code", "500");
			json.put("message", "只能上传"+StringUtils.join(suffixArray, ",")+"类型的图片");
		}else if(fileSize>maxSize){
			json.put("code", "500");
			json.put("message", "图片容量不能大于200K");
		}else {
			json.put("code", "200");
		}
		return json;
	}
	
	@RequestMapping(value="/file/download",method=RequestMethod.GET)
	public void filedownload(HttpServletRequest request,HttpServletResponse response,String fileName) throws IOException{
		String realPath=Consts.FILE_UPLOAD_PATH+fileName;
		CommonUtils.downFile(request, response, realPath, fileName);
	}
	
	
	//////////////////////////////////////////////////////
	@RequestMapping(value="/quartzIndex")
	public String exampleJobIndex() {
		return this.getJspPath("job/quartz");
	}
	
	
	@ResponseBody
	@RequestMapping(value="/addJob")
	public String addJob() {
		String result="200";
		try {
			Map<String, Object> param=new HashMap<>();
			param.put("exampleService", exampleService);
			QuartzManager.addJob("testJob", ExampleJob.class, "0/1 * * * * ?", param);
		}catch(Exception ex) {
			ex.printStackTrace();
			result="500";
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/modifyJob")
	public String modifyJob() {
		String result="200";
		try {
			Map<String, Object> param=new HashMap<>();
			param.put("exampleService", exampleService);
			QuartzManager.modifyJobTime("testJob", "0/5 * * * * ?");
//			QuartzManager.modifyJobTime("testJob", "0/5 * * * * ?",param);
//			QuartzManager.modifyJobTime("testJob", "DEFAULT_TRIGGER_GROUP_NAME", "0/5 * * * * ?");
		}catch(Exception ex) {
			ex.printStackTrace();
			result="500";
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/removeJob")
	public String removeJob() {
		String result="200";
		try {
			QuartzManager.removeJob("testJob");
		}catch(Exception ex) {
			ex.printStackTrace();
			result="500";
		}
		return result;
	}
	
	/////////////////////////////////////////////
	
	@RequestMapping(value="/redisIndex")
	public String redisIndex(HttpServletRequest request) {
		List<Item> itemList=this.getRedisItem();
		request.setAttribute("itemList", itemList);
		return this.getJspPath("redis/redis");
	}
	
	@ResponseBody
	@RequestMapping(value="/addRedisItem")
	public String addRedisItem(Item item) {
		String result="200";
		try {
			item.setId(CommonUtils.getUUID());
			redisUtils.write("item:"+item.getId(), item, null);
		}catch(Exception ex) {
			ex.printStackTrace();
			result="500";
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/delRedisItem")
	public String delRedisItem(String id) {
		String result="200";
		try {
			redisUtils.delete("item:"+id);
		}catch(Exception ex) {
			ex.printStackTrace();
			result="500";
		}
		return result;
	}
	
	public List<Item> getRedisItem() {
		Set<Serializable> itemKeys=redisUtils.getKeys("item:*");
		List<Item> itemList=new ArrayList<>();
		for(Serializable key : itemKeys) {
			Item item=(Item)redisUtils.read((String)key);
			itemList.add(item);
		}
		return itemList;
	}
}
