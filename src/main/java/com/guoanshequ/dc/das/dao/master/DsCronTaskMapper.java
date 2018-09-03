package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by greatypine on 2018/1/11.
 */
@Repository
@DataSource("master")
public interface DsCronTaskMapper {

    List<Map<String, String>> queryDsCronTask (Map<String, String> paraMap);

    Map<String, String> queryDsCronTaskById (int id);
    
    Integer updateTaskStatusById(Map<String, String> paraMap);
    
    Integer updateNextBeginTimeById(Map<String, String> paraMap);
    
    Integer updateFlagBeginTimeById(Map<String, String> paraMap);

}
