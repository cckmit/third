package org.opoo.apps.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.AbstractList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.dao.AttachmentDao;
import org.opoo.apps.exception.AttachmentException;
import org.opoo.apps.file.Details;
import org.opoo.apps.file.FileStorage;
import org.opoo.apps.file.FileStorageManager;
import org.opoo.apps.id.IdGeneratable;
import org.opoo.apps.id.IdGenerator;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

public class AttachmentManagerImpl implements AttachmentManager, InitializingBean {
	private static Log log = LogFactory.getLog(AttachmentManagerImpl.class);
	

	public static final class AttachmentExt extends Attachment{
		private static final long serialVersionUID = 1L;
		private Attachment a;
		private volatile AttachmentManagerImpl manager;
		
		public AttachmentExt(Attachment a, AttachmentManagerImpl manager) {
			this.a = a;
			this.manager = manager;
		}
		
		
//		@JSON(serialize=false)
//		public Attachment getWrappedAttachment(){
//			return a;
//		}

		@Override
		public String getContentType() {
			return a.getContentType();
		}

		@Override
		public int getFetchCount() {
			return a.getFetchCount();
		}

		@Override
		public String getFileName() {
			return a.getFileName();
		}

		@Override
		public String getFileType() {
			return a.getFileType();
		}

		@Override
		public String getLocation() {
			return a.getLocation();
		}

		@Override
		public String getOriginalFormat() {
			return a.getOriginalFormat();
		}

		@Override
		public String getProtectedFormat() {
			return a.getProtectedFormat();
		}

		@Override
		public String getReadableFormat() {
			return a.getReadableFormat();
		}


		@Override
		public String getStoreName() {
			return a.getStoreName();
		}

		@Override
		public Date getStoreTime() {
			return a.getStoreTime();
		}

		@Override
		public void setContentType(String contentType) {
			a.setContentType(contentType);
		}

		@Override
		public void setFetchCount(int fetchCount) {
			a.setFetchCount(fetchCount);
		}

		@Override
		public void setFileName(String fileName) {
			a.setFileName(fileName);
		}

		@Override
		public void setFileType(String fileType) {
			a.setFileType(fileType);
		}

		@Override
		public void setLocation(String location) {
			a.setLocation(location);
		}

		@Override
		public void setProtectedFormat(String protectedFormat) {
			a.setProtectedFormat(protectedFormat);
		}

		@Override
		public void setReadableFormat(String readableFormat) {
			a.setReadableFormat(readableFormat);
		}
		

		@Override
		public void setStoreName(String storeName) {
			a.setStoreName(storeName);
		}

		@Override
		public void setStoreTime(Date storeTime) {
			a.setStoreTime(storeTime);
		}
		
		private AttachmentManagerImpl getManager(){
			if(manager == null){
				manager = (AttachmentManagerImpl) Application.getContext().getAttachmentManager();
			}
			return manager;
		}

		@Override
		public void writeAttachment(OutputStream os) {
			getManager().fetch(a, os);
		}
		
		@Override
		public InputStream readInputStream() {
			return getManager().fetch(a, (String) null);
		}

		@Override
		public InputStream readInputStream(String format) {
			return getManager().fetch(a, format);
		}

		@Override
		public Long getKey() {
			return a.getKey();
		}

		@Override
		public void setKey(Long id) {
			a.setKey(id);
		}


		@Override
		public Long getId() {
			return a.getId();
		}

		@Override
		public void setId(Long id) {
			a.setId(id);
		}


		/* (non-Javadoc)
		 * @see org.opoo.apps.bean.core.Attachment#getFileSize()
		 */
		@Override
		public long getFileSize() {
			return a.getFileSize();
		}


		/* (non-Javadoc)
		 * @see org.opoo.apps.bean.core.Attachment#setFileSize(long)
		 */
		@Override
		public void setFileSize(long fileSize) {
			a.setFileSize(fileSize);
		}
	}
	
	
	
	private AttachmentDao attachmentDao;
	private FileStorageManager fileStorageManager;

