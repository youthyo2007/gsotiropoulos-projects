package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.support.PagedListHolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatorDTOBACKUP implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int PAGENUMBERGROUP = 5;
	
	private PagedListHolder<PageNumberDTO> pagedListHolder;
	
	
	public PaginatorDTOBACKUP() {}

	
	public PaginatorDTOBACKUP(Integer recordsCount) {
		init(recordsCount);
	}
	
	public PaginatorDTOBACKUP(Integer offset, Integer limit) {
		this.offset = offset;
		this.limit = limit;
	}
		
	
	private static Integer pageSize = 24;
	private Integer offset;
	private Integer limit;
	
	
	
	
	
	
	
	
	
	
	private Integer currentPage;
	private Integer totalPages;
	private Integer recordsCount;

	
	
	private List<PageNumberDTO> pageNumbersList;
	
	
	
	public void initPageNumbers() {
		pageNumbersList = new ArrayList<>();
	
		
		if((totalPages-currentPage)>PAGENUMBERGROUP) {
		for (int i=currentPage; i<currentPage+PAGENUMBERGROUP; i++) {
			PageNumberDTO currentDTO = new PageNumberDTO(i+"");
			if(i<=totalPages) {
				pageNumbersList.add(currentDTO);
			}
		}

		
		if((currentPage+PAGENUMBERGROUP)<totalPages) {
			pageNumbersList.add( new PageNumberDTO("..."));
			
		}		
		pageNumbersList.add( new PageNumberDTO(totalPages+""));
		
		}

		else if((totalPages-currentPage)<=PAGENUMBERGROUP) {
			int left_offset = currentPage-(PAGENUMBERGROUP-(totalPages-currentPage));
			
			if(left_offset>1) {
				pageNumbersList.add( new PageNumberDTO(firstPage()+""));
				pageNumbersList.add( new PageNumberDTO("..."));
			}
			

			for (int i=left_offset; i<totalPages; i++) {
				PageNumberDTO currentDTO = new PageNumberDTO(i+"");
				if(i<=totalPages) {
					pageNumbersList.add(currentDTO);
				}
			}
		}

	} 
	
	
	public RowBounds rowBounds() {
		return new RowBounds(this.offset,this.limit);
	}
	
	public boolean isLastPage() {
		return currentPage==totalPages;
	}
	
	
	public boolean isFirstPage() {
		return currentPage==1;
	}
	
	
	
	
	public RowBounds nextPage() {
		if(currentPage!=totalPages) {
			currentPage+=1;
			offset = offset+pageSize;
			
			if(currentPage==totalPages)
				limit = recordsCount;
			else
				limit = offset+pageSize;
			
		}
		
		
		initPageNumbers();
		
		return new RowBounds(this.offset,this.limit);
	}
	
	public RowBounds previousPage() {
		if(currentPage!=1) {
			currentPage-=1;
			offset = offset-pageSize;
		}

		initPageNumbers();
		
		return new RowBounds(this.offset,this.limit);
	}

	
	
	public RowBounds firstPage() {
		
		this.offset = 0;
		if(pageSize>recordsCount) {
			limit = recordsCount;
			currentPage = 1;
		}
		
		else {
			limit = pageSize;
			currentPage = 1;
		}
		
		initPageNumbers();
		
		return new RowBounds(this.offset,this.limit);
		
	}
	
	

	public RowBounds currentPage() {
		return new RowBounds(this.offset,this.limit);
	}
	
	
	
	
	
	
	
	public void init(Integer recordsCount) {
		this.recordsCount = recordsCount;
		
		
		
		this.offset = 0;
		if(pageSize>recordsCount) {
			limit = recordsCount;
			currentPage = 1;
			totalPages = 1;
					
		}
		else {
			limit = pageSize;
			currentPage = 1;
			
			if(recordsCount % pageSize ==0 )
				totalPages = recordsCount/pageSize;
			else
				totalPages = recordsCount/pageSize + 1;
			
		}
		
		
		initPageNumbers();
		
		
	}
	

	public static Integer getPageSize() {
		return pageSize;
	}
	public static void setPageSize(Integer pageSize) {
		PaginatorDTOBACKUP.pageSize = pageSize;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getRecordsCount() {
		return recordsCount;
	}
	public void setRecordsCount(Integer recordsCount) {
		this.recordsCount = recordsCount;
	}


	public void goToPage(String pageid) {
		this.currentPage = Integer.parseInt(pageid);
		offset = pageSize*(currentPage-1);
		
		if(currentPage==totalPages)
			limit = recordsCount;
		else
			limit = offset+pageSize;
		

		initPageNumbers();
	}


	public List<PageNumberDTO> getPageNumbersList() {
		return pageNumbersList;
	}


	public void setPageNumbersList(List<PageNumberDTO> pageNumbersList) {
		this.pageNumbersList = pageNumbersList;
	}


	public static int getPagenumbergroup() {
		return PAGENUMBERGROUP;
	}

	

}
