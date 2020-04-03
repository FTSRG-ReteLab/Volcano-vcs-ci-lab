package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;
import java.util.logging.Logger;

public class TrainControllerImpl implements TrainController {

    private int step = 0;
    private int referenceSpeed = 0;
    private int speedLimit = 0;
    private Thread thread;
    private System.Logger logger = (System.Logger) Logger.getLogger(TrainControllerImpl.class.getName());

    public TrainControllerImpl() {
        thread = new Thread(() -> {
            thread.start();
            try {
                followSpeed();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.log(System.Logger.Level.ERROR, "Thread Interrupted Exception!", e);
                thread.interrupt();
            }
        });
    }

    @Override
    public void followSpeed() {
        if (referenceSpeed < 0) {
            referenceSpeed = 0;
        } else {
            if (referenceSpeed + step > 0) {
                referenceSpeed += step;
            } else {
                referenceSpeed = 0;
            }
        }

        enforceSpeedLimit();
    }

    @Override
    public int getReferenceSpeed() {
        return referenceSpeed;
    }

    @Override
    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
        enforceSpeedLimit();

    }

    private void enforceSpeedLimit() {
        if (referenceSpeed > speedLimit) {
            referenceSpeed = speedLimit;
        }
    }

    @Override
    public void setJoystickPosition(int joystickPosition) {
        this.step = joystickPosition;
    }

}
