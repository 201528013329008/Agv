package agv.pageModel;

import java.util.ArrayList;
import java.util.List;


/**
 * jQgrid
 * @author China
 *
 */
public class JqGrid {
	private boolean success;
	private Long totalRows = 0L;//总数
	private int curPage = 1;//当前页
	private int pageSize = 10;//分页大小
	private List data = new ArrayList();//数据
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}

	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	
	
}
