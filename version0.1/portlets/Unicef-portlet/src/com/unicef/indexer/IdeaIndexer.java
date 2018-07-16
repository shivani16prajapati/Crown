package com.unicef.indexer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.unicef.constants.Constants;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaHistory;
import com.unicef.service.IdeaService;
import com.unicef.util.IdeaUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.portlet.PortletURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdeaIndexer extends BaseIndexer implements Indexer{
	private static final Log logger = LogFactoryUtil
			.getLog(IdeaIndexer.class);
	

	@Autowired
	public IdeaService ideaService;

	@Inject
	public void initIdeaDAOFactory(IdeaService ideaService) {
		this.ideaService = ideaService;
	}
	
	public static final String[] CLASS_NAMES = { Idea.class.getName() };

	public static final String PORTLET_ID = Constants.IDEA_PORTLET_ID;

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		Idea entry = (Idea) obj;

		deleteDocument(entry.getCompanyId(), entry.getIdeaId());

	}

	/**
	 * This metod is override default functionlity of BaseIndexer#doGetDocument method
	 */
	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Idea entry = (Idea) obj;

		Document document = getBaseModelDocument(PORTLET_ID, entry);
		// Added required contents in indexer
		document.addNumber(Field.ENTRY_CLASS_PK, entry.getIdeaId());
		document.addText(Field.CONTENT,
				HtmlUtil.extractText(entry.getDescription()));
		document.addText(Field.DESCRIPTION, entry.getDescription());
		document.addDate(Field.MODIFIED_DATE, entry.getModifiedDate());
		document.addDate(Field.CREATE_DATE, entry.getSubmissionDate());
		document.addText(Field.TITLE, entry.getIdeaTitle());
		document.addText(Constants.IDEA_NAME, entry.getIdeaTitle());
		document.addNumber(Constants.IDEA_PROGRAMME, entry.getIdeaProgramme());
		document.addNumber(Constants.IDEA_CATEGORY, entry.getIdeaCategory());
		document.addNumber(Constants.IDEA_STAGE, entry.getIdeaStage());
		document.addNumber(Constants.INNOVATION_TYPE, entry.getInnovationType());
		document.addNumber(Constants.IS_DELETED, entry.isDeleted() ? 1: 0);
		document.addNumber(Constants.CO_INVENTOR_ID, entry.getCoInventorId());
		document.addNumber(Constants.STATUS, entry.getStatus());
		document.addNumber(Field.GROUP_ID, entry.getGroupId());
		document.addNumber(Field.SCOPE_GROUP_ID, entry.getGroupId());
		document.addNumber(Field.COMPANY_ID, entry.getCompanyId());
		document.addNumber(Constants.VERSION, entry.getVersion());
		document.addNumber(Constants.HOT, entry.isIdeaHot() ? 1 : 0);
		document.addNumber(Constants.HOT_WEIGHT, entry.getHotWeight());
		
		// Added sortable fields in indexer
		String[] sortableTextFields = {Field.TITLE , Field.CREATE_DATE, "ideaName"};
		document.setSortableTextFields(sortableTextFields );

		return document;
	}

	@Override
	protected Summary doGetSummary(Document document, Locale locale,
			String snippet, PortletURL portletURL) throws Exception {

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/ideas");
		portletURL.setParameter("entryId", entryId);

		Summary summary = createSummary(document);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Idea entry = (Idea) obj;
		Document document = getDocument(entry);
		logger.info("Reindexing idea entry>>>>>>>entryId:"+entry.getIdeaId());
		SearchEngineUtil.updateDocument(getSearchEngineId(), entry.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Idea entry = IdeaUtil.getIdea(classPK);
		logger.info("Reindexing idea entry>>>>>>>entryId:"+entry.getIdeaId());
		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);
		reindexEntries(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected Document getBaseModelDocument(String portletId, Idea baseModel)
			throws SystemException {

		return getBaseModelDocument(portletId, baseModel, baseModel);
	}

	protected Document getBaseModelDocument(String portletId,
			Idea baseModel, Idea workflowedBaseModel)
			throws SystemException {
		Document document = newDocument();

		String className = baseModel.getClass().getName();

		long classPK = 0;

		classPK = (Long) baseModel.getIdeaId();

		long companyId = 10157;
		
		companyId = (Long) baseModel.getCompanyId();
		
		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(className, classPK);

			long[] assetTagsIds = StringUtil.split(
				ListUtil.toString(assetTags, AssetTag.TAG_ID_ACCESSOR), 0L);
		
		long[] tagIds = new long[assetTags.size()];
		int j = 0;
		for (AssetTag assetTag : assetTags) {
			tagIds[j] = assetTag.getTagId();
		}

		document.addUID(portletId, classPK);

		Set<IdeaHistory> histories = baseModel.getIdeaHistory();

		long[] ideaHistoryIds = new long[histories.size()];
		String[] ideaTitles = new String[histories.size()];
		int i = 0;
		for (IdeaHistory history : histories) {
			ideaHistoryIds[i] = history.getIdeaHistoryId();
			ideaTitles[i++] = history.getIdeaTitle();
		}
		
		Set<IdeaAttachement> attachements = baseModel.getAttachments();
		
		long[] ideaAttachmentsIds = new long[attachements.size()];
		long[] ideaFileEntryIds = new long[attachements.size()];
		int k = 0;
		for (IdeaAttachement ideaAttachment : attachements) {
			ideaAttachmentsIds[k] = ideaAttachment.getAttachementId();
			ideaFileEntryIds[k++] = ideaAttachment.getFileEntryId();
		}
		
		//add idea hot into indexer
		document.addNumber(Constants.HOT, GetterUtil.getBoolean(baseModel.isIdeaHot()) ? 1 : 0);
		
		//add idea hot weight into indexer
		document.addNumber(Constants.HOT_WEIGHT, baseModel.getHotWeight());
		
		//Added ideaHistoryIds in indexer
		document.addKeyword(Constants.HISTORY_IDS, ideaHistoryIds);

		//Added ideaAttachmentsIds in indexer
		document.addKeyword(Constants.ATTACHMENT_IDS, ideaAttachmentsIds);
		
		//Added tagIds in Indexer
		document.addKeyword(Constants.TAG_IDS, assetTagsIds);

		document.addKeyword(Field.ENTRY_CLASS_NAME, className);
		document.addKeyword(Field.ENTRY_CLASS_PK, classPK);
		document.addKeyword(Field.PORTLET_ID, portletId);
		document.addNumber(Field.COMPANY_ID, companyId);
		
		return document;
	}

	protected void reindexEntries(long companyId) throws PortalException,
			SystemException {
		List<Idea> ideas = IdeaUtil.getIdeaList();
		for(Idea idea : ideas){
			try {
				reindex(idea);
			} catch (Exception e) {
				logger.error("Error in rendexing idea:"+e.getMessage());
			}
		}

	}

}
