package agv.pageModel;


import java.util.Date;

public class Task {
	
	public enum State {
		undefined("undefined"),
		waiting("waiting"),heading("heading"),loading("loading"),
		loadingFinish("loadingFinish"),moving("moving"),unloading("unloading"),
		unloadingFinish("unloadingFinish"),cancelled("cancelled"),finished("finished");
		
		private String name;
		private State(String name){
			this.name = name;
		}
		
		public static Boolean hasName(String name) {  
	        for (State s : State.values()) {  
	            if (s.name.equals(name)) {  
	                return true;  
	            }  
	        }  
	        return false;  
	    }  
		
		public String getName(){
			return this.name;
		}
	}
	
	private Integer id;
	
	private String name;
	
	private Integer startPos;
	
	private Integer endPos;
	
	private Integer priority;
	
	private Date scheduleTime;
	
	private Integer typeId;
	
	private String state;
	
	private Boolean ifRead;
	
	private Integer agvId;
	
	private Integer length;
	
	private Integer width;
	
	private Integer height;
	
	private Integer weight;
	
	private Integer agvTypeId;

	private Date createDate;
	
	private Integer createUserid;
	
	private Date startDate;
	
	private Date startLoadDate;
	
	private Date endLoadDate;
	
	private Date startUnloadDate;
	
	private Date endUnloadDate;
	
	private Date endDate;
	
	private Integer shelf1Id;
	
	private Integer shelf1Row;
	
	private Integer shelf1Column;
	
	private Integer shelf2Id;
	
	private Integer shelf2Row;
	
	private Integer shelf2Column;
	
	//----------------------------
	
	private Agv agv;
	
	private AgvType agvType;
		
	private User user;
	
	private Shelf shelf1;
	
	private Shelf shelf2;
	
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
	public void setState(String state){
		if (state==null || "".equalsIgnoreCase(state)){
			state = State.undefined.getName();
		}
		if (State.hasName(state)){
			this.state = state;
		}else{
			this.state = "非法状态";
		}
		this.state = state;
	}
	
	public void setState(State state){
		try {
			setState(state.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * @return the agv
	 */
	public Agv getAgv() {
		return agv;
	}

	/**
	 * @param agv the agv to set
	 */
	public void setAgv(Agv agv) {
		this.agv = agv;
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

	/**
	 * @return the shelf1
	 */
	public Shelf getShelf1() {
		return shelf1;
	}

	/**
	 * @param shelf1 the shelf1 to set
	 */
	public void setShelf1(Shelf shelf1) {
		this.shelf1 = shelf1;
	}

	/**
	 * @return the shelf2
	 */
	public Shelf getShelf2() {
		return shelf2;
	}

	/**
	 * @param shelf2 the shelf2 to set
	 */
	public void setShelf2(Shelf shelf2) {
		this.shelf2 = shelf2;
	}
}
