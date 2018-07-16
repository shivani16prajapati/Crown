package com.liferay.authenticator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.UserLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("/basicauth")
public class BasicAuthenticator {
	
	@Context
	HttpServletRequest httRequest;
	
	@GET
	@Path("/authentication_initialization")
	public String authenticatedUserViaEmailAndPassword(@HeaderParam(EMAIL) String email, 
			@HeaderParam(PASSWORD) String password){
		
		_log.info("BASIC AUTHENTICATION CALLED ================= ");
		_log.info("EMAIL FROM HEADER ================= " + email);
		_log.info("PASSWORD FROM HEADER ================= " + password);
		
		StringBuilder response = new StringBuilder();
		String errorMessage = StringPool.BLANK;
		boolean isError = false;
		Throwable exception = null;
		long companyId = 10157;
		long isUserAuthenticated = 0l;
		JSONObject responseObject = null;
		String successMessage = StringPool.BLANK;
		
		try{
			isUserAuthenticated = UserLocalServiceUtil.authenticateForBasic(companyId, CompanyConstants.AUTH_TYPE_EA, email, password);
			if(isUserAuthenticated == 0){
				isError = true;
				errorMessage = "User doesn't exist.";
				responseObject = JSONFactoryUtil.createJSONObject();
				responseObject.put(STATUS_FLAG, STATUS_FLAG_NO);
				responseObject.put(SERVER_MESSAGE, errorMessage);
				
			}else if(isUserAuthenticated == -1){
				isError = true;
				errorMessage = "Authentication Failure.";
				responseObject = JSONFactoryUtil.createJSONObject();
				responseObject.put(STATUS_FLAG, STATUS_FLAG_NO);
				responseObject.put(SERVER_MESSAGE, errorMessage);
				
			}else if(isUserAuthenticated >= 1){
				isError = false;
				successMessage = "User Authenticated Successfully.";
				responseObject = JSONFactoryUtil.createJSONObject();
				responseObject.put(STATUS_FLAG, STATUS_FLAG_YES);
				responseObject.put(SERVER_MESSAGE, successMessage);
				responseObject.put(USER_ID, isUserAuthenticated);
				
			}/*else if(isUserAuthenticated > 1){
				isError = false;
				successMessage = "User Authenticated Successfully.";
				responseObject = JSONFactoryUtil.createJSONObject();
				responseObject.put(STATUS_FLAG, STATUS_FLAG_YES);
				responseObject.put(SERVER_MESSAGE, successMessage);
				responseObject.put(SERVER_MESSAGE, successMessage);
				responseObject.put(USER_ID, isUserAuthenticated);
			}*/
			
			
		}catch(PortalException e){
			_log.error("Portal Exception thrown " + e);
			isError = true;
			errorMessage = e.getMessage();
			exception = e;
		}catch(SystemException e){
			_log.error("System Exception Thrown " + e);
			isError = true;
			errorMessage = e.getMessage();
			exception = e;
		}catch(Exception e){
			_log.error("Exception in Authenticating user " + e);
			isError = true;
			errorMessage = e.getMessage();
			exception = e;
		}finally{
			if(isError){
				responseObject = JSONFactoryUtil.createJSONObject();
				responseObject.put(STATUS_FLAG, STATUS_FLAG_NO);
				responseObject.put(SERVER_MESSAGE, errorMessage);
			}
		}
		
		response.append(responseObject.toString());
		_log.info("Final Response ========== " + response.toString());
		
		return response.toString();
		
	}
	
	

	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String STATUS_FLAG = "status_flag";
	private static final String STATUS_FLAG_NO = "status_flag_no";
	private static final String STATUS_FLAG_YES = "status_flag_yes";
	private static final String SERVER_MESSAGE = "server_message";
	private static final String USER_ID = "user_id";
	
	private static Log _log = LogFactoryUtil.getLog(BasicAuthenticator.class);
	
	
}
