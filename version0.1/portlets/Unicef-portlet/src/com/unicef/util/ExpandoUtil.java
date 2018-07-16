package com.unicef.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

public class ExpandoUtil {
	
	/**
	 * @param companyId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static ExpandoTable addDLFileEntryExpandoTable(long companyId) throws PortalException, SystemException{
		
		try{
			return ExpandoTableLocalServiceUtil.addTable(companyId, PortalUtil.getClassNameId(DLFileEntry.class), "CUSTOM_FIELDS");
		}catch(com.liferay.portlet.expando.DuplicateTableNameException e){
			return ExpandoTableLocalServiceUtil.getTable(companyId,PortalUtil.getClassNameId(DLFileEntry.class), "CUSTOM_FIELDS");
		}
	}
	/**
	 * @param companyId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static ExpandoTable getDLFileEntryExpandoTable(long companyId) throws PortalException, SystemException{
		return ExpandoTableLocalServiceUtil.getTable(companyId , PortalUtil.getClassNameId(DLFileEntry.class), "CUSTOM_FIELDS");
	}
	/**
	 * @param columnName
	 * @param companyId
	 * @return
	 * @throws SystemException
	 * @throws PortalException
	 */
	public static ExpandoColumn addDLFileEntryExpandoColumn(String columnName, long companyId) throws SystemException, PortalException{
		try{
			return ExpandoColumnLocalServiceUtil.addColumn(getDLFileEntryExpandoTable(companyId).getTableId(), columnName, ExpandoColumnConstants.STRING);
		}catch(com.liferay.portlet.expando.DuplicateColumnNameException e){
			return ExpandoColumnLocalServiceUtil.getColumn(getDLFileEntryExpandoTable(companyId).getTableId(), columnName);
		}
	}
	/**
	 * @param columnName
	 * @param companyId
	 * @return
	 * @throws SystemException
	 * @throws PortalException
	 */
	public static ExpandoColumn getDLFileEntryExpandoColumn(String columnName,long companyId) throws SystemException, PortalException{
		return ExpandoColumnLocalServiceUtil.getColumn(getDLFileEntryExpandoTable(companyId).getTableId(), columnName);
	}
	
	/**
	 * @param companyId
	 * @param fileEntryId
	 * @param urlTitle
	 * @return
	 */
	public static String updateURLTitle(long companyId, long fileEntryId, String urlTitle){
		ExpandoColumn expandoUrlTitleColumn = null;
		try{
			
			expandoUrlTitleColumn = getDLFileEntryExpandoColumn(URL_TITLE_CUST_ATTR, companyId);
			_log.info("expando "+expandoUrlTitleColumn.getName()+" column found successfully");
		}catch(Exception e){
			_log.error("Error in getting expando url title column.Please add urlTitle custom attribute");
		}
		ExpandoValue urlTitleValue = null;
		try {
			urlTitleValue = ExpandoValueLocalServiceUtil.getValue(companyId,PortalUtil.getClassNameId(DLFileEntry.class),
												getDLFileEntryExpandoTable(companyId).getName(), 
												expandoUrlTitleColumn.getName(),
												fileEntryId);
			
			if(Validator.isNotNull(urlTitleValue)){
				urlTitleValue.setData(urlTitle);
				ExpandoValueLocalServiceUtil.updateExpandoValue(urlTitleValue);
			}else{
				urlTitleValue = ExpandoValueLocalServiceUtil.addValue(PortalUtil.getClassNameId(DLFileEntry.class), 
        				getDLFileEntryExpandoTable(companyId).getTableId(),
        				expandoUrlTitleColumn.getColumnId(), 
        				fileEntryId, urlTitle);
			}
			

		} catch (Exception e) {
			_log.error("Error in adding expando value for url title:"+e.getMessage());
		}
		return urlTitleValue.getData();
	}
	private static String URL_TITLE_CUST_ATTR = "urlTitle";
	private static Log _log = LogFactoryUtil.getLog(ExpandoUtil.class);
	
}
