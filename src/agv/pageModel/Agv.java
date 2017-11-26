package agv.pageModel;

import java.util.Date;


public class Agv {
	private Integer id;//编号
	
	private String name;//车辆名称
	
	private Boolean online;//是否在线
	
	private Boolean forbidden;//是否禁用
	
	private Integer typeId; //类型Id
	
	private Date createDate;//车辆创建时间
	
	private Integer createUserid;//车辆创建用户ID

	private User user;
		
	private AgvType agvType;
	
	private String state;
	
	private Integer taskId;
	
	private Integer x;
	
	private Integer y;
	
	private Integer station1;
	
	private Integer Station2;
	
	private Integer percent;
	
	private Task task;
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
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the agvType
	 */
	public AgvType getAgvType() {
		return agvType;
	}

	/**
	 * @param agvType the agvType to set
	 */
	public void setAgvType(AgvType agvType) {
		this.agvType = agvType;
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

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	
	
	
}