	private IdGenerator<Long> idGenerator;

	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.attachmentDao, "attachmentDao is required");
		Assert.notNull(this.fileStorageManager, "fileStorageManager is required");
		if(!(attachmentDao instanceof IdGeneratable)){
			throw new IllegalArgumentException("attachmentDao必须实现接口IdGeneratable" );
		}
		idGenerator = ((IdGeneratable<Long>) attachmentDao).getIdGenerator();
	}

	/**
	 * 创建Attachment记录
	 * @param id
	 * @param fileName
	 * @param contentType
	 * @param details
	 * @return
	 */
	private Attachment buildAttachment(Long id, String fileName, String contentType, Details details){
		Attachment a = new Attachment();
		a.setContentType(contentType);
		a.setFetchCount(0);
		a.setFileName(fileName);
		a.setFileType(details.getOriginalFormat());
		a.setId(id);
		a.setLocation(details.getLocation());
		a.setProtectedFormat(details.getProtectedFormat());
		a.setReadableFormat(details.getReadableFormat());
		a.setStoreName(details.getStoreName());
		a.setStoreTime(new Date());
		a.setFileSize(details.getFileSize());
		User user = UserHolder.getNullableUser();
		if(user != null){
			a.setUserId(user.getUserId());
		}
		return a;
	}
	
	public void deleteAttachment(Attachment a) {
		attachmentDao.delete(a);
		fileStorageManager.getFileStorage(a.getStoreName()).delete(a);
	}
	
	
	public void fetch(Long id, OutputStream os) {
		Attachment a = attachmentDao.get(id);
		fetch(a, os);
	}
	
	public void fetch(Attachment a, OutputStream os) {
		InputStream is = fetch(a, (String) null);
		try {
			FileCopyUtils.copy(is, os);
		} catch (IOException e) {
			throw new AttachmentException(e);
		}
	}

	private InputStream fetch(Attachment a, String format){
		//System.out.println("fetch file.....................");
		if(log.isDebugEnabled()){
			log.debug("fetch file: " + a.getId() + "." + format);
		}
		if(format != null && !format.equalsIgnoreCase(a.getOriginalFormat())
				&& !format.equalsIgnoreCase(a.getProtectedFormat())
				&& !format.equalsIgnoreCase(a.getReadableFormat())){
			throw new AttachmentException("要访问的文件类型不存在：" + format);
		}
		
		FileStorage fileStorage = fileStorageManager.getFileStorage(a.getStoreName());
		InputStream is = format == null ? fileStorage.fetch(a) : fileStorage.fetch(a, format);
		//a.setFetchCount(a.getFetchCount()+1);
		attachmentDao.updateFetchCount(a.getId());
		return is;
	}

	public InputStream fetch(Long id) {
		return fetch(id, (String)null);
	}

	public InputStream fetch(Long id, String format){
		Attachment a = attachmentDao.get(id);
		if(a != null){
			return fetch(a, format);
		}
		return null;
	}

	public List<Attachment> findAttachments(ResultFilter rf){
		final List<Attachment> list = (rf == null) ? attachmentDao.find() : attachmentDao.find(rf);
		return new AbstractList<Attachment>(){
			@Override
			public Attachment get(int index) {
				Attachment a = list.get(index);
				return new AttachmentExt(a, AttachmentManagerImpl.this);
			}
			@Override
			public int size() {
				return list.size();
			}
		};
	}
	
	public Attachment getAttachment(long id) {
		Attachment a = attachmentDao.get(id);
		if(a == null){
			return null;
		}
		return new AttachmentExt(a, this);
	}
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	/**
	 * @return the fileStorageManager
	 */
	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}
	
	public void removeAttachment(long id) {
		Attachment a = attachmentDao.get(id);
		deleteAttachment(a);
	}


	public void removeAttachments(Long[] ids) {
		for(int i = 0 ; i < ids.length ; i++){
			removeAttachment(ids[i]);
		}
	}


	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	/**
	 * @param fileStorageManager the fileStorageManager to set
	 */
	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public Attachment store(File tmpFile, String fileName, String contentType) {
		Long id = idGenerator.getNext();
		Details details = fileStorageManager.getActiveFileStorage().store(tmpFile, fileName, contentType, id);
		Attachment a = buildAttachment(id, fileName, contentType, details);
		return new AttachmentExt(attachmentDao.save(a), this);
	}

    public Attachment store(File tmpFile, String fileName, String contentType, String protectedFormat, String readableFormat) {
        Long id = idGenerator.getNext();
        Details details = fileStorageManager.getActiveFileStorage().store(tmpFile, fileName, contentType, id, protectedFormat, readableFormat);
        Attachment a = buildAttachment(id, fileName, contentType, details);
        return new AttachmentExt(attachmentDao.save(a), this);
    }

    public Attachment store(InputStream is, String fileName, String contentType) {
		Long id = idGenerator.getNext();
		Details details = fileStorageManager.getActiveFileStorage().store(is, fileName, contentType, id);
		Attachment a = buildAttachment(id, fileName, contentType, details);
		return new AttachmentExt(attachmentDao.save(a), this);
	}

    public Attachment store(InputStream is, String fileName, String contentType, String protectedFormat, String readableFormat) {
        Long id = idGenerator.getNext();
        Details details = fileStorageManager.getActiveFileStorage().store(is, fileName, contentType, id, protectedFormat, readableFormat);
        Attachment a = buildAttachment(id, fileName, contentType, details);
        return new AttachmentExt(attachmentDao.save(a), this);
    }

    public Attachment update(long id, File tmpFile, String fileName, String contentType){
		Details details = getFileStorageManager().getActiveFileStorage().store(tmpFile, fileName, contentType, id);
		Attachment a = buildAttachment(id, fileName, contentType, details);
		Attachment update = getAttachmentDao().update(a);
		return new AttachmentManagerImpl.AttachmentExt(update, this);
	}

    public Attachment update(long id, File tmpFile, String fileName, String contentType, String protectedFormat, String readableFormat) {
        Details details = getFileStorageManager().getActiveFileStorage().store(tmpFile, fileName, contentType, id, protectedFormat, readableFormat);
        Attachment a = buildAttachment(id, fileName, contentType, details);
        Attachment update = getAttachmentDao().update(a);
        return new AttachmentManagerImpl.AttachmentExt(update, this);
    }

    public Attachment update(long id, InputStream is, String fileName, String contentType) {
		Details details = getFileStorageManager().getActiveFileStorage().store(is, fileName, contentType, id);
		Attachment a = buildAttachment(id, fileName, contentType, details);
		Attachment update = getAttachmentDao().update(a);
		return new AttachmentManagerImpl.AttachmentExt(update, this);
	}

    public Attachment update(long id, InputStream is, String fileName, String contentType, String protectedFormat, String readableFormat) {
        Details details = getFileStorageManager().getActiveFileStorage().store(is, fileName, contentType, id, protectedFormat, readableFormat);
        Attachment a = buildAttachment(id, fileName, contentType, details);
        Attachment update = getAttachmentDao().update(a);
        return new AttachmentManagerImpl.AttachmentExt(update, this);
    }

}
