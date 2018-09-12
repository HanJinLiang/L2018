package com.hanjinliang.l2018.base;

/**
 * Created by HanJinLiang on 2018-09-12.
 */

public class RxBusEvent {
    private int eventCode;
    private Object extraData;

    public RxBusEvent(int eventCode) {
        this.eventCode = eventCode;
    }

    public RxBusEvent(int eventCode, Object extraData) {
        this.eventCode = eventCode;
        this.extraData = extraData;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }
}
