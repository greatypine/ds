package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.*;
import com.guoanshequ.dc.das.service.*;
import com.guoanshequ.dc.das.utils.ExcelDataFormat;
import com.guoanshequ.dc.das.utils.OSSUploadUtil;
import com.guoanshequ.dc.das.utils.URLUtils;
import com.guoanshequ.dc.das.utils.VerifyExcelDataFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunning on 2018/9/10.
 */
@Component
public class ExcelToDateSourceTask {
    @Autowired
    VillageService villageService;
    @Autowired
    TinyVillageService tinyVillageService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    HouseService houseService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    StoreService storeService;
    private static final Logger logger = LogManager.getLogger(ExcelToDateSourceTask.class);

    /**
     * 定时将地采文件中的数据导入到数据库中
     */
    @Scheduled(cron ="0 */1 * * * ?")
    public void syncExcelToDatasource(){
        logger.info("**********将地采文件中的数据导入到数据库中任务调度开始**********");
        String now_file=null;
        try {
            //读取oss中house文件夹下的所有地采文件名
            List<String> file_names = OSSUploadUtil.queryOssListByPath("daqWeb/house/");
            logger.info("文件夹中地采数据个数："+file_names.size());
            if(file_names==null||file_names.size()==0){logger.info("没文件结束");return;}
            for(String file_name:file_names){
                now_file=file_name;
                logger.info("正在上传的文件名:"+file_name);
                String str=null;
                //根据文件名查询是否有小区
                String village = tinyVillageService.isExistTinyVillageBYFileName(file_name);
                Attachment attachment = attachmentService.findAttachmentByFilePathName(file_name);
                if(attachment==null){
                    break;
                }
                if(village!=null){str="thod";}
                //验证文件格式
                if(file_name.indexOf("xlsx")>0
                        ||file_name.indexOf("xls")>0
                        ||file_name.indexOf("xlsm")>0)
                {
                    //检验文件格式是否正确
                    System.out.println("正在上传的文件名称:"+file_name);
                    HttpsURLConnection httpsURLConnection = URLUtils.getHttpsURLConnection(file_name);
                    StringBuffer buffer =  VerifyExcelDataFormat
                            .verifyExcelFile(httpsURLConnection.getInputStream(),file_name.split("/")[2]);
                    logger.info("可以正常发送请求");
                    if(buffer==null){
                        if("thod".equals(str)) {
                            String gb_code=file_name.split("/")[2].split("-")[0];
                            Village villageByGb_code =villageService.findVillageByFileName(gb_code);
                            if(villageByGb_code!=null){
                                houseService.deleteHouseDataByVillageID(villageByGb_code.getId());
                                buildingService.deleteBuildingByVillageID(villageByGb_code.getId());
                            }
                        }
                        User user = new User();
                        user.setId(attachment.getCreate_user_id());
                        user.setName(attachment.getCreate_user());
                        HttpsURLConnection httpsURLConnection1 = URLUtils.getHttpsURLConnection(file_name);
                        String string = this.saveFileExcel(user, httpsURLConnection1.getInputStream(), file_name.split("/")[2], attachment);
                        attachmentService.updateAttachments(now_file.split("/")[2],"上传成功","上传成功",string);
                        OSSUploadUtil.deleteObjectByUrl(now_file);
                        break;
                    }else{
                        //文件中内容异常
                        attachmentService.updateAttachments(now_file.split("/")[2],buffer.toString(),"上传失败",null);
                        OSSUploadUtil.deleteObjectByUrl(now_file);
                        break;
                    }
                }else{
                    //当文件名不是以xls,xlsx结尾的时候
                    attachmentService.updateAttachments(now_file.split("/")[2],"文件名格式不正确,文件类型为xls或xlsx格式！","上传失败",null);
                    OSSUploadUtil.deleteObjectByUrl(now_file);
                    break;
                }
            }
        }catch (Exception e){
            logger.info("存在异常！");
            logger.info(e.getMessage());
            e.printStackTrace();
            if(now_file!=null){
                attachmentService.updateAttachments(now_file.split("/")[2],e.getMessage().length()>=1000?e.getMessage().substring(0,1000):e.getMessage(),"上传失败",null);
                OSSUploadUtil.deleteObjectByUrl(now_file);
            }
        }

        logger.info("**********将地采文件中的数据导入到数据库中任务调度结束**********");

    }


