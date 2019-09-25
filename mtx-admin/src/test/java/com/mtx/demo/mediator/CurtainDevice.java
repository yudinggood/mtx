package com.mtx.demo.mediator;

public class CurtainDevice extends SmartDevice {
    public void operateDevice(String instruction,SmartMediator mediator) {
        System.out.println("窗帘已"+instruction);//通过传入指令，打开或关闭窗帘
        mediator.curtain(instruction);//窗帘通过中介者唤醒音乐设备和洗浴设备
    }

    public void readyState(String instruction) {
        //如果其他设备开启则调用此方法，唤醒窗帘
        System.out.println("窗帘设备准备"+instruction);
    }


}
