package com.ucu.edu.common.dto;

import java.util.List;

public class RefListWrapper {
	
	List<String> links;
	List<Long> ids;
	
	
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
		
}
