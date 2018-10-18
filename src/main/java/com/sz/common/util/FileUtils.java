package com.sz.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	/**
	 * 创建文件夹
	 * @param path
	 */
	public static void createFolder(String path){
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 写入文件
	 * @param filePath
	 * @param str
	 * @throws IOException
	 */
	public static void writeFile(String filePath,StringBuffer str) throws IOException{
		File file=new File(filePath);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream out=new FileOutputStream(filePath,true);
		out.write(str.toString().getBytes());
		out.flush();
		out.close();
	}
	
	/**
	 * 读取文件
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer readFile(String filePath) throws Exception{
		File file=new File(filePath);
		if(!file.exists()){
			throw new Exception("未找到文件");
		}
		StringBuffer sb=new StringBuffer();
		BufferedReader br=new BufferedReader(new FileReader(file));
		String temp=br.readLine();
		while(temp!=null){
			sb.append(temp);
			temp=br.readLine();
		}
		br.close();
		return sb;
	}
	
	/**
	 * 复制文件
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(String src,String dest) throws IOException{
		FileInputStream in=new FileInputStream(src);
		File file=new File(dest);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream out=new FileOutputStream(file);
		byte[] buffer=new byte[1024];
		int byteread=0;
		while((byteread=in.read(buffer))!=-1){
			out.write(buffer, 0, byteread);
		}
		in.close();
		out.close();
	}
	
	
	public static void main(String[] args) {
		try{
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}
}
