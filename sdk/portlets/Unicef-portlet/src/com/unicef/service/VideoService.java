package com.unicef.service;


import java.util.List;

import com.unicef.domain.Video;

public interface VideoService extends GenericService<Video, Long>{
	
	public void deleteVideoByVideoId(long videoId);
	
	public Video getVideoByVideoId(long videoId);
	
	public List<Video> getListofVideos(long start, int recordsSize,
			String fieldname, String orderBy);

}
