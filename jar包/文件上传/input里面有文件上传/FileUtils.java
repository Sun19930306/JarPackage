package com.qf.utils;

import java.io.File;
import java.util.UUID;

public class FileUtils {
	
	/**
	 * 创建上传文件
	 * @param name
	 * @return
	 */
	public static File getFile(String name) {
		File file=null ;
		//先创建目录
		File dir=new  File("D:\\img");
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		file= new File(dir,UUID.randomUUID().toString()+"_"+name);
		return file;
	}

}
