package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.EmployeeMoreInfo;
import com.guoanshequ.dc.das.model.EmployeePosition;
import com.guoanshequ.dc.das.service.EmployeeMoreInfoService;
import com.guoanshequ.dc.das.service.EmployeeService;
import com.guoanshequ.dc.das.service.MongoService;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.task
 * @Description:国安侠更多信息
 * @Author: gbl
 * @CreateDate: 2018/9/6 10:39
 */
@Component
public class EmployeeMoreInfoTask {
    @Autowired
    MongoService mongoService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMoreInfoService employeeMoreInfoService;

    /**
     * @Description 国安侠历史运行里程
     * @author gbl
     * @date 2018/9/6 10:44
     **/

    //@Scheduled(cron = "0 11 04 11 9 ?")
    public void getHistoryMovementDistance(){
        new Thread(){
            @Override
            public void run() {

                try {

                    Long total = mongoService.getEmployeePositonCount();
                    List<EmployeePosition> list = new ArrayList<EmployeePosition>();
                    List<Map<String,Object>> employeeList = new ArrayList<Map<String,Object>>();
                    long skipcount = 0;

                    while(total >= skipcount){

                        list = mongoService.queryEmployeeHistoryPosition(skipcount);
                        for(int i=0;i<list.size();i++){
                            EmployeePosition ep = list.get(i);
                            employeeList = employeeService.queryEmployeeById(ep.get_id());
                            if(employeeList!=null&&employeeList.size()>0){
                                String employeeNo = employeeList.get(0).get("employeeNo").toString();
                                List positions = ep.getLocations();

                                float moveDistance = 0;
                                if(positions.size() > 0){
                                    double distance_sum = 0;
                                    for(int j = 0; j <positions.size(); j++){
                                        if(j != positions.size()-1){
                                            List object2 = (List)positions.get(j);
                                            List object3 = (List)positions.get(j+1);
                                            double temp_distance = Distance(Double.parseDouble(object2.get(0).toString()),Double.parseDouble(object2.get(1).toString()), Double.parseDouble(object3.get(0).toString()),Double.parseDouble(object3.get(1).toString()));
                                            distance_sum += temp_distance;
                                        }
                                    }
                                    moveDistance= distance_sum == 0 ? 0 :Float.parseFloat(distance_sum+"");
                                }


                                if(moveDistance > 0){
                                    EmployeeMoreInfo employeeMoreInfo = new EmployeeMoreInfo();
                                    employeeMoreInfo.setMoveDistance(moveDistance);
                                    List<Map<String,Object>> emiList = employeeMoreInfoService.queryEmployeeMoreInfoByEmployeeNo(employeeNo);
                                    if(emiList != null && emiList.size() > 0){
                                        for(int m = 0; m < emiList.size(); m++){
                                            float distance = Float.parseFloat(emiList.get(m).get("moveDistamce")==null?"0":String.valueOf(emiList.get(m).get("moveDistamce")));
                                            employeeMoreInfo.setEmployeeNo(employeeNo);
                                            employeeMoreInfo.setMoveDistance(moveDistance+distance);
                                            employeeMoreInfo.setUpdate_time(new Date());
                                            employeeMoreInfoService.updateEmployeeMoveDistance(employeeMoreInfo);
                                        }
                                    }else{
                                        employeeMoreInfo.setEmployeeNo(employeeNo);
                                        employeeMoreInfo.setMoveDistance(moveDistance);
                                        employeeMoreInfo.setUpdate_time(new Date());
                                        employeeMoreInfo.setCreate_time(new Date());
                                        employeeMoreInfoService.saveEmployeeMoveDistance(employeeMoreInfo);
                                    }
                                }
                            }

                        }
                        skipcount = skipcount+50;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * @Description 获取国安侠前天的移动里程
     * @author gbl
     * @date 2018/9/10 9:43
     **/
    //@Scheduled(cron = "0 22 08 * * ?")
    public void getMovementDistance(){
        new Thread(){
            @Override
            public void run() {

                try {
                    EmployeeMoreInfo clearEMI = new EmployeeMoreInfo();
                    clearEMI.setUpdate_time(new Date());
                    employeeMoreInfoService.updateEmployeeOneDayMoveDistanceZero(clearEMI);//先清空所有员工一天的运行里程

                    List<EmployeePosition> list = new ArrayList<EmployeePosition>();
                    List<Map<String,Object>> employeeList = new ArrayList<Map<String,Object>>();

                        list = mongoService.queryEmployeePosition();
                        for(int i=0;i<list.size();i++){
                            EmployeePosition ep = list.get(i);
                            employeeList = employeeService.queryEmployeeById(ep.get_id());
                            if(employeeList!=null&&employeeList.size()>0){
                                String employeeNo = employeeList.get(0).get("employeeNo").toString();
                                List positions = ep.getLocations();

                                float moveDistance = 0;
                                if(positions.size() > 0){
                                    double distance_sum = 0;
                                    for(int j = 0; j <positions.size(); j++){
                                        if(j != positions.size()-1){
                                            List object2 = (List)positions.get(j);
                                            List object3 = (List)positions.get(j+1);
                                            double distance = Distance(Double.parseDouble(object2.get(0).toString()),Double.parseDouble(object2.get(1).toString()), Double.parseDouble(object3.get(0).toString()),Double.parseDouble(object3.get(1).toString()));
                                            distance_sum = distance_sum+distance;
                                        }
                                    }
                                    moveDistance= distance_sum == 0 ? 0 :Float.parseFloat(distance_sum+"");
                                }


                                if(moveDistance > 0){
                                    EmployeeMoreInfo employeeMoreInfo = new EmployeeMoreInfo();
                                    employeeMoreInfo.setMoveDistance(moveDistance);
                                    List<Map<String,Object>> emiList = employeeMoreInfoService.queryEmployeeMoreInfoByEmployeeNo(employeeNo);
                                    if(emiList != null && emiList.size() > 0){
                                        for(int m = 0; m < emiList.size(); m++){
                                            float distance = Float.parseFloat(emiList.get(m).get("moveDistamce")==null?"0":String.valueOf(emiList.get(m).get("moveDistamce")));
                                            employeeMoreInfo.setEmployeeNo(employeeNo);
                                            employeeMoreInfo.setMoveDistance(moveDistance+distance);
                                            employeeMoreInfo.setUpdate_time(new Date());
                                            employeeMoreInfo.setOneDyMoveDistance(moveDistance);
                                            employeeMoreInfoService.updateEmployeeMoveDistance(employeeMoreInfo);
                                        }
                                    }else{
                                        employeeMoreInfo.setEmployeeNo(employeeNo);
                                        employeeMoreInfo.setMoveDistance(moveDistance);
                                        employeeMoreInfo.setUpdate_time(new Date());
                                        employeeMoreInfo.setCreate_time(new Date());
                                        employeeMoreInfo.setOneDyMoveDistance(moveDistance);
                                        employeeMoreInfoService.saveEmployeeMoveDistance(employeeMoreInfo);
                                    }
                                }
                            }

                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


        /**
         * @Description 计算距离
         * @author gbl
         * @date 2018/9/6 17:05
         **/

        public static double Distance(double long1, double lat1, double long2,  double lat2) {
            double a, b, R;
            R = 6371; // 地球半径
            lat1 = lat1 * Math.PI / 180.0;
            lat2 = lat2 * Math.PI / 180.0;
            a = lat1 - lat2;
            b = (long1 - long2) * Math.PI / 180.0;
            double d;
            double sa2, sb2;
            sa2 = Math.sin(a / 2.0);
            sb2 = Math.sin(b / 2.0);
            d = 2*R*Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)*Math.cos(lat2) * sb2 * sb2));
            return d;
        }



    /**
     * @Description 时间间隔天数
     * @author gbl
     * @date 2018/7/10 13:47
     **/
    private Map<String,Object> dateCompare(Date fromDate, Date toDate){
        Map<String,Object> result = new HashMap<String,Object>();
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int year = toYear  -  fromYear;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
        int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
        int year1= month%12;
        int year2 = month/12;
        if(year1>0){
            result.put("preciseYear",year2+"年以上");
        }else if(year1==0){
            result.put("preciseYear",year2+"年");
        }else{
            result.put("preciseYear","none");
        }
        result.put("year",year);
        result.put("month",month);
        result.put("day",day);
        return result;
    }


    /**
     * @Description
     * @author gbl
     * @date 2018/9/10 14:49
     **/

    //@Scheduled(cron = "0 0 09 * * ?")
    public void analyzeEmployeeWorkingAge() {

        List<Map<String,Object>> humanresources = null;
        List<Map<String,Object>> storekeeper = null;
        try {
                humanresources = employeeMoreInfoService.queryValidHumanresource();
                storekeeper = employeeMoreInfoService.selectValidStoreKepeer();
                Map<String,Object> result = new HashMap<String,Object>();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat sf=new SimpleDateFormat("yyyy.MM.dd");
                EmployeeMoreInfo employeeMoreInfo=null;

                //更新国安侠工龄
                for(int i=0;i<humanresources.size();i++){
                    Object topostdate = humanresources.get(i).get("topostdate");
                    Object employeeNo = humanresources.get(i).get("employee_no");
                    if(topostdate==null||employeeNo==null){
                        continue;

                    }

                    try {
                        result = dateCompare(sdf.parse(topostdate.toString()),date);
                    } catch (ParseException e) {
                        try {
                            result = dateCompare(df.parse(topostdate.toString()),date);
                        } catch (ParseException e1) {
                            try {
                                result = dateCompare(sf.parse(topostdate.toString()),date);
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                                result.put("year",0);
                                result.put("month",0);
                                result.put("day",0);
                                result.put("preciseYear","none");
                            }
                        }
                    }
                    Integer year = Integer.parseInt(String.valueOf(result.get("year")));
                    Integer month = Integer.parseInt(String.valueOf(result.get("month")));
                    Integer day = Integer.parseInt(String.valueOf(result.get("day")));
                    String preciseYear = String.valueOf(result.get("preciseYear"));


                    List<Map<String,Object>>  empList = employeeMoreInfoService.queryEmployeeMoreInfoByEmployeeNo(String.valueOf(employeeNo));
                    Date cur_date = new Date();
                    if(empList!=null&&empList.size()>0){//如果存在则更新
                        EmployeeMoreInfo employeeMoreInfo_s = new EmployeeMoreInfo();

                        for(int j=0;j<empList.size();j++){
                            employeeMoreInfo_s.setEmployeeNo(String.valueOf(employeeNo));
                            employeeMoreInfo_s.setWorkingAge_year(year);
                            employeeMoreInfo_s.setWorkingAge_month(month);
                            employeeMoreInfo_s.setWorkingAge_day(day);
                            employeeMoreInfo_s.setWorkingAge_year_precise(preciseYear);
                            employeeMoreInfo_s.setCreate_time(cur_date);
                            employeeMoreInfo_s.setUpdate_time(cur_date);
                            employeeMoreInfoService.updateEmployeeWorkingAge(employeeMoreInfo_s);
                        }
                    }else{//不存在则新增
                        employeeMoreInfo = new EmployeeMoreInfo();
                        employeeMoreInfo.setWorkingAge_year(year);
                        employeeMoreInfo.setWorkingAge_month(month);
                        employeeMoreInfo.setWorkingAge_day(day);
                        employeeMoreInfo.setWorkingAge_year_precise(preciseYear);
                        employeeMoreInfo.setUpdate_time(cur_date);
                        employeeMoreInfoService.saveEmployeeWorkingAge(employeeMoreInfo);

                    }

                }

                //更新店长工龄
                for(int j=0;j<storekeeper.size();j++){
                    Object topostdate = storekeeper.get(j).get("topostdate");
                    Object employeeNo = storekeeper.get(j).get("employee_no");
                    if(topostdate==null){
                        continue;
                    }

                    try {
                        result = dateCompare(sdf.parse(topostdate.toString()),date);
                    } catch (ParseException e) {
                        try {
                            result = dateCompare(df.parse(topostdate.toString()),date);
                        } catch (ParseException e1) {
                            try {
                                result = dateCompare(sf.parse(topostdate.toString()),date);
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                                result.put("year",0);
                                result.put("month",0);
                                result.put("day",0);
                                result.put("preciseYear","none");
                            }
                        }
                    }

                    Integer year = Integer.parseInt(String.valueOf(result.get("year")));
                    Integer month = Integer.parseInt(String.valueOf(result.get("month")));
                    Integer day = Integer.parseInt(String.valueOf(result.get("day")));
                    String preciseYear = String.valueOf(result.get("preciseYear"));

                    List<Map<String,Object>>  empList = employeeMoreInfoService.queryEmployeeMoreInfoByEmployeeNo(String.valueOf(employeeNo));
                    Date cur_date = new Date();
                    if(empList!=null&&empList.size()>0){//如果存在则更新
                        EmployeeMoreInfo employeeMoreInfo_e = new EmployeeMoreInfo();
                        for(int n=0;n<empList.size();n++){

                            employeeMoreInfo_e.setWorkingAge_year(year);
                            employeeMoreInfo_e.setWorkingAge_month(month);
                            employeeMoreInfo_e.setWorkingAge_day(day);
                            employeeMoreInfo_e.setWorkingAge_year_precise(preciseYear);

                            employeeMoreInfo_e.setUpdate_time(cur_date);
                            employeeMoreInfoService.updateEmployeeWorkingAge(employeeMoreInfo_e);

                        }
                    }else{//不存在则新增
                        employeeMoreInfo = new EmployeeMoreInfo();
                        employeeMoreInfo.setWorkingAge_year(year);
                        employeeMoreInfo.setWorkingAge_month(month);
                        employeeMoreInfo.setWorkingAge_day(day);
                        employeeMoreInfo.setWorkingAge_year_precise(preciseYear);
                        employeeMoreInfoService.saveEmployeeWorkingAge(employeeMoreInfo);
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
