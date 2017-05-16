package com.github.diamond.web.service;

import com.github.diamond.web.common.SuperDiamondConstant;
import com.github.diamond.web.model.ConfHistory;
import com.github.diamond.web.model.ConfProjectConfig;
import com.github.diamond.web.persistence.ConfHistoryMapper;
import com.github.diamond.web.persistence.ConfProjectConfigMapper;
import com.github.diamond.web.strategy.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author liuhaoyong 2017年5月16日 上午9:00:41
 */
@Service
public class HistoryService {

    @Autowired
    private ConfHistoryMapper confHistoryMapper;

    @Autowired
    private ConfProjectConfigMapper confProjectConfigMapper;

    public List<ConfHistory> getHistoryRecordByConfId(Long configId,String type){
        ConfHistory record=new ConfHistory();
        record.setConfigId(configId);
        record.setType(type);
        return this.confHistoryMapper.queryConfHisByConfId(record);
    }

    /**
     * 回滚配置项
     * @param hisId
     * @param configId
     * @param type
     * @param hisValue
     * @param user
     */
    public void rollbackConfig(Long hisId,Long configId,String type,String hisValue,String user){
        ConfHistory history=this.confHistoryMapper.queryByHisId(hisId);
        if(history!=null){
            Context context=new Context(SuperDiamondConstant.envMap.get(type));
            ConfProjectConfig config=context.setFieldValue(hisValue, user);
            config.setConfigId(configId);
            confProjectConfigMapper.update(config);
        }
    }

    public ConfHistory queryByHisId(Long hisId){
        return this.confHistoryMapper.queryByHisId(hisId);
    }
}
