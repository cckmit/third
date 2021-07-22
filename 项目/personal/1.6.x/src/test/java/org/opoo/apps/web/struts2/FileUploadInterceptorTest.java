package org.opoo.apps.web.struts2;

import java.io.File;

import junit.framework.TestCase;

import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileUploadInterceptorTest extends TestCase {
    private FileUploadInterceptor interceptor;
    private File tempDir;
    
    protected void setUp() throws Exception {
        super.setUp();
        interceptor = new FileUploadInterceptor();
        tempDir = File.createTempFile("struts", "fileupload");
        tempDir.delete();
        tempDir.mkdirs();
    }

    protected void tearDown() throws Exception {
        tempDir.delete();
        interceptor.destroy();
        super.tearDown();
    }
    
    

    public void testInvalidContentTypeMultipartRequest() throws Exception {
    	MyFileupAction action = new MyFileupAction();
    	System.out.println(ActionContext.getContext());
    }
    
    private class MyFileupAction extends ActionSupport {

        private static final long serialVersionUID = 6255238895447968889L;

        // no methods
    }
}
