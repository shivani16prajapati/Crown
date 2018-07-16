package com.unicef.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "video")
public class Video {
	
	@Id
	@Column(name = "video_id")
	@GeneratedValue
	private Long videoId;
	
	@Column(name = "video_name", nullable=false)
	private String videoName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_date")
	private Date expireDate;
	
	@Column(name = "video_url", nullable=false)
	private String videoUrl;
	
	@Column(name = "video_image", nullable=false)
	private long videoImage;
	
	public long getVideoImage() {
		return videoImage;
	}

	public void setVideoImage(long videoImage) {
		this.videoImage = videoImage;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getFeeOrderPlacement() {
		return feeOrderPlacement;
	}

	public void setFeeOrderPlacement(int feeOrderPlacement) {
		this.feeOrderPlacement = feeOrderPlacement;
	}

	@Column(name = "feed_order", nullable=false)
	private int feeOrderPlacement;
	

}
