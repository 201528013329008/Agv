package agv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import agv.pageModel.SateJson;
import agv.pageModel.SessionInfo;
import agv.web.WebContant;

@Controller
@RequestMapping("/files")
public class UploadController {

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	@ResponseBody
	public SateJson upload(HttpServletRequest request) {
		System.out.println("----------上传------");
		SateJson j = new SateJson();
		j.setMsg("上传失败！");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// SimpleDateFormat dateformat = new
		// SimpleDateFormat("yyyyMMddHH24MISS");
		// String datestr = dateformat.format(new Date());
		/** 构建附件保存的目录 **/
		String logoPathDir = WebContant.FILES_PATH;
		/** 得到附件保存目录的真实路径 **/
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		/** 根据真实路径创建目录 **/
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流 **/
		MultipartFile multipartFile = multipartRequest.getFile("file");
		/** 获取文件的后缀 **/
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		System.out.println(suffix);
		if (!WebContant.isSafeFile(suffix)) {
			j.setSuccess(false);
			j.setMsg("文件类型非法！");
			return j;

		}
		// /**使用UUID生成文件名称**/
		String realName = multipartFile.getOriginalFilename();
		String saveName = UUID.randomUUID().toString() + suffix;
		System.out.println(realName);
		System.out.println(saveName);
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = logoRealPathDir + File.separator + saveName;
		File file = new File(fileName);
		try {
			multipartFile.transferTo(file);
			j.setObj(saveName);
			j.setSuccess(true);
			j.setMsg("上传成功！");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return j;

	}

	@RequestMapping("/download")
	@ResponseBody
	public String download(String fileName, HttpServletRequest req,
			HttpServletResponse rsp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		System.out.println("filename:" + fileName);
		String downLoadPath = req.getSession().getServletContext()
				.getRealPath("/")
				+ WebContant.FILES_PATH +  fileName;
		System.out.println(downLoadPath);
		long fileLength = new File(downLoadPath).length();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String outName = sdf.format(date);
		String suffix = fileName.substring(
				fileName.lastIndexOf("."));
		rsp.setContentType("text/plain");// 设置为下载application/x-download
		rsp.setHeader("Connection", "keep-alive");
		rsp.setHeader("Content-disposition", "attachment; filename="
				+ outName+suffix);
		rsp.setHeader("Content-Length", String.valueOf(fileLength));

		try {
			File file = new File(downLoadPath);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = rsp.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
			os.flush();
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/export")
	@ResponseBody
	public String  export(String downName,HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException { 
		SessionInfo sessionInfo = WebContant.getCurrentSessionInfo(req);
		req.setCharacterEncoding("UTF-8");
		String downLoadPath = req.getSession().getServletContext().getRealPath("/") 
				+ WebContant.DOWNLOAD_PATH + "\\";
		System.out.println("downloadname:" + downName);
		downName = downLoadPath+downName;
		long fileLength = new File(downName).length();
		if(downLoadPath.contains(".xls")){
			rsp.setContentType("application/vnd.ms-excel");//设置为下载application/x-download
		}else if(downLoadPath.contains(".xlsx")){
			rsp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		}
		String saveName = downName.replace(downLoadPath, "");//new String(downName.replace(downLoadPath, "").getBytes("utf-8"), "ISO8859-1");
		System.out.println(saveName);
		rsp.setHeader("Connection","keep-alive");
		rsp.setHeader("Content-disposition", "attachment; filename=" + new String(saveName.getBytes("utf-8"), "ISO8859-1")); //new String(filename.getBytes("utf-8"), "ISO8859-1"));
		rsp.setHeader("Content-Length", String.valueOf(fileLength));
 
		try {  
            File file=new File(downName); 
            InputStream inputStream=new FileInputStream(file);  
            OutputStream os=rsp.getOutputStream(); 
            byte[] b=new byte[1024];  
            int length;  
            while((length=inputStream.read(b))>0){  
                os.write(b,0,length);  
            }
            inputStream.close();
            os.flush();
            os.close();
            
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return null;
	}
}