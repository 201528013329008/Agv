package agv.model;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "tb_user", catalog = "zyb_agv")
@DynamicInsert(true)
@DynamicUpdate(true)
class Tuser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1027559646872771917L;
	private Integer userId;
	private String userName;
	private String loginName;
	private String userType;
	private Integer userStatus;
	private String userPassword;
	private Date createDate;
	private Integer createUserid;

	// Constructors

	/** default constructor */
	public Tuser() {
	}

	/** minimal constructor */
	public Tuser(String loginName, String userPassword) {
		this.loginName = loginName;
		this.userPassword = userPassword;
	}

	/** full constructor */
	public Tuser(String userName, String loginName, String userType,
			Integer userStatus, String userPassword, Timestamp createDate,
			Integer createUserid) {
		this.userName = userName;
		this.loginName = loginName;
		this.userType = userType;
		this.userStatus = userStatus;
		this.userPassword = userPassword;
		this.createDate = createDate;
		this.createUserid = createUserid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", length = 40)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "login_name", nullable = false, length = 40)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "user_type", length = 10)
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "user_status")
	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "user_password", nullable = false, length = 200)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "create_date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_userid")
	public Integer getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(Integer createUserid) {
		this.createUserid = createUserid;
	}

}