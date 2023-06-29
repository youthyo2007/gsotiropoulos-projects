package com.socialvideo.data.dto;



import com.socialvideo.utilities.WebUtility;

public class TagDTO {

	private String text;
	private String weight = WebUtility.nextRandomInt(5,20)+"";
	
	

	
	public TagDTO(){};
	public TagDTO(String text) {this.text = text;};
	public TagDTO(String text, Integer weight) {
		this.text = text;
		this.weight = weight.toString();
	};
	
	
	public String getText() {
		return text;
	}
	
	
	
	public String getLink() {
		return "javascript:onTagClick(\'"+this.text+"\');";
	}
	
	

	public void setText(String text) {
		this.text = text;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}


}
