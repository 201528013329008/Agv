package agv.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import agv.pageModel.SessionInfo;
import agv.pageModel.User;
/**
 * web常用的信息
 * @author gzuni
 *
 */
public final class WebContant implements java.io.Serializable{

	/** 定义登录的用户 名称 */
	public static String SESSION_INFO = "sessionInfo";
	/** 文件的类型 */
	public static final String[] SAFE_FILE_SUFFIX = {".jpg",".jpeg",".gif",".png",
			".zip",".rar",".doc",".docx",".xls",".xlsx",
			".pdf",".txt",".ppt",".pptx"};
	public static final String FILES_PATH = "/files/";//文件的保存路径,如果下载时提示无权限，请设置此文件夹的安全属性，增加everyone用户的读写权限
	public static final String DOWNLOAD_PATH = "/download/";//导出excel路径
	public HttpSession httpSession = null;
	
	/**
	 * 获取当前登录的用户
	 * 
	 * @return 当前的用户
	 */
	public static SessionInfo getCurrentSessionInfo(HttpServletRequest req) {
		return (SessionInfo) req.getSession().getAttribute(SESSION_INFO);
	}

	/**
	 * 保存当前的用户
	 * 
	 * @param req
	 * @param sessionInfo
	 */
	public static void SetCurrentSessionInfo(HttpServletRequest req,
			SessionInfo sessionInfo) {
		req.getSession().setAttribute(SESSION_INFO, sessionInfo);
	}

	/**
	 * 获取用户
	 * 
	 * @param req
	 * @return
	 */
	public static User getCurrentUser(HttpServletRequest req) {
		SessionInfo sessionInfo = getCurrentSessionInfo(req);
		if (sessionInfo != null) {
			return sessionInfo.getUser();
		}
		return null;
	}


	/**
	 * 保存session
	 * 
	 * @param req
	 * @param key
	 * @param value
	 */
	public static void setSession(HttpServletRequest req, String key,
			Object obj) {
		req.getSession().setAttribute(key, obj);
	}

	/**
	 * 获取session
	 * 
	 * @param req
	 * @param key
	 * @return
	 */
	public static Object getSession(HttpServletRequest req, String key) {
		return req.getSession().getAttribute(key);
	}

	
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	/**
	 * 判断某个字符串是否在数组中
	 * @param substring
	 * @param source
	 * @return
	 */
	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			//System.out.println(substring.equals(aSource) + "" + aSource);
			if (aSource.equals(substring.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取操作的路径
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request){
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		System.out.println("操作路径:" + url);
		return  url;
	}
	

	/**
	 * 判断某个值是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if(str!=null && !"".equals(str)){
			return true;
		}
		return false;
	}
	/**
	 * 判断文件是否安全的类型
	 * @param suffix
	 * @return
	 */
	public static boolean isSafeFile(String suffix){
		for(String s:SAFE_FILE_SUFFIX){
			if(s.toLowerCase().equals(suffix.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}
