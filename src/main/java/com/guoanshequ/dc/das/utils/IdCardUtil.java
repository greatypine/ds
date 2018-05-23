package com.guoanshequ.dc.das.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.guoanshequ.dc.das.model.IdCard;

/**
 * 
* @ClassName: IdCardUtil
* @Description: 身份证工具类,由于现在使用的是二代身份证，所以只验证18位
* 地址码：（前6位）所在县（市、旗、区）的行政区划代码
* 出生日期码：（第7位到第14位）
* 顺序码：（第15位到17位）县、区级政府所辖派出所的分配码 
* 校验码：（最后1位） 
* 
* @author caops
* @date 2018年5月21日 下午6:04:00
*
 */
public class IdCardUtil {

	public static IdCard getIdCardInfo(String idcardStr) throws Exception {
		IdCard idcard = new IdCard();
		//出生地
		String birthplace = idcardStr.substring(0,6);
		idcard.setBirthplace(birthplace);
		//省份
		String province = idcardStr.substring(0, 2);
		idcard.setProvince(province);
		//城市
		String city = idcardStr.substring(0,4);
		idcard.setCity(city);
		
		//出生日期
        String birthday = idcardStr.substring(6, 14);  
       // Date birthdate = new SimpleDateFormat("yyyy/MM/dd").parse(birthday);
        idcard.setBirthday(birthday);
		
        // 获取性别  
        String gender = idcardStr.substring(16, 17);  
        if (Integer.parseInt(gender) % 2 != 0) {
        	idcard.setGender("男");  
        } else {  
            idcard.setGender("女");  
        } 
		
		return idcard ; 
	}
	
}
