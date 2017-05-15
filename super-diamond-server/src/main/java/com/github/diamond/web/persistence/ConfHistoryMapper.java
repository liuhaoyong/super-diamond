package com.github.diamond.web.persistence;

import com.github.diamond.web.model.ConfHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfHistoryMapper {

    int deleteByPrimaryKey(Long hisId);

    int deleteByBatch(@Param("hisIds") List<Long> hisIds);

    int insert(ConfHistory record);

    List<ConfHistory> queryConfHisByConfId(ConfHistory record);

    ConfHistory queryByHisId(@Param("hisId")Long hisId);
}