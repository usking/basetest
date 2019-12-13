package com.sz.common.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.sz.common.vo.Progress;

@Component
public class FileUploadProgressListener implements ProgressListener {
	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
		Progress progressStatus = new Progress();// 保存上传状态
		session.setAttribute("progressStatus", progressStatus);
	}

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		Progress progressStatus = (Progress) session.getAttribute("progressStatus");
		progressStatus.setBytesRead(bytesRead);
		progressStatus.setContentLength(contentLength);
		progressStatus.setItems(items);
	}

}