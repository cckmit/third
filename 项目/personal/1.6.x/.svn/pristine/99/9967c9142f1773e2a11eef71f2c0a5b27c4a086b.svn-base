package org.opoo.apps.web.struts2.plugin.json;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.opoo.apps.Model;
import org.opoo.apps.ModelAware;
import org.opoo.apps.json.JSONUtils;

import com.googlecode.jsonplugin.JSONResult;
import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

@Deprecated
public class CustomJSONResult extends JSONResult {
	private static final long serialVersionUID = -4578519716360759032L;
	private static final Log log = LogFactory.getLog(CustomJSONResult.class);
//	private boolean ignoreInterfaces = true;
//	private boolean enumAsBean = false;

	public CustomJSONResult() {
		super();
	}

//	public void setIgnoreInterfaces(boolean ignoreInterfaces) {
//		this.ignoreInterfaces = ignoreInterfaces;
//	}
//
//	public void setSerialNumberClass(String serialNumberClass) {
//	}
//
//	public void setEnumAsBean(boolean enumAsBean) {
//		this.enumAsBean = enumAsBean;
//	}
	public void execute(ActionInvocation invocation) throws Exception {
		String profileKey = "execute CustomJSONResult: ";
		
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

		try {
			
			UtilTimerStack.push(profileKey);
			
			Object action = invocation.getAction();
			// Object rootObject;
			if (action instanceof ModelAware) {
				Model model = ((ModelAware) action).getModel();
				JSONUtils.toJSONList(model.getRows());
				// result = new Response(result);
				// rootObject = new Response((WebResult) action);

				String json = JSONUtil.serialize(model, 
						super.getExcludePropertiesList(), 
						super.getIncludePropertiesList(),
						super.isIgnoreHierarchy(),
						super.isEnumAsBean(),
						super.isExcludeNullProperties());
						//ignoreInterfaces,
						//enumAsBean);
				boolean writeGzip = super.isEnableGZIP() && JSONUtil.isGzipInRequest(request);
				//if(log.isDebugEnabled()){
				//	log.debug(action + " ½á¹û£º" + json);
				//}
				
				writeToResponse(response, json, writeGzip);
				//JSONUtil.writeJSONToResponse(response, getEncoding(),
				//		isWrapWithComments(), json, false, writeGzip);
			} else {
				log.error("CustomJSONResult cannot handler your action: "	+ action);
			}
		} catch (IOException exception) {
			log.error(exception);
			throw exception;
		}finally{
			UtilTimerStack.pop(profileKey);
		}
	}
}
