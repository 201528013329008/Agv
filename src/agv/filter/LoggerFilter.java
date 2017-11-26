package agv.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.MDC;
import agv.pageModel.User;
import agv.web.WebContant;

public class LoggerFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
        HttpSession session= req.getSession();
        MDC.put("userId_log4j",0);  
        if (session!=null){
        	try{
        		MDC.put("userId_log4j",0);
	            User user=(User)WebContant.getCurrentSessionInfo(req).getUser();
	            if (user!=null && user.getUserId()!=null && user.getUserId()>0){
	                MDC.put("userId_log4j",user.getUserId());
	            }
        	}catch(Exception e){
        		//e.printStackTrace();
        	}
        }

       chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
