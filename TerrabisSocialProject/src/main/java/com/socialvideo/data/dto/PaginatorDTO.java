package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.support.PagedListHolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private PagedListHolder<Integer> pagedListHolder;
	private List<PageNumberDTO> pageNumbersList;
	private Integer recordsCount;
	
	
	
	public PaginatorDTO() {}

	
	public PaginatorDTO(Integer recordsCount) {
		this.recordsCount = recordsCount;
		init(recordsCount);
		initPageNumbers();
		
	}


	public PaginatorDTO(Integer recordsCount, Integer pageSize) {
		this.recordsCount = recordsCount;
		this.pageSize = pageSize;
		init(recordsCount);
		initPageNumbers();
	}
	


	private Integer pageSize = 12;

	
	
	public boolean isLastPage() {
		return pagedListHolder.isLastPage();
	}
	
	
	
	public boolean isFirstPage() {
		return pagedListHolder.isFirstPage();
		
	}
	
	
	
	public Integer getCurrentPage() {
		return pagedListHolder.getPage();
	}
	

	public Integer getTotalPages() {
		return pagedListHolder.getPageCount();
	}

	
	public RowBounds firstPage() {
		pagedListHolder.setPage(0);
		initPageNumbers();
		return new RowBounds(0,pageSize);
		
	}
	
	
	
	public RowBounds currentPage() {
		return new RowBounds(getOffset(),getLimit());
	}
	
	
	
	
	public RowBounds goToPage(int page) {
		pagedListHolder.setPage(page-1);
		initPageNumbers();
		return new RowBounds((page-1)*pageSize,page*pageSize);
	}
	
	public RowBounds nextPage() {
		pagedListHolder.nextPage();
		initPageNumbers();
		return new RowBounds(getOffset(),getLimit());
	}
	
	
	public int getOffset() {
		int offset =  (pagedListHolder.getPage())*pageSize;
		System.out.println("offset:"+offset);
		return offset;
	}
	
	public int getLimit() {
		//int limit =  (pagedListHolder.getPage()+1)*pageSize;
		//System.out.println("limit:"+limit);
		int limit = pageSize;
		return limit;
	}
	
	
	private void initPageNumbers() {
		pageNumbersList = new ArrayList<>();
		
		
		/*		if(!pagedListHolder.isFirstPage()) {
			System.out.println(String.valueOf(pagedListHolder.getPage()-1));
			
		}
			*/
				
		if(pagedListHolder.getFirstLinkedPage()>1) {
			pageNumbersList.add(new PageNumberDTO("1"));
			pageNumbersList.add(new PageNumberDTO("AFTER_ONE"));
		}
		
		for (int i=pagedListHolder.getFirstLinkedPage(); i<=pagedListHolder.getLastLinkedPage(); i++) {
			pageNumbersList.add(new PageNumberDTO(i+1+""));
		}

		
		if(pagedListHolder.getLastLinkedPage()< pagedListHolder.getPageCount() - 2) {
			pageNumbersList.add(new PageNumberDTO("DOTS"));
		}
		
		if(pagedListHolder.getLastLinkedPage()< pagedListHolder.getPageCount() - 1) {
			pageNumbersList.add(new PageNumberDTO(pagedListHolder.getPageCount()+""));
		}
		
		
		/*		if(!pagedListHolder.isLastPage()) {
			System.out.println(pagedListHolder.getPage());
		}*/
		

	}
	
	
	private void init(int recordsCount) {


		
			List<Integer> resultsList = new ArrayList<>();
			
			for(int i=1; i<=recordsCount; i++) {
				resultsList.add(new Integer(i));
			}
			
			this.pagedListHolder = new PagedListHolder<>(resultsList);
			pagedListHolder.setPageSize(pageSize);
	}


	public List<PageNumberDTO> getPageNumbersList() {
		return pageNumbersList;
	}


	public void setPageNumbersList(List<PageNumberDTO> pageNumbersList) {
		this.pageNumbersList = pageNumbersList;
	}


	public PagedListHolder<Integer> getPagedListHolder() {
		return pagedListHolder;
	}


	public void setPagedListHolder(PagedListHolder<Integer> pagedListHolder) {
		this.pagedListHolder = pagedListHolder;
	}


	public Integer getRecordsCount() {
		return recordsCount;
	}


	public void setRecordsCount(Integer recordsCount) {
		this.recordsCount = recordsCount;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	

}
