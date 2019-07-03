package com.xyj.utils;

import java.util.UUID;

public class IdGenUtils {

	public static String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
