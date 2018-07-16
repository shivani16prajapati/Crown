package com.unicef.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.unicef.dao.VideoDAO;
import com.unicef.domain.Video;
import com.unicef.service.VideoService;

@Service
public class VideoServiceImpl extends GenericServiceImpl<Video, Long> implements VideoService{

	private VideoDAO videoDAO;
	
	@Inject
	public void initIdeaDAOFactory(VideoDAO videoDAO) {
		this.videoDAO = videoDAO;
		setGenericDAOFactory(videoDAO);
	}

	@Override
	public void deleteVideoByVideoId(long videoId) {
		videoDAO.deleteVideoByVideoId(videoId);
	}

	@Override
	public Video getVideoByVideoId(long videoId) {
		return videoDAO.getVideoByVideoId(videoId);
	}

	@Override
	public List<Video> getListofVideos(long start, int recordsSize,
			String fieldname, String orderBy) {
		
		return videoDAO.getListofVideos(start, recordsSize, fieldname, orderBy);
	}
	
}
