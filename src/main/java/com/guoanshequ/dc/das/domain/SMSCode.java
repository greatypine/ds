package com.guoanshequ.dc.das.domain;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.domain
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/6 15:19
 */
public enum SMSCode {
    SMS_SUCCESS("00","发送成功"),
    SMS_PARAM_INCOMPLETE("1","参数不完整，请检查所带的参数名是否都正确"),
    SMS_UNAUTHORIZED("2","鉴权失败，一般是用户名密码不对"),
    SMS_PHONE_MORE_THAN_FIFTY("3","号码数量超出 50 条"),
    SMS_FAIL("4","发送失败"),
    SMS_NOT_SUFFICIENT_FUNDS("5","余额不足"),
    SMS_SENSITIVE_WORD("6","发送内容含屏蔽词"),
    SMS_CONTENT_MORE("7","短信内容超出 350 个字"),
    SMS_CONTENT_BANNED("72","内容被审核员屏蔽"),
    SMS_PHONE_ILLEGAL("8","号码列表中没有合法的手机号码或手机号为黑名单或验证码类每小时超过限制条数"),
    SMS_LIMIT_AT_NIGHT("9","夜间管理，不允许一次提交超过 20 个号码"),
    SMS_PHONE_BLACKLIST("02","手机号码在黑名单"),
    SMS_PHONE_FORMAT_ERROR("81","手机号码错误，请检查手机号是否正确"),
    SMS_IP_ERROR("ERR IP","IP 验证未通过，请联系管理员增加鉴权 IP"),
    SMS_REQUEST_FAIL("-1","短信发送请求失败");


    private String code;
    private String message;

    SMSCode(String code,String message){
        this.code=code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static  String getMessage(String code){
        for(SMSCode sc:SMSCode.values()){
            if(sc.getCode().equals(code)){
                return sc.getMessage();
            }
        }
        return null;
    }




}
