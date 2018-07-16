package com.ust.portlet.homepage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.USTHomePageIdea;
import com.unicef.service.IdeaCommentService;
import com.unicef.service.IdeaService;
import com.unicef.service.LikeService;
import com.unicef.util.IdeaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class USTHomepageController {
	
	@Autowired
	private IdeaService ideaService;
	
	@Autowired		
	private LikeService likeService;		
	
	@Autowired		
	private IdeaCommentService ideaCommentService;
	
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest) throws PortalException, SystemException {
		return "view";
	}
	
	@ResourceMapping(value="viewHomepageIdea")
	public String viewHomepageIdeaURL(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model){
		
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
		
		String key = ParamUtil.getString(request, "idea");
		int ideaCount = ParamUtil.getInteger(request,"ideaCount");
		
		int recordsize = ideaCount <= 2 ? 9:8;
		
		int start =  ideaCount <= 2 ? ((ideaCount * 9) - recordsize) : ((ideaCount * 8) - recordsize)+2;
		
		long ideaComment = 0;
		long ideaUpvote  = 0;
		
		String userAvatarURL = StringPool.BLANK;
		List<Idea> ideaList = new ArrayList<Idea>();
		List<IdeaAttachement> ideaAttachmentList = new ArrayList<IdeaAttachement>();
		USTHomePageIdea ustHomePageIdea = null;
		List<USTHomePageIdea> ustHomePageIdeaList = new ArrayList<USTHomePageIdea>();
		int placeHolderCount = 0;
		
		System.out.println( "record size" + start + recordsize);
		
		if(key != null && key.equalsIgnoreCase("hottest")){	
			try{
				ideaList = ideaService.getAllHotIdeas();
				System.out.println("idealIst size"+ideaList.size());
				DLFileEntry dlFileEntry = null;
				String imageURL = StringPool.BLANK;
				
				ideaList = ideaService.getSortedByHotnessHomeList(start, recordsize);
				
				for (Idea idea : ideaList) {
					//for getting total idea upvotes 
					ideaUpvote = likeService.getCountOfIdeaLike(idea.getIdeaId());
					//for getting total idea comments
					ideaComment = ideaCommentService.getIdeaCommentCount(idea.getIdeaId());
					
					ustHomePageIdea = new USTHomePageIdea();
					
					ideaAttachmentList = ideaService.getLatestIdeaAttachementByIdeaId(idea.getIdeaId());
					
						if(ideaAttachmentList.size() == 0){
							imageURL = request.getContextPath()+"/images/Aha2.png";
						}
						for (IdeaAttachement ideaAttachement : ideaAttachmentList) {
								
							try{
								dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(ideaAttachement.getFileEntryId());
							}catch(NoSuchFileEntryException e){
								System.out.println(e.getMessage());
								imageURL = request.getContextPath()+"/images/Aha3.png";
							}
							if(Validator.isNotNull(dlFileEntry) && ideaAttachement.getFileEntryId() == dlFileEntry.getFileEntryId()){
								imageURL = "/documents/" + dlFileEntry.getGroupId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle() + "/" + dlFileEntry.getUuid();
							}
							/*userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());						
							ustHomePageIdea.setIdeaId(idea.getIdeaId());
							ustHomePageIdea.setIdeaTitle(idea.getIdeaTitle());
							ustHomePageIdea.setIdeaImage(imageURL);
							ustHomePageIdea.setUserImage(userAvatarURL);
							ustHomePageIdea.setTagLine(idea.getIdeaTagTitle());
							//set total idea upvotes in list 
							ustHomePageIdea.setIdeaComment(ideaComment);
							//set total idea comments in list
							ustHomePageIdea.setIdeaUpvote(ideaUpvote);
							
							ustHomePageIdeaList.add(ustHomePageIdea);*/
						}
					
					userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());						
					ustHomePageIdea.setIdeaId(idea.getIdeaId());
					ustHomePageIdea.setIdeaTitle(idea.getIdeaTitle());
					ustHomePageIdea.setIdeaImage(imageURL);
					ustHomePageIdea.setUserImage(userAvatarURL);
					ustHomePageIdea.setTagLine(idea.getIdeaTagTitle());
					//set total idea upvotes in list 
					ustHomePageIdea.setIdeaComment(ideaComment);
					//set total idea comments in list
					ustHomePageIdea.setIdeaUpvote(ideaUpvote);
					
					ustHomePageIdeaList.add(ustHomePageIdea);
				}
				
				
				if(ideaCount == 1){
					placeHolderCount = 9 - ustHomePageIdeaList.size();
					System.out.println("placeHolderCount : "+placeHolderCount);
				}else if(ideaCount > 1){
					placeHolderCount = 8 - ustHomePageIdeaList.size();
					System.out.println("placeHolderCount : "+placeHolderCount);
				}
				model.addAttribute("placeHolderCount", placeHolderCount);
				model.addAttribute("ideaCount", ideaCount);
				model.addAttribute("ustHomePageIdeaList", ustHomePageIdeaList);
				model.addAttribute("ideaListSize", ustHomePageIdeaList.size());
				model.addAttribute("ideahottest",1);
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(key.equalsIgnoreCase("latest")){
		
			try{
				ideaList = ideaService.getSortedIdeaHomeList(start, recordsize);
				
				DLFileEntry dlFileEntry = null;
				String imageURL = StringPool.BLANK;
				
				for (Idea idea : ideaList) {
					//for getting total idea upvotes 
					ideaUpvote = likeService.getCountOfIdeaLike(idea.getIdeaId());
					//for getting total idea comments
					ideaComment = ideaCommentService.getIdeaCommentCount(idea.getIdeaId());
					
					ustHomePageIdea = new USTHomePageIdea();
					
					ideaAttachmentList = ideaService.getLatestIdeaAttachementByIdeaId(idea.getIdeaId());
					
						if(ideaAttachmentList.size() == 0){
							imageURL = request.getContextPath()+"/images/Aha2.png";
						}
						for (IdeaAttachement ideaAttachement : ideaAttachmentList) {
							try{
								dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(ideaAttachement.getFileEntryId());
							}catch(NoSuchFileEntryException e){
								System.out.println(e.getMessage());
								imageURL = request.getContextPath()+"/images/Aha3.png";
							}
							if(Validator.isNotNull(dlFileEntry) && ideaAttachement.getFileEntryId() == dlFileEntry.getFileEntryId()){
								imageURL = "/documents/" + dlFileEntry.getGroupId() + "/" + dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle() + "/" + dlFileEntry.getUuid();
							}
							/*userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());
							ustHomePageIdea.setIdeaId(idea.getIdeaId());
							ustHomePageIdea.setIdeaTitle(idea.getIdeaTitle());
							ustHomePageIdea.setIdeaImage(imageURL);
							ustHomePageIdea.setUserImage(userAvatarURL);
							ustHomePageIdea.setTagLine(idea.getIdeaTagTitle());
							//set total idea upvotes in list 
							ustHomePageIdea.setIdeaComment(ideaComment);
							//set total idea comments in list
							ustHomePageIdea.setIdeaUpvote(ideaUpvote);
							
							ustHomePageIdeaList.add(ustHomePageIdea);*/
						}
					
					userAvatarURL = IdeaUtil.getAvatarURL(idea.getCoInventorId());
					ustHomePageIdea.setIdeaId(idea.getIdeaId());
					ustHomePageIdea.setIdeaTitle(idea.getIdeaTitle());
					ustHomePageIdea.setIdeaImage(imageURL);
					ustHomePageIdea.setUserImage(userAvatarURL);
					ustHomePageIdea.setTagLine(idea.getIdeaTagTitle());
					//set total idea upvotes in list 
					ustHomePageIdea.setIdeaComment(ideaComment);
					//set total idea comments in list
					ustHomePageIdea.setIdeaUpvote(ideaUpvote);
					
					ustHomePageIdeaList.add(ustHomePageIdea);
				}
				
				if(ideaCount == 1){
					placeHolderCount = 9 - ustHomePageIdeaList.size();
					System.out.println("placeHolderCount : "+placeHolderCount);
				}else if(ideaCount > 1){
					placeHolderCount = 8 - ustHomePageIdeaList.size();
					System.out.println("placeHolderCount : "+placeHolderCount);
				}
				model.addAttribute("placeHolderCount", placeHolderCount);
				model.addAttribute("ideaCount", ideaCount);
				model.addAttribute("ustHomePageIdeaList", ustHomePageIdeaList);
				model.addAttribute("ideaListSize", ustHomePageIdeaList.size());
				model.addAttribute("idealatest",1);
				
			}catch(Exception e){
			  e.printStackTrace();
			}
		}
		return "homepage";
	}
}