package com.nekosighed.miaosha.pojo;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class SequenceInfoDO {
    /**
     * 表单info
     */
    private String tableInfo;

    /**
     * 当前步长
     */
    private Integer currentStep;

    /**
     * 间隔步长
     */
    private Integer step;

    public String getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(String tableInfo) {
        this.tableInfo = tableInfo == null ? null : tableInfo.trim();
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}