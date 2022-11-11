package com.dezhentech.common.core.utils.objects;

import java.io.Serializable;

/**
 * dzNull
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzNull
 * @since 2022/11/10 20:40:02
 **/
public class DzNull implements Serializable {
    private static final long serialVersionUID = 7092611880189329093L;
    
    private Object readResolve() {
      return DzObjectUtil.NULL;
    }
  }