    public String saveFileExcel( User user,InputStream inp, String fileName,
                                Attachment attachment) throws Exception {
        Long attachmentId = attachment.getId();
        Map<String, Object> map = ExcelDataFormat.getMapDataFromExcel(inp);
        // 社区
        Village village = (Village) map.get("village");
        // 小区+楼房+住宅
        Map<TinyVillage, List<Map<Building, List<House>>>> buildMap = (Map<TinyVillage, List<Map<Building, List<House>>>>) map
                .get("build");
        // 小区+平房住宅
        Map<TinyVillage, List<House>> bungalowMap = (Map<TinyVillage, List<House>>) map.get("bungalow");
        // 小区+写字楼楼层
        Map<TinyVillage, List<House>> shangYeMap = (Map<TinyVillage, List<House>>) map.get("shangye");
        // 小区+广场
        Map<TinyVillage, List<House>> guangChangMap = (Map<TinyVillage, List<House>>) map.get("guangchang");
        // 小区+其他
        Map<TinyVillage, List<House>> otherMap = (Map<TinyVillage, List<House>>) map.get("other");
        // 获取门店名称和门店编码
        String store_code = village.getStore_code();
        Store store = storeService.findStoreBystoreno(store_code);
        if (store == null) {
            attachment.setMessage("门店编码不正确或门店不存在");
            attachment.setUploadType("失败");
            attachment.setStore_name(store.getName());
            attachmentService.updateAttachment(attachment);
            throw new RuntimeException("门店编码不正确或门店不存在");
        }
        attachment.setStore_name(store.getName());

        // 根据文件名里的gb_code取得社区
        String gb_code = fileName.split("-")[0];// 国标
        Village vlist = villageService.findVillageByFileName(fileName.split("-")[0]);
        if (vlist == null) {
            attachment.setMessage("社区国标码" + gb_code + "不存在");
            attachment.setUploadType("失败");
            attachment.setStore_name(store.getName());
            attachmentService.updateAttachment(attachment);
            throw new RuntimeException("社区国标码" + gb_code + "不存在");
        }

        Long villageId = vlist.getId();
        // 保存社区
        vlist.setAttachment_id(attachmentId);
        vlist.setCommittee_address(village.getCommittee_address());
        vlist.setSquare_area(village.getSquare_area());
        vlist.setHousehold_number(village.getHousehold_number());
        vlist.setResident_population_number(village.getResident_population_number());
        vlist.setCommittee_phone(village.getCommittee_phone());
        vlist.setIntroduction(village.getIntroduction());

        // 更新社区
        Village village1 = villageService.updateVillage(vlist, user);
        // 保存小区，住宅楼，商业楼，平房
        saveBuildData(buildMap, village1, user, attachmentId , store);
        saveShangyeData(shangYeMap, village1, user, attachmentId, store);
        saveBungalow(bungalowMap, village1, user, attachmentId, store);
        saveGuangChang(guangChangMap, village1, user, attachmentId, store);
        saveOther(otherMap, village1, user, attachmentId, store);
        return store.getName();
    }

