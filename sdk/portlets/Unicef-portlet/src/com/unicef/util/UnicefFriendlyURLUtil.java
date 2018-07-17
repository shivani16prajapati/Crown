package com.unicef.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * This is util class for create unique url for document.
 * 
 * @author Divyang Patel
 */
@Component
public class UnicefFriendlyURLUtil {

	public static String createDocuementURL(long fileEntryId, String friendlyURL, long groupId) {
		String urlTitle = normalize(fileEntryId, friendlyURL);
		return getUniqueDocumentUrlTitle(fileEntryId, urlTitle, groupId);
	}
	
	public static String normalize(long id, String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return String.valueOf(id);
		}
		return normalize(friendlyURL);
	}

	public static String normalize(String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = StringUtil.toLowerCase(friendlyURL.trim());


		friendlyURL = friendlyURL.replaceAll(_friendlyURLPattern.pattern(),
				StringPool.BLANK);

		Matcher matcher = _stopWordsPattern.matcher(friendlyURL);
		String cleanStr = matcher.replaceAll(StringPool.DASH);
		if(Validator.isNotNull(cleanStr)){
			friendlyURL = cleanStr.trim();
		}
		friendlyURL = friendlyURL.replaceAll(StringPool.SPACE,
				StringPool.DASH);
		friendlyURL = friendlyURL.replaceAll(
				_friendlyURLHyphenPattern.pattern(), StringPool.DASH);
		friendlyURL = friendlyURL.replaceAll("^-|-$", StringPool.BLANK);

		return trimString(friendlyURL);
	}
	
	public static String getUniqueDocumentUrlTitle(Long fileEntryId, String urlTitle, long groupId){

		DLFileEntry fileEntry = getDocumentByUrlTitle(urlTitle);

		if ((fileEntry == null) || fileEntryId.equals(fileEntry.getFileEntryId())) {
			return urlTitle;
		}
		if((urlTitle.length() + 2) > URL_MAX_LENGTH) {
			urlTitle = urlTitle.substring(0, URL_MAX_LENGTH - 2);
		}
		urlTitle = urlTitle + StringPool.DASH + 1;
		
		for (int i = 2;; i++) {
			fileEntry = getDocumentByUrlTitle(urlTitle);

			if ((fileEntry == null) || fileEntryId.equals(fileEntry.getFileEntryId())) {
				if(groupId > 0) {
					try {
						Layout layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(groupId, false, urlTitle);
						if(layout == null) {
							break;
						}
					}catch (Exception e) {
						break;
					}
				} else {
					break;
				}
			} 

			urlTitle = getNextUrlTitle(urlTitle, i);
		}

		return urlTitle;
	}
	
	private static String getNextUrlTitle(String urlTitle, int i) {
		String suffix = StringPool.DASH + i;

		String prefix = urlTitle;

		if (urlTitle.length() > suffix.length()) {
			prefix = urlTitle.substring(0,
					urlTitle.length() - suffix.length());
		}

		return prefix + suffix;
	}

	public static DLFileEntry getDocumentByUrlTitle(String urlTitle) {
		return null;
		/*return documentService.findDocumentByURL(urlTitle);*/
	}
	
	public static String trimString(String value) {
		if (value == null) {
			return value;
		}

		int maxLength = URL_MAX_LENGTH;

		if (value.length() > maxLength) {
			return value.substring(0, maxLength);
		} else {
			return value;
		}
	}

	public static long getDocumentIdByURL(String urlTitle) {
		//TODO: 
		return 0L;
	}

	public static long getManufacturerIdByURL(String urlTitle) {
		//TODO: 
		return 0L;
	}

	private static Pattern _friendlyURLHyphenPattern = Pattern
			.compile("(-)\\1+");
	private static Pattern _friendlyURLPattern = Pattern
			.compile("[^a-z0-9 -]");
	private static Pattern _stopWordsPattern = Pattern
			.compile("\\b(?:a|an|as|at|before|but|by|for|from|is|in|into|like|of|off|on|onto|per|since|than|the|this|that|to|up|via|with)\\b\\s*");

	private static int URL_MAX_LENGTH = 128;
/*
	private static final String[] ENGLISH_WORDS = new String[] { "a", "an",
		"as", "at", "before", "but", "by", "for", "from", "is", "in",
		"into", "like", "of", "off", "on", "onto", "per", "since", "than",
		"the", "this", "that", "to", "up", "via", "with" };*/
}
