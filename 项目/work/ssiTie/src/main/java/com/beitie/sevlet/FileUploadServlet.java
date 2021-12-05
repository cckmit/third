package com.beitie.sevlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beitie.util.DateFormatUtils;
import org.apache.commons.io.FilenameUtils;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import org.apache.commons.lang3.StringUtils;

/**
 * @author huangshuang
 * @version 2011-5-1
 */
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -3096800116651263134L;

	private String fileSizeLimit="";
	private File oldFile;
	private String kfsId;

	public void init(ServletConfig config) throws ServletException {
//		this.fileSizeLimit = config.getInitParameter("fileSizeLimit");
	}

	public void destroy() {
//		this.fileSizeLimit = null;
		super.destroy();
	}

	class MyFileRenamePolicy implements FileRenamePolicy {
		public File rename(File file) {
			oldFile = file;
			String fileSaveName = StringUtils.join(new String[] { java.util.UUID.randomUUID().toString(), ".",
					FilenameUtils.getExtension(file.getName()) });
			File result = new File(file.getParentFile(), fileSaveName);
			return result;
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--- BEGIN DOPOST ---");
		System.out.println("scwjId:" + request.getParameter("scwjId"));
		System.out.println("sclb:" + request.getParameter("sclb"));

		String scwjId = request.getParameter("scwjId");
		String mjycclbh = request.getParameter("clbh");//开发商面积预测上传附件
		String sqlx = request.getParameter("sqlx");//开发商面积管理上传附件 申请类型
		String clmc = request.getParameter("clmc");
		String mjycsqId = request.getParameter("mjycsqId");
		HttpSession session = request.getSession();
		//文件保存位置，当前项目下的upload/mail
		String uploadDir = "upload" + File.separatorChar + "mail" + File.separatorChar;
		//每天上传的文件根据日期存放在不同的文件夹
		String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd"
				+ File.separatorChar;
		String autoCreatedDateDir = DateFormatUtils.format(new java.util.Date(), autoCreatedDateDirByParttern);

		//String ctxDir = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		String ctxDir = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
			ctxDir = ctxDir + File.separatorChar;
		}
		File savePath = new File(ctxDir + uploadDir + autoCreatedDateDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		System.out.println(savePath.getName() + "======");
		String saveDirectory = ctxDir + uploadDir + autoCreatedDateDir;
		System.out.println(ctxDir + "-----" + saveDirectory);

//		String realpath = this.getClass().getResource("/").getPath();
//		String filepath = realpath.substring(1, realpath.indexOf("WEB-INF/classes"));
//		System.out.println("filepath="+filepath);
//		String saveDirectory = filepath + uploadDir + autoCreatedDateDir;

		if (StringUtils.isBlank(this.fileSizeLimit.toString())) {
			this.fileSizeLimit = "1";//限制1M
		}
		int maxPostSize = Integer.parseInt(this.fileSizeLimit) * 1024 * 1024;
		String encoding = "UTF-8";
		FileRenamePolicy rename = new MyFileRenamePolicy();
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, rename);
			System.out.println(oldFile.getName() + "==================");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// 输出
		Enumeration files = multi.getFileNames();
		while (files.hasMoreElements()) {
			String name = (String) files.nextElement();
			File f = multi.getFile(name);
			System.out.println(f.getName());
			if (f != null) {
				String fileName = multi.getFilesystemName(name);
				String lastFileName = saveDirectory + "\\" + fileName;
				//保存到数据库中的文件路径
				//这个路径从upload/mail/下开始---因为页面上取时从ycfc/下取值
				String bcFileName = uploadDir + autoCreatedDateDir + "\\" + fileName;

				String fileSavePath = uploadDir + autoCreatedDateDir + fileName;
				System.out.println("SimpleUploaderServlet");
				System.out.println("文件地址:" + lastFileName);
				System.out.println("保存时文件地址:" + bcFileName);
				System.out.println("保存路径:" + fileSavePath);
			}

			System.out.println("--- END DOPOST ---");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
