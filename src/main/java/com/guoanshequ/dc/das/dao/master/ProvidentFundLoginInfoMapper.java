package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.model.ProvidentFundLoginInfo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description: 各城市公积金登录参数
 * @Author: gbl
 * @CreateDate: 2018/12/25 11:37
 */
public interface ProvidentFundLoginInfoMapper {

    /**
     * @Description 保存公积金登录参数配置信息
     * @author gbl
     * @date 2018/12/25 11:38
     **/

    public  void saveProvidentFundLoginInfo(ProvidentFundLoginInfo providentFundLoginInfo);

    /**
     * @Description 查询公积金登录参数
     * @author gbl
     * @date 2018/12/25 14:02
     **/

    public List<ProvidentFundLoginInfo> queryProvidentFundLoginInfo(Map<String,Object> param);
}
