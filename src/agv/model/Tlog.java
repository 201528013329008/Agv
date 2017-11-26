package agv.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_log", catalog = "zyb_agv")
public class Tlog implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3813977418570374954L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;//编号
	
	@Basic
	private Integer userId;
	
	@Column(name = "class")
	private String className;
	
	@Basic
	private String method;
	
	@Basic
	private Date createDate;
	
	@Basic
	private String logLevel;
	
	@Basic
	private String msg;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}

	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
