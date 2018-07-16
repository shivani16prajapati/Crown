package com.unicef.domain;

public class DownloadDocument {

	private long fileEntryId;
	private int count;
	private String title;
	
	
	public long getFileEntryId() {
		return fileEntryId;
	}
	public void setFileEntryId(long fileEntryId) {
		this.fileEntryId = fileEntryId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
