package agv.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_agv_type", catalog = "zyb_agv")
public class TagvType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4568329168669398413L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;//编号
	
	@Basic
	private String name;//类型名称
	
	@Basic
	private Integer length;
	
	@Basic
	private Integer width;
	
	@Basic
	private Integer height;
	
	@Basic
	private Integer weight;
	
	@Basic
	private Float maxVel;
	
	@Basic
	private Float turnVel;

	
	/**
	 * 
	 */
	public TagvType() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param length
	 * @param width
	 * @param height
	 * @param weight
	 * @param maxVel
	 * @param turnVel
	 */
	public TagvType(Integer id, String name, Integer length, Integer width, Integer height, Integer weight,
			Float maxVel, Float turnVel) {
		super();
		this.id = id;
		this.name = name;
		this.length = length;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.maxVel = maxVel;
		this.turnVel = turnVel;
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
	 * @return the maxVel
	 */
	public Float getMaxVel() {
		return maxVel;
	}

	/**
	 * @param maxVel the maxVel to set
	 */
	public void setMaxVel(Float maxVel) {
		this.maxVel = maxVel;
	}

	/**
	 * @return the turnVel
	 */
	public Float getTurnVel() {
		return turnVel;
	}

	/**
	 * @param turnVel the turnVel to set
	 */
	public void setTurnVel(Float turnVel) {
		this.turnVel = turnVel;
	}
	
}
