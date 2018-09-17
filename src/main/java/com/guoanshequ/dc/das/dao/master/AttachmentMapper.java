package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by sunning on 2018/9/11.
 */
@Repository
@DataSource("master")
public interface AttachmentMapper {
    /**
     * 查询一个正在上传的附件信息
     * @param map
     * @return
     */
    Attachment findAttachmentByFileNameAndFileType(Map<String, Object> map);

    /**
     * 根据文件名查询所有正在上传中的附件信息
     * @param file_name
     * @return
     */
    List<Attachment> findAllAttachmentByFileNameAndFileType(String file_name);

    void updateAttachment(Attachment attachment);





}
