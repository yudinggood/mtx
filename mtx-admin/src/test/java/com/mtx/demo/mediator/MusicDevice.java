package com.mtx.demo.mediator;

public class MusicDevice extends SmartDevice {
    public void operateDevice(String instruction,SmartMediator mediator) {
        System.out.println("音乐设备已"+instruction);
        mediator.music(instruction);
    }

    public void readyState(String instruction) {
        System.out.println("音乐设备准备"+instruction);
    }


}
