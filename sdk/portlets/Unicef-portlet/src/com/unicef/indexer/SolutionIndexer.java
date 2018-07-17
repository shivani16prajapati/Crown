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
import com.unicef.constants.Constants;
import com.unicef.domain.Idea;
import com.unicef.domain.IdeaAttachement;
import com.unicef.domain.IdeaHistory;
import com.unicef.domain.Solution;
import com.unicef.service.IdeaService;
import com.unicef.service.SolutionService;
import com.unicef.util.IdeaUtil;
import com.unicef.util.SolutionUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.portlet.PortletURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolutionIndexer extends BaseIndexer implements Indexer{
	private static final Log logger = LogFactoryUtil
			.getLog(SolutionIndexer.class);
	

	@Autowired
	public SolutionService solutionService;

	@Inject
	public void initIdeaDAOFactory(SolutionService solutionService) {
		this.solutionService = solutionService;
	}
	
	public static final String[] CLASS_NAMES = { Solution.class.getName() };

	public static final String PORTLET_ID = Constants.SOLUTION_PORTLET_ID;

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
		Solution entry = (Solution) obj;

		deleteDocument(entry.getCompanyId(), entry.getSolutionId());

	}

	/**
	 * This metod is override default functionlity of BaseIndexer#doGetDocument method
	 */
	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Solution entry = (Solution) obj;

		Document document = getBaseModelDocument(PORTLET_ID, entry);
		// Added required contents in indexer
		document.addNumber(Field.ENTRY_CLASS_PK, entry.getSolutionId());
		document.addText(Field.CONTENT,
				HtmlUtil.extractText(entry.getDescription()));
		document.addText(Field.DESCRIPTION, entry.getDescription());
		document.addDate(Field.MODIFIED_DATE, entry.getModifiedDate());
		document.addDate(Field.CREATE_DATE, entry.getSubmissionDate());
		document.addText(Field.TITLE, entry.getSolutionTitle());
		document.addText(Constants.SOLUTION_NAME, entry.getSolutionTitle());
		document.addNumber(Constants.SOLUTION_PROGRAMME, entry.getSolutionProgramme());
		document.addNumber(Constants.SOLUTION_CATEGORY, entry.getSolutionCategory());
		document.addNumber(Constants.SOLUTION_STAGE, entry.getSolutionStage());
		document.addNumber(Constants.SOLUTION_SPACE_ID, entry.getSpaceId());
		document.addNumber(Constants.CO_INVENTOR_ID, entry.getCoInventorId());
		document.addNumber(Constants.STATUS, entry.getStatus());
		document.addNumber(Field.GROUP_ID, entry.getGroupId());
		document.addNumber(Field.SCOPE_GROUP_ID, entry.getGroupId());
		document.addNumber(Field.COMPANY_ID, entry.getCompanyId());
		document.addNumber(Constants.HOT, entry.isSolutionHot() ? 1 : 0);
		document.addNumber(Constants.HOT_WEIGHT, entry.getHotWeight());
		
		// Added sortable fields in indexer
		String[] sortableTextFields = {Field.TITLE , Field.CREATE_DATE, Constants.SOLUTION_NAME};
		document.setSortableTextFields(sortableTextFields );

		return document;
	}

	@Override
	protected Summary doGetSummary(Document document, Locale locale,
			String snippet, PortletURL portletURL) throws Exception {

		String entryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/solutions");
		portletURL.setParameter("entryId", entryId);

		Summary summary = createSummary(document);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Solution entry = (Solution) obj;
		Document document = getDocument(entry);
		logger.info("Reindexing solution entry>>>>>>>entryId:"+entry.getSolutionId());
		SearchEngineUtil.updateDocument(getSearchEngineId(), entry.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Solution entry = SolutionUtil.getSolution(classPK);
		logger.info("Reindexing solution entry>>>>>>>entryId:"+entry.getSolutionId());
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

	protected Document getBaseModelDocument(String portletId, Solution baseModel)
			throws SystemException {

		return getBaseModelDocument(portletId, baseModel, baseModel);
	}

	protected Document getBaseModelDocument(String portletId,
			Solution baseModel, Solution workflowedBaseModel)
			throws SystemException {
		Document document = newDocument();

		String className = baseModel.getClass().getName();

		long classPK = 0;

		classPK = (Long) baseModel.getSolutionId();

		long companyId = 10157;
		
		companyId = (Long) baseModel.getCompanyId();
		
		document.addUID(portletId, classPK);

		//add solution hot into indexer
		document.addNumber(Constants.HOT, GetterUtil.getBoolean(baseModel.isSolutionHot()) ? 1 : 0);
				
		//add solution hot weight into indexer
		document.addNumber(Constants.HOT_WEIGHT, baseModel.getHotWeight());
				
		document.addKeyword(Field.ENTRY_CLASS_NAME, className);
		document.addKeyword(Field.ENTRY_CLASS_PK, classPK);
		document.addKeyword(Field.PORTLET_ID, portletId);
		document.addNumber(Field.COMPANY_ID, companyId);
		
		return document;
	}

	protected void reindexEntries(long companyId) throws PortalException,
			SystemException {
		List<Solution> solutions = SolutionUtil.getSolutionList();
		for(Solution solution : solutions){
			try {
				reindex(solution);
			} catch (Exception e) {
				logger.error("Error in rendexing solution:"+e.getMessage());
			}
		}

	}

}
