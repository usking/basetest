package com.sz.common.constant;

import com.sz.common.util.PropUtils;

public class Consts {

	/**文件上传路径**/
	public static final String FILE_UPLOAD_PATH=PropUtils.getConfigInfo("file_upload_path");
	/**文件访问地址**/
	public static final String FILE_UPLOAD_URL=PropUtils.getConfigInfo("file_upload_url");
	
	public static final String CSRF_TOKEN_NAME="csrftoken";
}
