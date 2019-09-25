package com.mtx.demo.mediator;

public abstract class SmartDevice {
    //相关设备打开之后 使其进入准备状态
    public abstract void readyState(String instruction);
    //操作该设备
    public abstract void operateDevice(String instruction, SmartMediator mediator);


}
