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
        Assert.notNull(fileInfo.getFile(), "上传文件为空。");
        Map<String, Object> parameters = fileInfo.getParameters();
        if(log.isDebugEnabled()){
            log.debug("Parameters: " + parameters);
        }

        String stringId = AppsMapUtils.getString(parameters, "id");
        long id = NumberUtils.toLong(stringId);
        log.info("===修改ID为" + id + "的文件内容==");

        String pf = AppsMapUtils.getString(parameters, "pf");
        log.info("===受保护模式为: " + pf + "===");

        String rf = AppsMapUtils.getString(parameters, "rf");
        log.info("===只模式为: " + pf + "===");

        //Attachment a = this._$1.store(inputFile.getFile(), inputFile.getFileName(), inputFile.getContentType());
        //model.setData(a, "文件上传成功");
        //return chain.handle(model, inputFile);
        Attachment a = attachmentManager.update(id, fileInfo.getFile(), fileInfo.getFileName(), fileInfo.getContentType(), pf, rf);
        model.setData(a, "文件修改成功");
        return fileHandlerChain.handle(model, fileInfo);
    }
}