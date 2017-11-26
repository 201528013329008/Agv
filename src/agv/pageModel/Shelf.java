package agv.pageModel;
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
 * Shelf entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Shelf{
	

	private Integer id;//编号
	
	private String sn;
	
	private Integer height;
	 
	private Integer width;
	 
	private Integer boxHeight;
	 
	private Integer boxWidth;
	 
	private Integer rowNum;
	 
	private Integer columnNum;
	 
	private Integer thickness;
	 
	private Integer baseHeight;
	
	private Boolean occupied;
	 
	private Date createDate;
	 
	private Integer createUserid;
	
	private User user;
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
	 * @return the boxHeight
	 */
	public Integer getBoxHeight() {
		return boxHeight;
	}
	/**
	 * @param boxHeight the boxHeight to set
	 */
	public void setBoxHeight(Integer boxHeight) {
		this.boxHeight = boxHeight;
	}
	/**
	 * @return the boxWidth
	 */
	public Integer getBoxWidth() {
		return boxWidth;
	}
	/**
	 * @param boxWidth the boxWidth to set
	 */
	public void setBoxWidth(Integer boxWidth) {
		this.boxWidth = boxWidth;
	}
	/**
	 * @return the rowNum
	 */
	public Integer getRowNum() {
		return rowNum;
	}
	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	/**
	 * @return the columnNum
	 */
	public Integer getColumnNum() {
		return columnNum;
	}
	/**
	 * @param columnNum the columnNum to set
	 */
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}
	/**
	 * @return the thickness
	 */
	public Integer getThickness() {
		return thickness;
	}
	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}
	/**
	 * @return the baseHeight
	 */
	public Integer getBaseHeight() {
		return baseHeight;
	}
	/**
	 * @param baseHeight the baseHeight to set
	 */
	public void setBaseHeight(Integer baseHeight) {
		this.baseHeight = baseHeight;
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
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	/**
	 * @return the occupied
	 */
	public Boolean getOccupied() {
		return occupied;
	}
	/**
	 * @param occupied the occupied to set
	 */
	public void setOccupied(Boolean occupied) {
		this.occupied = occupied;
	}
}