package org.opoo.apps.service.impl;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.file.handler.FileHandler;
import org.opoo.apps.file.handler.FileHandlerChain;
import org.opoo.apps.file.handler.FileInfo;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.apps.util.AppsMapUtils;
import org.springframework.util.Assert;

import java.util.Map;

public class AttachmentUpdateFileHandler implements FileHandler {
    private final static Log log = LogFactory.getLog(AttachmentUpdateFileHandler.class);
    private AttachmentManager attachmentManager;

    public AttachmentManager getAttachmentManager() {
        return attachmentManager;
    }

    public void setAttachmentManager(AttachmentManager attachmentManager) {
        this.attachmentManager = attachmentManager;
    }

    public Model handle(Model model, FileInfo fileInfo, FileHandlerChain fileHandlerChain) throws Exception {
        Assert.notNull(fileInfo.getFile(), "�ϴ��ļ�Ϊ�ա�");
        Map<String, Object> parameters = fileInfo.getParameters();
        if(log.isDebugEnabled()){
            log.debug("Parameters: " + parameters);
        }

        String stringId = AppsMapUtils.getString(parameters, "id");
        long id = NumberUtils.toLong(stringId);
        log.info("===�޸�IDΪ" + id + "���ļ�����==");

        String pf = AppsMapUtils.getString(parameters, "pf");
        log.info("===�ܱ���ģʽΪ: " + pf + "===");

        String rf = AppsMapUtils.getString(parameters, "rf");
        log.info("===ֻģʽΪ: " + pf + "===");

        //Attachment a = this._$1.store(inputFile.getFile(), inputFile.getFileName(), inputFile.getContentType());
        //model.setData(a, "�ļ��ϴ��ɹ�");
        //return chain.handle(model, inputFile);
        Attachment a = attachmentManager.update(id, fileInfo.getFile(), fileInfo.getFileName(), fileInfo.getContentType(), pf, rf);
        model.setData(a, "�ļ��޸ĳɹ�");
        return fileHandlerChain.handle(model, fileInfo);
    }
}