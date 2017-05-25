package com.guoanshequ.dc.das.domain;

/**
 * Created by shuhuadai on 2017/2/21.
 */
public enum EnumRespStatus {
    AUTH_OK("1000000", "授权通过"),
    AUTH_ERROR("1000001", "未授权"),
    DATA_OK("2000000", "成功返回数据结果"),
    DATA_ERROR("2000001", "数据错误"),
    DATA_NOCOND("2000002", "请至少输入一组查询条件"),
    DATA_LIMIT("2000003", "查询时间段不允许超过30天"),
    DATA_NODATA("2000004", "系统查无此条件的数据"),
    DATA_WRNOCOND("2000010", "请求参数必须包含年月、门店名称信息"),
    DATA_CSNOCOND("2000020", "请求参数必须包含年月、档数信息"),
    DATA_CSNOCOND1("2000021", "请求参数必须包含年月、门店名称、档数信息"),
    DATA_CSHUMANTYPE("2000022", "请求参数必须包含datatype信息"),
    DATA_STORENOCOND("2000030", "请求参数必须包含开始日期、结束日期"),
    DATA_RELANOCOND("2000040", "请求参数必须包含年月条件"),
    DATA_RELANOCOND1("2000041", "请求参数必须包含年月、门店名称条件"),
    DATA_RELAHUMANTYPE("2000042", "请求参数必须包含datatype信息"),
    DATA_SENDNOCOND("2000050", "请求参数必须包含开始日期、结束日期、门店编号、门店名称"),
    DATA_TRADENOCOND("2000060", "请求参数必须包含开始日期、结束日期、门店编号、门店名称"),
    DATA_REBUYNOCOND("2000070", "请求参数必须包含年份、月份、门店编号、门店名称"),
    DATA_NEWADDNOCOND("2000080", "请求参数必须包含开始日期、结束日期、门店编号、门店名称"),
    DATA_FERRYNOCOND("2000090", "请求参数必须包含年月条件"),
    DATA_TSENDNOCOND("2000100", "请求参数必须包含年份、月份"),
    DATA_TSENDMONTH("20000101", "当前时间只能获取上月数据"),
    DATA_TNEWADDNOCOND("2000120", "请求参数必须包含年份、月份"),
    DATA_TREBUYNOCOND("2000130", "请求参数必须包含年份、月份"),
    DATA_TTRADENOCOND("2000140", "请求参数必须包含年份、月份"),
    DATA_HUMANTYPE("2000150", "请求参数必须包含datatype信息"),
    REQUEST_ERROR("8000001", "请求错误"),
    SYSTEM_ERROR("9000001", "系统错误");

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
