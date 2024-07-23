package com.gcp.cloud.poc.utilities;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.gcp.cloud.poc.utilities.constants.StatusCodes;
import com.gcp.cloud.poc.utilities.constants.StatusMessage;
import com.gcp.cloud.poc.utilities.constants.SystemCodes;
import com.gcp.cloud.poc.utilities.exception.ServiceException;

public class CommonUtility {
	public static CommonUtility commonUtility = null;
	public static CommonUtility getInstance() {
		if(commonUtility==null) {
			commonUtility = new CommonUtility();
		}
		return commonUtility;
	}
	
	/**
	 * Checks object value is null. If null return true othewise false.
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNull(Object value) {
		return value==null?true:false;
	}
	
	/**
	 * Checks object value is null. If not null return true othewise false.
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNotNull(Object value) {
		return value!=null?true:false;
	}
	
	/**
	 * Checks list is null or not
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	/**
	 * Checks list is null or not
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNotNullOrEmpty(List<?> list) {
		return list != null && !list.isEmpty();
	}
	
	/**
	 * Checks list is not null and empty
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNotNullOrEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}
	
	/**
	 * Checks list is not null and empty
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNullOrEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	
	/**
	 * Checks string is not null and empty
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNotNullOrEmpty(String input) {
		return input != null && !input.isEmpty();
	}
	
	/**
	 * Checks string is not null and empty
	 * 
	 * @author arun
	 * @param value
	 */
	public boolean isNullOrEmpty(String input) {
		return input == null || input.isEmpty();
	}
	
	/**
	 * Read file content
	 * @author arun
	 * @param filePath
	 * @return
	 * @throws ServiceException
	 */
	public InputStream readFileContent(String filePath) throws ServiceException {
		if(isNullOrEmpty(filePath)) {
			throw new ServiceException(SystemCodes.INPUTS_ERROR, StatusCodes.INPUT_EMPTY, StatusMessage.INPUT_EMPTY);
		}
        InputStream inputStream = CommonUtility.class.getClassLoader().getResourceAsStream(filePath);
        if(isNull(inputStream)) {
        	throw new ServiceException(SystemCodes.FILE_INPUTS_ERROR, StatusCodes.FILE_EMPTY, StatusMessage.FILE_EMPTY);
        }
        return inputStream;
	}
}
