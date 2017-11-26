package agv.pageModel;

import java.util.List;

/**
 * session信息模型
 * 
 * @author China
 * 
 */
public class SessionInfo implements java.io.Serializable {

	private User user;//当前登录用户
	private String ip;
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
