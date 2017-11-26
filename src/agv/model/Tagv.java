package agv.model;
// default package

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Agv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_agv", catalog = "zyb_agv")
public class Tagv implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5201140754979612641L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;//编号
	
	@Basic
	private String name;//车辆名称
	
	@Basic
	private Boolean online;//是否在线
	
	@Basic
	private Boolean forbidden;//是否禁用
	
	@Basic
	private Integer typeId; //类型Id
	
	@Basic
	private Date createDate;//车辆创建时间
	
	@Basic
	private Integer createUserid;//车辆创建用户ID
	
	@Basic
	private String state;
	
	@Basic
	private Integer taskId;
	
	@Basic
	private Integer x;
	
	@Basic
	private Integer y;
	
	@Basic
	private Integer station1;
	
	@Basic
	private Integer Station2;
	
	@Basic
	private Integer percent;
	/**
	 * 
	 */
	public Tagv() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param online
	 * @param forbidden
	 * @param typeId
	 * @param createDate
	 * @param createUserid
	 */
	public Tagv(Integer id, String name, Boolean online, Boolean forbidden, Integer typeId, Date createDate,
			Integer createUserid) {
		super();
		this.id = id;
		this.name = name;
		this.online = online;
		this.forbidden = forbidden;
		this.typeId = typeId;
		this.createDate = createDate;
		this.createUserid = createUserid;
	}
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the online
	 */
	public Boolean getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(Boolean online) {
		this.online = online;
	}

	/**
	 * @return the forbidden
	 */
	public Boolean getForbidden() {
		return forbidden;
	}

	/**
	 * @param forbidden the forbidden to set
	 */
	public void setForbidden(Boolean forbidden) {
		this.forbidden = forbidden;
	}

	/**
	 * @return the type_id
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * @param type_id the type_id to set
	 */
	public void setTypeId(Integer type_id) {
		this.typeId = type_id;
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
	 * @return the createUserid
	 */
	public Integer getCreateUserid() {
		return createUserid;
	}

	/**
	 * @param createUserid the createUserid to set
	 */
	public void setCreateUserid(Integer createUserid) {
		this.createUserid = createUserid;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the taskId
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * @return the station1
	 */
	public Integer getStation1() {
		return station1;
	}

	/**
	 * @param station1 the station1 to set
	 */
	public void setStation1(Integer station1) {
		this.station1 = station1;
	}

	/**
	 * @return the station2
	 */
	public Integer getStation2() {
		return Station2;
	}

	/**
	 * @param station2 the station2 to set
	 */
	public void setStation2(Integer station2) {
		Station2 = station2;
	}

	/**
	 * @return the percent
	 */
	public Integer getPercent() {
		return percent;
	}

	/**
	 * @param percent the percent to set
	 */
	public void setPercent(Integer percent) {
		this.percent = percent;
	}

}