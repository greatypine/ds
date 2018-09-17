package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.AttachmentMapper;
import com.guoanshequ.dc.das.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunning on 2018/9/11.
 */
@Service("AttachmentService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class AttachmentService {
    @Autowired
    AttachmentMapper attachmentDao;

    /**
     * 根据文件名查询正在上传中的地采附件
     * @param file_name
     * @return
     */
    public Attachment findAttachmentByFilePathName(String file_name){
        Map<String,Object> map=new HashMap<>();
        map.put("file_name",file_name.split("/")[2]);
        map.put("file_type_name","地址文件");
        return attachmentDao.findAttachmentByFileNameAndFileType(map);
    }

    public void updateAttachments(String file_name,String message,String uploadType,String store_name){
        List<Attachment> attachments = attachmentDao.findAllAttachmentByFileNameAndFileType(file_name);
        for (Attachment attachment:attachments
             ) {
            attachment.setMessage(message);
            attachment.setUploadType(uploadType);
            attachment.setStore_name(store_name);
            attachment.setUpdate_time(new Date());
            attachmentDao.updateAttachment(attachment);
        }
    }

    public void updateAttachment(Attachment attachment){
        attachmentDao.updateAttachment(attachment);
    }







}
