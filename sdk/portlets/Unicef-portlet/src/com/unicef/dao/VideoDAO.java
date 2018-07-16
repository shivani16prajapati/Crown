package com.unicef.dao;


import java.util.List;

import com.unicef.domain.Video;

public interface VideoDAO extends GenericDAO<Video, Long> {

	public void deleteVideoByVideoId(long videoId);
	
	public Video getVideoByVideoId(long videoId);
	
	List<Video> getListofVideos(long start, int recordsSize,
			String fieldname, String orderBy);
	
}
