package com.guoanshequ.dc.das.domain;

/**
 * Created by shuhuadai on 2017/2/21.
 */
public enum EnumRespStatus {
    AUTH_OK("1000000", "授权通过"),
    AUTH_ERROR("1000001", "未授权"),
    TOKEN_OK("1000002", "TOKEN通过"),
    DATA_OK("2000000", "成功返回数据结果"),
    DATA_ERROR("2000001", "数据错误"),
    DATA_NOCOND("2000002", "请至少输入一组查询条件"),
    DATA_LIMIT("2000003", "查询时间段不允许超过30天"),
    DATA_NODATA("2000004", "系统查无此条件的数据"),
    DATA_NOYEARMONTH("2000005", "请求参数必须包含年份、月份"),
    DATA_NOPARA("2000006", "请提供查询所需参数"),
    DATA_WRNOCOND("2000010", "请求参数必须包含年月信息"),
    DATA_CSNOCOND("2000020", "请求参数必须包含年月、档数信息"),
    DATA_CSNOCOND1("2000021", "请求参数必须包含年月、门店名称、档数信息"),
    DATA_CSHUMANTYPE("2000023", "请求参数必须包含datatype信息"),
    DATA_CSNOCOND3("2000024", "请求参数必须包含开始日期、结束日期、门店编号"),
    DATA_CSNOCOND4("2000025", "请求参数必须包含开始日期、结束日期、员工编号"),
    DATA_STORENOCOND("2000030", "请求参数必须包含开始日期、结束日期"),
    DATA_RELANOCOND("2000040", "请求参数必须包含年月条件"),
    DATA_RELANOCOND1("2000041", "请求参数必须包含年月、门店名称条件"),
    DATA_RELANOCOND2("2000042", "请求参数必须包含年份、月份、门店编号"),
    DATA_RELANOCOND3("2000043", "请求参数必须包含开始日期、结束日期、员工编号"),
    DATA_RELAHUMANTYPE("2000042", "请求参数必须包含datatype信息"),
    DATA_SENDNOCOND("2000050", "请求参数必须包含开始日期、结束日期、门店编号"),
    DATA_SENDNOCOND1("2000051", "请求参数必须包含开始日期、结束日期、员工编号"),
    DATA_TRADENOCOND("2000060", "请求参数必须包含开始日期、结束日期、门店编号"),
    DATA_REBUYNOCOND("2000070", "请求参数必须包含年份、月份、门店编号"),
    DATA_NEWADDNOCOND("2000080", "请求参数必须包含开始日期、结束日期、门店编号"),
    DATA_FERRYNOCOND("2000090", "请求参数必须包含年月条件"),
    DATA_TSENDNOCOND("2000100", "请求参数必须包含年份、月份"),
    DATA_TSENDMONTH("20000101", "当前时间只能获取上月数据"),
    DATA_TNEWADDNOCOND("2000120", "请求参数必须包含年份、月份"),
    DATA_TREBUYNOCOND("2000130", "请求参数必须包含年份、月份"),
    DATA_TTRADENOCOND("2000140", "请求参数必须包含年份、月份"),
    DATA_HUMANTYPE("2000150", "请求参数必须包含datatype信息"),
    DATA_TOPDATACOND("2000160", "请求参数必须包含年份、月份"),
    DATA_REWARDNOCOND("2000170", "请求参数必须包含开始日期、结束日期、门店编号"),
    DATA_TREWARDNOCOND("2000180", "请求参数必须包含年份、月份"),
    DATA_GMVPERCNOCOND("2000190", "请求参数必须包含开始日期、结束日期、门店编号"),
    PES_NODATA("7000001", "未推送任何数据"),
    PES_DATAOK("7000002", "推送数据成功"),
    PES_TARGET01("7000011", "请求参数必须包含职位、年月、门店信息"),
    PES_TARGET02("7000012", "请求参数必须包含考核月份参数"),
    TASK_RUNOK("8000009", "手动调度成功"),
    TASK_TOPDATA("8000010", "topData手动调度成功"),
    TASK_ADDHUMANDATA("8000011","人员店长数据手动调度成功"),
    TASK_CUSTOMERDATA("8000012","客户画像数据手动调度成功"),
    TASK_CUSTOMERSTOREDATA("8000013","客户画像(按门店)数据手动调度成功"),
    TASK_RELATIONDATA("8000014","拜访记录数据手动调度成功"),
    TASK_RELATIONSTOREDATA("8000015","拜访记录(按门店)数据手动调度成功"),
    TASK_WORKRECORDDATA("8000016","员工绩效打分数据手动调度成功"),
    TASK_FERRYPUSHDATA("8000017","社区摆渡车数据手动调度成功"),
    TASK_PLATFORMDATA("8000020", "平台数据手动调度成功"),
    TASK_NEWADDCUSOK("8000021", "新增用户手动调度成功"),
    TASK_REBUYCUSOK("8000022", "复购用户手动调度成功"),
    TASK_STOERTRADEOK("8000023", "门店交易额手动调度成功"),
    TASK_SENDORDERSOK("8000024", "上门送单量手动调度成功"),
    TASK_REWARDTIMESOK("8000025", "国安侠好评次数手动调度成功"),
    TASK_ABNORORDEROK("8000026", "异常订单手动调度成功"),
    TASK_AREANEWADDCUSOK("8000027", "片区新增用户调度成功"),
    TASK_AREANEWADDCUSSTOREOK("8000028", "片区新增用户按门店调度成功"),
    TASK_AREATRADEOK("8000029", "片区GMV调度成功"),
    TASK_AREATRADESTOREOK("8000030", "片区GMV按门店调度成功"),
    TASK_AREAZDGMVOK("8000031", "片区重点GMV调度成功"),
    TASK_AREAZDGMVSTOREOK("8000032", "片区重点GMV按门店调度成功"),
    SYSTEM_ERROR("9000001", "系统错误"),
    REQUEST_ERROR("9000002", "请求错误"),
    REQUEST_TIMEOUT("9000003", "已过期的请求"),
    REQUEST_REPEATED("9000004", "无效的重复请求"),
    REQUEST_TOKENNULL("9000005", "请求中需要token参数"),
    
    DATA_NODATETIME("9000099", "请求参数必须包含datetime且不为空"),
	DATA_ERRORDATETIMEFORMAT("9000098", "请求参数格式不正确");

    private String code;
    private String message;

    EnumRespStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean equals(EnumRespStatus other) {
        if (this.code.equals(other.code) && this.message.equals(other.message)) {
            return true;
        } else {
            return false;
        }
    }

}
