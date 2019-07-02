package com.xyj.modules.pay.order;

import com.xyj.modules.pay.vo.UnifiedOrderResultVo;
import com.xyj.modules.pay.vo.UnifiedOrderVo;

/**
 * 支付接口
 * @author zhouguangming
 * @date 7/2/19
 * @since
 */
public interface PayService {
    public UnifiedOrderResultVo unifiedOrder(UnifiedOrderVo unifiedOrderVo) throws Exception;
}