    /**
     * 保存广场、公园数据
     * @param map
     * @param village
     * @param user
     * @param attachmentId
     * @param store
     */
    public void saveGuangChang(Map<TinyVillage, List<House>> map, Village village, User user, Long attachmentId, Store store) {
        Long townId = village.getTown_id();
        TinyVillage tv = null;
        Building building = null;
        TinyVillage tinyVillage = null;
        for (Map.Entry<TinyVillage, List<House>> entry : map.entrySet()) {
            tv = entry.getKey();
            // 插入小区数据 t_tiny_village
            tinyVillage = tinyVillageService.findTinyVillageByVillageIdAndName(tv.getName(), village.getId());
            if (tinyVillage == null){
                tv.setStore_id(store.getStore_id());
                tv.setVillage_id(village.getId());
                tv.setTown_id(townId);
                tv.setAttachment_id(attachmentId);
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tv,user);
                tinyVillageService.insertTinyCode(tinyVillage,user);
            }else{
                // 如果小区不为空判断小区是否有门店
                Long store_id = tinyVillage.getStore_id();
                if (store_id == null || "".equals(store_id) || tinyVillage.getResidents_number() == null
                        || "".equals(tinyVillage.getResidents_number())) {
                    tinyVillage.setStore_id(store.getStore_id());
                }
                tinyVillage.setResidents_number(tv.getResidents_number());
                tinyVillage.setAddress(tv.getAddress());
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tinyVillage,user);;
            }
            // 插入楼房数据 t_building
            building = new Building();
            building.setType(3); // 3=商业楼宇
            building.setName(tv.getName());
            building.setTinyvillage_id(tinyVillage.getId());
            building.setVillage_id(village.getId());
            building.setAttachment_id(attachmentId);
            Building saveBuilding = buildingService.findBuildingBynameAndTinyIdAndtype(building);
            if (saveBuilding == null) {
                saveBuilding = buildingService.insertBuilding(building,user);
            }
            House house = new House();
            house.setAttachment_id(attachmentId);
            house.setTinyvillage_id(tinyVillage.getId());
            house.setBuilding_id(saveBuilding.getId());
            house.setApprove_status(0);
            house.setHouse_type(3);
            house.setBuilding_room_number("0");
            house.setBuilding_house_no(101 + "");
            houseService.insertHouse(house,user);
        }
    }

    /**
     * 保存其他数据
     * @param map
     * @param village
     * @param user
     * @param attachmentId
     * @param store
     */
    public void saveOther(Map<TinyVillage, List<House>> map, Village village, User user, Long attachmentId,
                           Store store) {
        Long townId = village.getTown_id();
        TinyVillage tv = null;
        Building building = null;
        TinyVillage tinyVillage = null;
        for (Map.Entry<TinyVillage, List<House>> entry : map.entrySet()) {
            tv = entry.getKey();
            // 插入小区数据 t_tiny_village
            tinyVillage = tinyVillageService.findTinyVillageByVillageIdAndName(tv.getName(), village.getId());
            if (tinyVillage == null) {
                tv.setStore_id(store.getStore_id());
                tv.setVillage_id(village.getId());
                tv.setTown_id(townId);
                tv.setAttachment_id(attachmentId);
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tv,user);
                tinyVillageService.insertTinyCode(tinyVillage,user);
            } else {
                // 如果小区不为空判断小区是否有门店
                Long store_id = tinyVillage.getStore_id();
                if (store_id == null || "".equals(store_id) || tinyVillage.getResidents_number() == null
                        || "".equals(tinyVillage.getResidents_number())) {
                    tinyVillage.setStore_id(store.getStore_id());

                }
                tinyVillage.setResidents_number(tv.getResidents_number());
                tinyVillage.setAddress(tv.getAddress());
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tinyVillage,user);
            }
            // 插入楼房数据 t_building
            building = new Building();
            building.setType(5); // 5=其他类型
            building.setName(tv.getName());
            building.setTinyvillage_id(tinyVillage.getId());
            building.setVillage_id(village.getId());
            building.setAttachment_id(attachmentId);
            Building saveBuilding = buildingService.findBuildingBynameAndTinyIdAndtype(building);
            if (saveBuilding == null) {
                saveBuilding = buildingService.insertBuilding(building,user);
            }
            House house = new House();
            house.setAttachment_id(attachmentId);
            house.setTinyvillage_id(tinyVillage.getId());
            house.setBuilding_id(saveBuilding.getId());
            house.setApprove_status(0);
            house.setHouse_type(5);
            house.setBuilding_room_number("0");
            house.setBuilding_house_no(101 + "");
            houseService.insertHouse(house,user);
        }
    }
    /**
     * 存储平房
     * @param map
     * @param village
     * @param user
     * @param attachmentId
     * @param store
     */
    public void saveBungalow(Map<TinyVillage, List<House>> map, Village village, User user, Long attachmentId, Store store) {
        System.out.println("调用saveBungalow");
        List<House> houseList = null;
        List<House> newHouseList = null;
        Long townId = village.getTown_id();
        Long tinyVillageId = null;
        TinyVillage tv = null;
        for (Map.Entry<TinyVillage, List<House>> entry : map.entrySet()) {
            tv = entry.getKey();
            // 判断小区是否存在，如果小区不存在则插入新小区，否则不变
            TinyVillage tinyVillage = tinyVillageService.findTinyVillageByVillageIdAndName(tv.getName(), village.getId());
            if (tinyVillage == null) {
                tv.setStore_id(store.getStore_id());
                // 插入小区数据 t_tiny_village
                tv.setVillage_id(village.getId());
                tv.setTown_id(townId);
                tv.setAttachment_id(attachmentId);
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tv,user);
                tinyVillageService.insertTinyCode(tinyVillage,user);
                tinyVillageId = tinyVillage.getId();
            } else {
                // 如果小区不为空判断小区是否有门店
                Long store_id = tinyVillage.getStore_id();
                if (store_id == null || "".equals(store_id) || tinyVillage.getResidents_number() == null
                        || "".equals(tinyVillage.getResidents_number())) {
                    tinyVillage.setStore_id(store.getStore_id());
                }
                tinyVillage.setResidents_number(tv.getResidents_number());
                tinyVillage.setAddress(tv.getAddress());
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tinyVillage,user);
                tinyVillageId = tinyVillage.getId();
            }

            houseList = entry.getValue();
            newHouseList = new ArrayList();
            for (House house : houseList) {
                // 插入住宅数据 t_house 平房的building和house是一对一的关系
                house.setBuilding_id(null);
                house.setAttachment_id(attachmentId);
                house.setTinyvillage_id(tinyVillageId);
                newHouseList.add(house);
            }
            saveHouseList(newHouseList, user);
        }
    }
    /**
     * 存储住宅楼
     * @param map
     * @param village
     * @param user
     * @param attachmentId
     * @param store
     */
    public void saveBuildData(Map<TinyVillage, List<Map<Building, List<House>>>> map, Village village, User user,
                              Long attachmentId, Store store) {
        System.out.println("调用saveBuildData");
        List<House> houseList = null;
        List<House> newHouseList = null;
        List<Map<Building, List<House>>> list = null;
        Long townId = null;
        TinyVillage tinyVillage = null;
        Long buildingId = null;
        TinyVillage tv = null;
        Building building = null;
        // 取得当前社区的街道ID
        for (Map.Entry<TinyVillage, List<Map<Building, List<House>>>> entry : map.entrySet()) {
            tv = entry.getKey();
            // 判断小区是否存在，如果小区不存在则插入新小区，否则不变
            tinyVillage = tinyVillageService.findTinyVillageByVillageIdAndName(tv.getName(), village.getId());

            if (tinyVillage == null) {
                // 插入小区数据 t_tiny_village
                tv.setVillage_id(village.getId());
                tv.setTown_id(village.getTown_id());
                tv.setAttachment_id(attachmentId);
                tv.setStore_id(store.getStore_id());
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tv, user);
                tinyVillageService.insertTinyCode(tinyVillage,user);
            } else {
                // 如果小区不为空判断小区是否有门店
                Long store_id = tinyVillage.getStore_id();
                if (store_id == null || "".equals(store_id) || tinyVillage.getResidents_number() == null
                        || "".equals(tinyVillage.getResidents_number())) {
                    tinyVillage.setStore_id(store.getStore_id());
                }
                tinyVillage.setResidents_number(tv.getResidents_number());
                tinyVillage.setAddress(tv.getAddress());
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tinyVillage, user);
            }
            list = entry.getValue();
            for (Map<Building, List<House>> bhMap : list) {
                for (Map.Entry<Building, List<House>> entrys : bhMap.entrySet()) {

                    // 插入楼房数据 t_building
                    building = entrys.getKey();
                    building.setType(1); // 1=普通楼房住宅
                    building.setVillage_id(village.getId());
                    building.setTinyvillage_id(tinyVillage.getId());
                    building.setAttachment_id(attachmentId);
                    Building buildingByTinyvillageAndName = buildingService.findBuildingBynameAndTinyIdAndtype(building);
                    if (buildingByTinyvillageAndName == null) {
                        building = buildingService.insertBuilding(building,user);
                    } else {
                        building = buildingByTinyvillageAndName;
                    }
                    // 插入住宅数据 t_house
                    houseList = entrys.getValue();
                    newHouseList = new ArrayList();
                    for (House house : houseList) {
                        house.setAttachment_id(attachmentId);
                        house.setTinyvillage_id(tinyVillage.getId());
                        house.setBuilding_id(building.getId());
                        newHouseList.add(house);
                    }
                    saveHouseList(newHouseList,user);
                }
            }
        }
    }

    /**
     * 存储商业楼宇
     * @param map
     * @param village
     * @param user
     * @param attachmentId
     * @param store
     */
    public void saveShangyeData(Map<TinyVillage, List<House>> map, Village village, User user, Long attachmentId,
                                 Store store) {
        System.out.println("调用saveShangyeData");
        List<House> houseList = null;
        List<House> newHouseList = null;
        Long tinyVillageId = null;
        Long townId = village.getTown_id();
        Long buildingId = null;
        TinyVillage tv = null;
        Building building = null;
        TinyVillage tinyVillage = null;
        for (Map.Entry<TinyVillage, List<House>> entry : map.entrySet()) {
            tv = entry.getKey();
            // 插入小区数据 t_tiny_village
            tinyVillage = tinyVillageService.findTinyVillageByVillageIdAndName(tv.getName(), village.getId());
            if (tinyVillage == null) {
                tv.setStore_id(store.getStore_id());
                tv.setVillage_id(village.getId());
                tv.setTown_id(townId);
                tv.setAttachment_id(attachmentId);
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tv,user);
                tinyVillageService.insertTinyCode(tinyVillage,user);
            } else {
                // 如果小区不为空判断小区是否有门店
                Long store_id = tinyVillage.getStore_id();
                if (store_id == null || "".equals(store_id) || tinyVillage.getResidents_number() == null
                        || "".equals(tinyVillage.getResidents_number())) {
                    tinyVillage.setStore_id(store.getStore_id());
                }
                tinyVillage.setResidents_number(tv.getResidents_number());
                tinyVillage.setAddress(tv.getAddress());
                tinyVillage = tinyVillageService.insertOrUpdateTinyVillage(tinyVillage,user);
            }
            // 插入楼房数据 t_building
            building = new Building();
            building.setType(2); // 2=商业楼宇
            building.setName(tv.getName());
            building.setTinyvillage_id(tinyVillage.getId());
            building.setVillage_id(village.getId());
            building.setAttachment_id(attachmentId);
            Building saveBuilding = buildingService.findBuildingBynameAndTinyIdAndtype(building);
            if (saveBuilding == null) {
                saveBuilding = buildingService.insertBuilding(building,user);
            }
            // 插入住宅数据 t_house 写字楼每层为一个house数据
            houseList = entry.getValue();
            newHouseList = new ArrayList();
            for (House house : houseList) {
                house.setAttachment_id(attachmentId);
                house.setTinyvillage_id(tinyVillage.getId());
                house.setBuilding_id(saveBuilding.getId());
                newHouseList.add(house);
            }
            saveHouseList(newHouseList, user);
        }
    }







    /**
     * 保存房间集合 批处理
     *
     * @param houseList
     * @return
     */
    public void saveHouseList(final List<House> houseList, User user){
        //System.out.println("调用saveHouseList");
        House housevalue = null;
        for (House house : houseList) {
            housevalue = new House();
            housevalue.setHouse_type(house.getHouse_type());
            housevalue.setTinyvillage_id(house.getTinyvillage_id());
            housevalue.setBuilding_id(house.getBuilding_id() == null ? 0 : house.getBuilding_id());
            housevalue.setBuilding_unit_no(house.getBuilding_unit_number());
            housevalue.setBuilding_house_no(
                    house.getBuilding_room_number() == null ? "0" : house.getBuilding_room_number() + "");
            if (house.getHouse_type() == 2) {
                housevalue.setHouse_no(house.getCommercial_floor_number() + "");
            } else {
                housevalue.setHouse_no(house.getBungalow_number());
            }
            housevalue.setUsed_price(0);
            housevalue.setRent(0);
            housevalue.setApprove_status(0);
            housevalue.setAddress(house.getAddress() == null ? "" : house.getAddress());
            housevalue.setAttachment_id(house.getAttachment_id());
            houseService.insertHouse(housevalue,user);
        }
    }


}
