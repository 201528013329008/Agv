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
 * Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_task", catalog = "zyb_agv")
public class Ttask implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4867721321784423120L;

	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Basic
	private String name;
	
	@Basic
	private Integer startPos;
	
	@Basic
	private Integer endPos;
	
	@Basic
	private Integer priority;
	
	@Basic
	private Date scheduleTime;
	
	@Basic
	private Integer typeId;
	
	@Basic
	private String state;
	
	@Basic 
	private Boolean ifRead;
	
	@Basic
	private Integer agvId;
	
	@Basic
	private Integer length;
	
	@Basic
	private Integer width;
	
	@Basic
	private Integer height;
	
	@Basic
	private Integer weight;
	
	@Basic
	private Integer agvTypeId;

	@Basic
	private Date createDate;
	
	@Basic
	private Integer createUserid;
		 
	@Basic
	private Date startDate;
	
	@Basic
	private Date startLoadDate;
	
	@Basic
	private Date endLoadDate;
	
	@Basic
	private Date startUnloadDate;
	
	@Basic
	private Date endUnloadDate;
	
	@Basic
	private Date endDate;
	
	@Basic
	private Integer shelf1Id;
	
	@Basic
	private Integer shelf1Row;
	
	@Basic
	private Integer shelf1Column;
	
	@Basic
	private Integer shelf2Id;
	
	@Basic
	private Integer shelf2Row;
	
	@Basic
	private Integer shelf2Column;


	/**
	 * @param id
	 * @param name
	 * @param startPos
	 * @param endPos
	 * @param priority
	 * @param scheduleTime
	 * @param typeId
	 * @param state
	 * @param agvId
	 * @param length
	 * @param width
	 * @param height
	 * @param weight
	 * @param agvTypeId
	 * @param createDate
	 * @param createUserid
	 */
	public Ttask(Integer id, String name, Integer startPos, Integer endPos, Integer priority, Date scheduleTime,
			Integer typeId, String state, Integer agvId, Integer length, Integer width, Integer height, Integer weight,
			Integer agvTypeId, Date createDate, Integer createUserid) {
		super();
		this.id = id;
		this.name = name;
		this.startPos = startPos;
		this.endPos = endPos;
		this.priority = priority;
		this.scheduleTime = scheduleTime;
		this.typeId = typeId;
		this.state = state;
		this.agvId = agvId;
		this.length = length;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.agvTypeId = agvTypeId;
		this.createDate = createDate;
		this.createUserid = createUserid;
	}

	public Ttask() {
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
	 * @return the startPos
	 */
	public Integer getStartPos() {
		return startPos;
	}

	/**
	 * @param startPos the startPos to set
	 */
	public void setStartPos(Integer startPos) {
		this.startPos = startPos;
	}

	/**
	 * @return the endPos
	 */
	public Integer getEndPos() {
		return endPos;
	}

	/**
	 * @param endPos the endPos to set
	 */
	public void setEndPos(Integer endPos) {
		this.endPos = endPos;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the scheduleTime
	 */
	public Date getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
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
	 * @return the agvId
	 */
	public Integer getAgvId() {
		return agvId;
	}

	/**
	 * @param agvId the agvId to set
	 */
	public void setAgvId(Integer agvId) {
		this.agvId = agvId;
	}

	/**
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
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
	 * @return the agvTypeId
	 */
	public Integer getAgvTypeId() {
		return agvTypeId;
	}

	/**
	 * @param agvTypeId the agvTypeId to set
	 */
	public void setAgvTypeId(Integer agvTypeId) {
		this.agvTypeId = agvTypeId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startLoadDate
	 */
	public Date getStartLoadDate() {
		return startLoadDate;
	}

	/**
	 * @param startLoadDate the startLoadDate to set
	 */
	public void setStartLoadDate(Date startLoadDate) {
		this.startLoadDate = startLoadDate;
	}

	/**
	 * @return the endLoadDate
	 */
	public Date getEndLoadDate() {
		return endLoadDate;
	}

	/**
	 * @param endLoadDate the endLoadDate to set
	 */
	public void setEndLoadDate(Date endLoadDate) {
		this.endLoadDate = endLoadDate;
	}

	/**
	 * @return the startUnloadDate
	 */
	public Date getStartUnloadDate() {
		return startUnloadDate;
	}

	/**
	 * @param startUnloadDate the startUnloadDate to set
	 */
	public void setStartUnloadDate(Date startUnloadDate) {
		this.startUnloadDate = startUnloadDate;
	}

	/**
	 * @return the endUnloadDate
	 */
	public Date getEndUnloadDate() {
		return endUnloadDate;
	}

	/**
	 * @param endUnloadDate the endUnloadDate to set
	 */
	public void setEndUnloadDate(Date endUnloadDate) {
		this.endUnloadDate = endUnloadDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the ifRead
	 */
	public Boolean getIfRead() {
		return ifRead;
	}

	/**
	 * @param ifRead the ifRead to set
	 */
	public void setIfRead(Boolean ifRead) {
		this.ifRead = ifRead;
	}

	/**
	 * @return the shelf1Id
	 */
	public Integer getShelf1Id() {
		return shelf1Id;
	}

	/**
	 * @param shelf1Id the shelf1Id to set
	 */
	public void setShelf1Id(Integer shelf1Id) {
		this.shelf1Id = shelf1Id;
	}

	/**
	 * @return the shelf1Row
	 */
	public Integer getShelf1Row() {
		return shelf1Row;
	}

	/**
	 * @param shelf1Row the shelf1Row to set
	 */
	public void setShelf1Row(Integer shelf1Row) {
		this.shelf1Row = shelf1Row;
	}

	/**
	 * @return the shelf1Column
	 */
	public Integer getShelf1Column() {
		return shelf1Column;
	}

	/**
	 * @param shelf1Column the shelf1Column to set
	 */
	public void setShelf1Column(Integer shelf1Column) {
		this.shelf1Column = shelf1Column;
	}

	/**
	 * @return the shelf2Id
	 */
	public Integer getShelf2Id() {
		return shelf2Id;
	}

	/**
	 * @param shelf2Id the shelf2Id to set
	 */
	public void setShelf2Id(Integer shelf2Id) {
		this.shelf2Id = shelf2Id;
	}

	/**
	 * @return the shelf2Row
	 */
	public Integer getShelf2Row() {
		return shelf2Row;
	}

	/**
	 * @param shelf2Row the shelf2Row to set
	 */
	public void setShelf2Row(Integer shelf2Row) {
		this.shelf2Row = shelf2Row;
	}

	/**
	 * @return the shelf2Column
	 */
	public Integer getShelf2Column() {
		return shelf2Column;
	}

	/**
	 * @param shelf2Column the shelf2Column to set
	 */
	public void setShelf2Column(Integer shelf2Column) {
		this.shelf2Column = shelf2Column;
	}


}