package com.unicef.portlet.video;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.unicef.domain.Solution;
import com.unicef.domain.Video;
import com.unicef.service.IdeaService;
import com.unicef.service.VideoService;
import com.unicef.util.IdeaConstant;
import com.unicef.util.VideoConstant;


/**
 * Portlet implementation class VideoController
 */

@Controller
@RequestMapping("VIEW")
public class VideoController{
	
	private static final Log log = LogFactoryUtil.getLog(VideoController.class);
	
	private static final String UPLOAD_VIDEO = "upload_video";
	private static final String VIEW_LIST_VIDEOS = "videoList";
	private static final String VIDEO_RESPONSE_JSP= "videoResponse";
	private static final String VIEW_VIDEO_JSP= "viewVideo";
	
	@Autowired
	private VideoService videoService;

	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest,
			RenderResponse renderResponse){
		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<Video> videoList = new ArrayList<Video>();
		
		String urlName = themeDisplay.getLayout().getFriendlyURL();
		
		if(urlName.equalsIgnoreCase("/submit-video")){
			model.addAttribute("action", "save");
			return UPLOAD_VIDEO;
		}else{
			videoList = videoService.findAll();
			model.addAttribute("noOfVideo", videoList.size());
			return VIEW_LIST_VIDEOS;
		}
		
	}
	
	

	@RenderMapping(params="view=updateVideo")
	public String updateVideo(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				long videoId = ParamUtil.getLong(renderRequest, "videoId");
				
				Video video = null;
				if(videoId > 0){
					try{
						video = videoService.find(videoId);
						model.addAttribute("videoId", videoId);
						model.addAttribute("action", "edit");
						model.addAttribute("videoName", video.getVideoName());
						model.addAttribute("videoExpireDate", video.getExpireDate());
						model.addAttribute("videoUrl", video.getVideoUrl());
						model.addAttribute("videoFeedOrder", video.getFeeOrderPlacement());
						
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				return UPLOAD_VIDEO;
				
	
	}
				
	
	@ResourceMapping(value="deleteVideoURL")
	public void deleteVideoURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
			   HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			  
			   long videoId = ParamUtil.getLong(request, "videoId");
			   try{
			   videoService.deleteVideoByVideoId(videoId);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
			   model.addAttribute("noOfVideo", videoService.findAll().size());
			   
		
	}
	
	@ResourceMapping(value="getVideoDataURL")
	public String getVideoDataURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
				
				HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
				
				PortletPreferences preferences = resourceRequest.getPreferences();
				
				int recordsSize = Integer.parseInt(request.getParameter("recordsPerPage"));
				int pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String fieldname = request.getParameter("fieldName");
				String orderBy = request.getParameter("orderBy");

				try{
					long start = (long) (pageNo * recordsSize) - recordsSize;
					List<Video> videos = videoService.getListofVideos(start, recordsSize, fieldname, orderBy);
					log.info("start : "+start+", recordsSize : "+recordsSize+" , fieldname : "+fieldname+" , orderBy : "+orderBy);
					if(videos.size() > 0){
						model.addAttribute("videoList", videos);
					}
				}catch(Exception e){
					e.printStackTrace();
					log.error("Error in Get Video Data URl Method"+e.getMessage());
				}
				return VIDEO_RESPONSE_JSP;
				
	}
	
	
	@ActionMapping(params = "action=uploadVideo")
	public void uploadVideo(ActionRequest actionRequest, ActionResponse actionResponse, Model model) {
		
		/*HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));*/
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);
		
		if (!themeDisplay.isSignedIn()) {
			SessionErrors.add(actionRequest, VideoConstant.ERROR_VIDEO_LOG_IN);
			return;
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
		
		String videoName = ParamUtil.getString(uploadRequest, "videoName",
				StringPool.BLANK);
		String videoUrl = ParamUtil.getString(uploadRequest, "videoUrl",
				StringPool.BLANK);
		int videofeed = ParamUtil.getInteger(uploadRequest, "videofeed");
		long videoId = ParamUtil.getLong(uploadRequest, "hiddenVideoId");
		String expireDate = ParamUtil.getString(uploadRequest, "expireDate",
				StringPool.BLANK);
		String action = ParamUtil.getString(uploadRequest, "hiddenVideoAction",
				StringPool.BLANK);
		
			Date ed = null;
			try {
				ed = simpleDateFormat.parse(expireDate);
			} catch (Exception e1) {
				ed = new Date();
				e1.printStackTrace();
			}
		
		log.info("action "+action);
		
		Video video = null;
		if(action.equalsIgnoreCase("edit")){
			log.info("videoId "+videoId);
			video = videoService.getVideoByVideoId(videoId);
			video.setExpireDate(ed);
			video.setVideoName(videoName);
			video.setVideoUrl(videoUrl);
			video.setFeeOrderPlacement(videofeed);
			
			DLFileEntry fileEntry1 = null;
			try {
				fileEntry1 = DLFileEntryLocalServiceUtil.fetchDLFileEntry(video.getVideoImage());
				if(Validator.isNotNull(fileEntry1)){
					DLFileEntryLocalServiceUtil.deleteDLFileEntry(fileEntry1);
				}
					FileEntry fileEntry = null;
					try {
						fileEntry = saveImage(uploadRequest, themeDisplay,
								"videfileUpload");
						video.setVideoImage(fileEntry.getFileEntryId());
					} catch (PortalException | SystemException e) {
						SessionErrors.add(actionRequest, VideoConstant.ERROR_IN_VIDEO_MESSAGE_CREATE);
						e.printStackTrace();
					}
				
			} catch (SystemException e) {
				SessionErrors.add(actionRequest, VideoConstant.ERROR_IN_VIDEO_MESSAGE_CREATE);
				e.printStackTrace();
			}
			videoService.update(video);
			SessionMessages.add(actionRequest, VideoConstant.SUCCESS_VIDEO_MESSAGE_UPDATE);
			
		}else{
			video = new Video();
			video.setExpireDate(ed);
			video.setVideoName(videoName);
			video.setVideoUrl(videoUrl);
			video.setFeeOrderPlacement(videofeed);
			
			FileEntry fileEntry = null;
			try {
				fileEntry = saveImage(uploadRequest, themeDisplay,
						"videfileUpload");
			} catch (PortalException | SystemException e) {
				SessionErrors.add(actionRequest, VideoConstant.ERROR_IN_VIDEO_MESSAGE_CREATE);
				e.printStackTrace();
			}
			video.setVideoImage(fileEntry.getFileEntryId());
			videoService.create(video);
			SessionMessages.add(actionRequest, VideoConstant.SUCCESS_VIDEO_MESSAGE_CREATE);
		}
		
		
	}
	
	@RenderMapping(params="view=viewVideo")
	public String viewVideo(RenderRequest renderRequest,RenderResponse renderResponse,Model model){
				ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				long videoId = ParamUtil.getLong(renderRequest, "videoId",0);
				
				Video video = null;
				if(videoId > 0){
					try{
						video = videoService.find(videoId);
						if(Validator.isNotNull(video)){
							model.addAttribute("viewVideo", video);
						}else{
							video = new Video();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}	
				return VIEW_VIDEO_JSP;
	}
	
	
	private FileEntry saveImage(UploadPortletRequest uploadRequest,
			ThemeDisplay themeDisplay, String currentFileName)
			throws PortalException, SystemException {
		File file = uploadRequest.getFile(currentFileName);
		ServiceContext serviceContext = ServiceContextFactory
				.getInstance(uploadRequest);
		serviceContext.setAddGuestPermissions(Boolean.TRUE);
		Folder defaultFolder = null;
		try {
			defaultFolder = DLAppServiceUtil.getFolder(
					themeDisplay.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					"videos");
		} catch (PrincipalException e3) {
			log.error(e3.getMessage(), e3);
		} catch (Exception e) {
			try {
				defaultFolder = DLAppServiceUtil.addFolder(
						themeDisplay.getScopeGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						"videos",
						StringPool.BLANK, serviceContext);
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
			}
		}

		String contentType = MimeTypesUtil.getContentType(file);
		FileEntry fileEntry = null;
		try {
			fileEntry = DLAppLocalServiceUtil.addFileEntry(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					defaultFolder.getFolderId(),
					uploadRequest.getFullFileName(currentFileName),
					contentType,
					uploadRequest.getFullFileName(currentFileName),
					uploadRequest.getFullFileName(currentFileName),
					"changeLog", file, serviceContext);
		} catch (PortalException e2) {
			fileEntry = DLAppLocalServiceUtil.addFileEntry(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					defaultFolder.getFolderId(),
					uploadRequest.getFullFileName(currentFileName),
					contentType, uploadRequest.getFullFileName(currentFileName)
							+ new Date().getTime(), "bgImage", "changeLog",
					file, serviceContext);
		} catch (SystemException e3) {
			log.error(e3.getMessage(), e3);
		}

		return fileEntry;
	}
		

}
