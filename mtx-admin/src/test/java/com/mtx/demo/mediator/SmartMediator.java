package com.mtx.demo.mediator;

public abstract  class SmartMediator {
    //保留所有设备的引用是为了当接收指令时可以唤醒其他设备的操作
    SmartDevice bd;
    SmartDevice md;
    SmartDevice cd;
    public SmartMediator(SmartDevice bd, SmartDevice md, SmartDevice cd) {
        super();
        this.bd = bd;
        this.md = md;
        this.cd = cd;
    }
    public abstract void music(String instruction);
    public abstract void curtain(String instruction);
    public abstract void bath(String instruction);

}
