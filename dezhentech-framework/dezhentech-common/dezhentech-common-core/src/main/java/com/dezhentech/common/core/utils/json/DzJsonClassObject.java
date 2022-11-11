package com.dezhentech.common.core.utils.json;

import lombok.Data;

/**
 * Json和Class映射对象<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.json.DzJsonClassObject
 * @since 2022/11/10 20:37:28
 **/
@Data
public class DzJsonClassObject {
	private String className;
	private String objectJson;
}
