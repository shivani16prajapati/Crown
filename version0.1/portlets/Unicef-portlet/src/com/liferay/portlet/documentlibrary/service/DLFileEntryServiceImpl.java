package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.unicef.util.ExpandoUtil;
import com.unicef.util.UnicefFriendlyURLUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public class DLFileEntryServiceImpl extends DLFileEntryServiceWrapper {

	public DLFileEntryServiceImpl(DLFileEntryService dlFileEntryService) {
		super(dlFileEntryService);
	}

	@Override
	public DLFileEntry addFileEntry(long groupId, long repositoryId,
			long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			long fileEntryTypeId, Map<String, Fields> fieldsMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
			throws PortalException, SystemException {
		
		DLFileEntry dlFileEntry  = super.addFileEntry(groupId, repositoryId, folderId, sourceFileName,
				mimeType, title, description, changeLog, fileEntryTypeId, fieldsMap,
				file, is, size, serviceContext);
		
		String urlTitle = UnicefFriendlyURLUtil.createDocuementURL(dlFileEntry.getFileEntryId(), dlFileEntry.getTitle(), 0);
		
		ExpandoUtil.updateURLTitle(dlFileEntry.getCompanyId(), dlFileEntry.getFileEntryId(), urlTitle);
		
		return dlFileEntry;
	}
	
	@Override
	public DLFileEntry updateFileEntry(long fileEntryId, String sourceFileName,
			String mimeType, String title, String description,
			String changeLog, boolean majorVersion, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is,
			long size, ServiceContext serviceContext) throws PortalException,
			SystemException {
		
		DLFileEntry dlFileEntry  = super.updateFileEntry(fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, fileEntryTypeId, fieldsMap, file,
				is, size, serviceContext);
		
		String urlTitle = UnicefFriendlyURLUtil.createDocuementURL(dlFileEntry.getFileEntryId(), dlFileEntry.getTitle(), 0);
		
		ExpandoUtil.updateURLTitle(dlFileEntry.getCompanyId(), dlFileEntry.getFileEntryId(), urlTitle);
		
		return dlFileEntry;
	}

}
