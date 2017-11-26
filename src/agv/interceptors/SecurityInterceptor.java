package agv.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import agv.pageModel.SessionInfo;
import agv.util.ConfigUtil;


/**
 * 权限拦截器
 * 
 * @author China
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {

	protected Logger systemLogger = Logger.getLogger("agv");
	protected Logger userLogger = Logger.getLogger("agv.user");
	

	private List<String> excludeUrls;// 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		
		if (url.indexOf("/base/") > -1 || excludeUrls.contains(url)) {// 如果要访问的资源是不需要验证的
			return true;
		}

		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		if (sessionInfo == null) {// 如果没有登录或登录超时
			request.setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
			request.getRequestDispatcher("/content/login.jsp").forward(request, response);
			systemLogger.info("无效sessionInfo");
			return false;
		}
		if (url.indexOf("content")>-1) {
			userLogger.warn("不允许访问："+url);
			request.getRequestDispatcher("/content/index.jsp").forward(request, response);
			return false;
		}
		if (!"super".equals(sessionInfo.getUser().getUserType()) && 
		   (url.indexOf("super")>-1)) {
			userLogger.warn(sessionInfo.getUser().getUserName()+" 试图访问"+url);
			request.getRequestDispatcher("/content/index.jsp").forward(request, response);
			return false;
		}
		if (url.indexOf("dataGrid")<0){
			systemLogger.info(sessionInfo.getUser().getUserName()+" 访问"+url);
		}
		return true;
	}
}
