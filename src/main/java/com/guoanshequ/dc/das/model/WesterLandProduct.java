package com.guoanshequ.dc.das.model;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: 东方大地保险产品
 * @Author: gbl
 * @CreateDate: 2019/1/29 13:26
 */
public class WesterLandProduct {

    private	String	prdNo; //产品销售方案编码
    private	String	prdName; //	产品名称
    private	String	mainRiskCode; //	保险公司产品编码
    private	String	riskCode; //	险种编码
    private	String	payEndYearFlag; //	缴费期间标志
    private	String	payEndYear; //	缴费期间
    private	String	insuYearFlag; //	保险期间标志
    private	String	insuYear; //	保险期间
    private	String	payIntv; //	缴费间隔（缴费频次）
    private	String	prem; //	产品保费
    private	String	amnt; //	产品保额
    private	String	mult; //	份数
    private	String	payEndDate; //	终交日期
    private	String	cvaliDate; //	生效日期
    private	String	endDate; //	保险责任终止日期
    private	String	createDate; //	创建日期


    public String getPrdNo() {
        return prdNo;
    }

    public void setPrdNo(String prdNo) {
        this.prdNo = prdNo;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getMainRiskCode() {
        return mainRiskCode;
    }

    public void setMainRiskCode(String mainRiskCode) {
        this.mainRiskCode = mainRiskCode;
    }

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getPayEndYearFlag() {
        return payEndYearFlag;
    }

    public void setPayEndYearFlag(String payEndYearFlag) {
        this.payEndYearFlag = payEndYearFlag;
    }

    public String getPayEndYear() {
        return payEndYear;
    }

    public void setPayEndYear(String payEndYear) {
        this.payEndYear = payEndYear;
    }

    public String getInsuYearFlag() {
        return insuYearFlag;
    }

    public void setInsuYearFlag(String insuYearFlag) {
        this.insuYearFlag = insuYearFlag;
    }

    public String getInsuYear() {
        return insuYear;
    }

    public void setInsuYear(String insuYear) {
        this.insuYear = insuYear;
    }

    public String getPayIntv() {
        return payIntv;
    }

    public void setPayIntv(String payIntv) {
        this.payIntv = payIntv;
    }

    public String getPrem() {
        return prem;
    }

    public void setPrem(String prem) {
        this.prem = prem;
    }

    public String getAmnt() {
        return amnt;
    }

    public void setAmnt(String amnt) {
        this.amnt = amnt;
    }

    public String getMult() {
        return mult;
    }

    public void setMult(String mult) {
        this.mult = mult;
    }

    public String getPayEndDate() {
        return payEndDate;
    }

    public void setPayEndDate(String payEndDate) {
        this.payEndDate = payEndDate;
    }

    public String getCvaliDate() {
        return cvaliDate;
    }

    public void setCvaliDate(String cvaliDate) {
        this.cvaliDate = cvaliDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
