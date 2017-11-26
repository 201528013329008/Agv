package agv.pageModel;

public class Msg {
	private String order;
	private Integer id;
	
	public Msg(String msgStr) throws Exception{
		int index = msgStr.indexOf(":");
		this.order = msgStr.substring(0, index);
		if (index<msgStr.length()){
			this.id = Integer.parseInt(msgStr.substring(index+1));
		}
	}
	
	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
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
	
	public String getMsg(){
		return getOrder()+":"+getId();
	}

}
