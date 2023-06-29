package com.socialvideo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "communities")
public class CommunityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, name="userid_fk")
	private Long userid;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Integer privacy;
	
	
}


