package agv.pageModel;

import java.io.Serializable;
import java.util.Date;

import agv.model.Tuser;

/**
 * 用户页面
 * 
 * @author China
 * 
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1436274466975670138L;
	
	private Integer userId;
	private String userName;
	private String loginName;
	private String userType;
	private Integer userStatus;
	private String userPassword;
	private Date createDate;
	private Integer createUserid;
	private String sel;
	private String search;
	
	public String getSel() {
		return sel;
	}
	public void setSel(String sel) {
		this.sel = sel;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(Integer createUserid) {
		this.createUserid = createUserid;
	}

	
}
