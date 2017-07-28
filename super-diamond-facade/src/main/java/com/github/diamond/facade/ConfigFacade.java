package com.github.diamond.facade;

import com.github.diamond.facade.request.ConfigEditRequest;
import com.github.diamond.facade.response.ConfigEditResponse;

/**
 * PaymentFacade.java功能描述：统一配置对外服务
 * 
 * @author quyinjun 2017年7月27日 上午9:29:02
 */
public interface ConfigFacade {

    /**
     * 编辑配置
     * 
     * @param request
     * @return
     */
    public ConfigEditResponse edit(ConfigEditRequest request);
}
