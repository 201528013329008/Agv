package agv.pageModel;

/**
 * EasyUI 分页帮助类
 * 
 * @author China
 * 
 */
public class PageHelper {

	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段
	private String sidx;//jqgrid 排序字段
	private String sord;//jqgrid 排序关键字asc/desc
	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	private String order;// asc/desc
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
