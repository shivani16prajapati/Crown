package com.unicef.comparator;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.util.Comparator;

public class DLFileEntryComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		DLFileEntry f1 = (DLFileEntry)o1;
		DLFileEntry f2 = (DLFileEntry)o2;
		/*return (f1.getReadCount() < f2.getReadCount()) ? -1: (f1.getReadCount() > f2.getReadCount()) ? 1:0 ;*/
		return f2.getReadCount() - f1.getReadCount();
	}
}
