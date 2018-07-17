package com.unicef.portlet.topdownloads;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * <p>
 * This class is a {@link Controller} of Top Download portlet. All portlet requests
 * of Top Download will be handle by this class.
 * </p>
 * 
 * @author Divyang Patel
 */
@Controller
@RequestMapping("VIEW")
public class TopDownloadController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @param locale
	 * @param model
	 * @return
	 * @throws SystemException 
	 * @throws PortalException 
	 * @see Locale
	 * @see Model
	 */
	@RenderMapping
	public String view(Locale locale, Model model, RenderRequest renderRequest, RenderResponse renderResponse) throws PortalException, SystemException {
		
		List<DLFileEntry> dlFileEntries = DLFileEntryLocalServiceUtil.getFileEntries( -1, -1);
		
		List<DLFileEntry> docList = new ArrayList<DLFileEntry>();
		for(DLFileEntry dlFileEntry : dlFileEntries){
		    if(!dlFileEntry.isInTrash()){
		    	docList.add(dlFileEntry);
		    }
		}
		Collections.sort(docList, fileEntryComparator);
		
		List<DLFileEntry> topMostDownloadList = new ArrayList<DLFileEntry>();
		
		int i = 1;
		for(DLFileEntry dlFileEntry : docList){
			if(dlFileEntry.getReadCount() != 0){
				topMostDownloadList.add(dlFileEntry);
			}
			if(i == MAX_TOP_DOWNLOADS_RESULT_SHOW){
				break;
			}
			i++;
		}
		//test
		
		model.addAttribute("fileEntryList", topMostDownloadList);
		
		return VIEW_JSP;
	}
	
	private static final String VIEW_JSP = "view";

	private static final int MAX_TOP_DOWNLOADS_RESULT_SHOW = 3;
	
	private static final Logger logger = LoggerFactory
			.getLogger(TopDownloadController.class);
	 
	private static Comparator<DLFileEntry> fileEntryComparator = null;
	
	static {
		fileEntryComparator = new Comparator<DLFileEntry>() {
			public int compare(DLFileEntry a, DLFileEntry b) {
				return (a.getReadCount() > b.getReadCount()) ? -1: (a.getReadCount() < b.getReadCount()) ? 1:0 ;
			}
		};
	}
}