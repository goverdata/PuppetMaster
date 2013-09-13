package com.github.common.security;

import com.github.common.exception.ErrorCode;
import com.github.common.exception.RAMPAPIException;

public class StringValidator {
	public static void verifyEmpty(String paraName, String parameter) {
		if (parameter == null || parameter.trim().isEmpty()) {
			throw new RAMPAPIException(ErrorCode.COMMON_EMPTY_STRING, paraName);
		}
	}
}
