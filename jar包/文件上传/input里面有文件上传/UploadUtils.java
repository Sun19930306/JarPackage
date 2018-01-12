package com.qf.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class UploadUtils {

	
	public static Map<String, String> handleUploadForm(HttpServletRequest request){
		Map<String, String> map =new HashMap<>();
		//处理上传
		//1.判断是否是多部分上传
		if (!ServletFileUpload.isMultipartContent(request)) {
			return null;
		}
		//以下是多部分上传
		//2.配置工厂
		DiskFileItemFactory factory =new DiskFileItemFactory();
		//配置上传过程的临时目录
		factory.setRepository(new  File("D:\\"));
		
		//3.创建上传核心类
		ServletFileUpload fileUpload =new ServletFileUpload();
		fileUpload.setFileItemFactory(factory);
		fileUpload.setProgressListener(new ProgressListener() {
			
			@Override
			public void update(long arg0, long arg1, int arg2) {
				// TODO Auto-generated method stub
				/**
				 参数1 arg0 表示下载的进度   
				 参数2：arg1 表示整个文件的大小
				 */
				System.out.println(arg0*100/arg1+"%");
			}
		});
		
		
		//4.解析请求
		try {
			List<FileItem> list = fileUpload.parseRequest(request);
			for (FileItem fileItem : list) {
				 //判断是普通表单项还是上传项
				if (fileItem.isFormField()) {
					//普通表单项
					String name =fileItem.getFieldName();
					String value =fileItem.getString("utf-8");
					map.put(name, value);
				}else {
					
					//上传项
					String filename = FilenameUtils.getName(fileItem.getName());
					File file = FileUtils.getFile(filename);
					fileItem.write(file);
					String diskDir = file.getAbsolutePath();
					//本地磁盘：c:/img/d7beb2f8-da1c-4cb7-8163-f67045168190_1.png
					System.out.println(diskDir);
					
					//path2文件名称
					String path2 = diskDir.substring(diskDir.lastIndexOf("\\")+1);
					//上传后目的是让别人访问  网络地址
					//headimg是tomcat服务器映射的虚拟路径：
					 //  conf/server.xml Host里面配置
				//<Context docBase="磁盘真实路径c:/img" path="/headimg"></Context>
					//网络：http://localhost/headimg/d7beb2f8-da1c-4cb7-8163-f67045168190_1.png
					//String netWorkPath="http://localhost/headimg/"+path2;
					//两种情况：一种是存储完整路径，一种是相对路径
					map.put("product_url",path2);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
}
