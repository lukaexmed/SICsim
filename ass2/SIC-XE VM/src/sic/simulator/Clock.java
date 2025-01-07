package sic.simulator;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    private Timer timer;
    private boolean isRunning = false;
    private int speed;
    private Machine machine;
    private boolean wasCancelled = false;





    public Clock(int speed, Machine machine) {
        this.speed = speed;
        this.machine = machine;
        this.timer = new Timer();
    }

    public void start(){
        if(!isRunning){
            isRunning = true;
            if(wasCancelled){
                timer = new Timer();
                wasCancelled = false;
            }
            TimerTask izvedi = new TimerTask() {
                @Override
                public void run() {
                    //eksekucija se mora ustavit ko pridemo do halt J halt, tam se zacikla, cp se neha spreminjat
                    int oldPC = machine.regs.getPC();
                    machine.execute();
                    int newPC = machine.regs.getPC();
                    if(oldPC == newPC) {
                        stop();
                    }
                }
            };
            timer.scheduleAtFixedRate(izvedi, 0, speed);
        }
    }

    public void stop(){
        if(isRunning){
            timer.cancel();
            isRunning = false;
            wasCancelled = true;
        }
    }

    public boolean isRunning(){
        return isRunning;
    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
