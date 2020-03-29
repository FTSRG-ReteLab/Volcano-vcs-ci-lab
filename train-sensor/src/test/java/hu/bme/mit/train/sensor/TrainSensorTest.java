package hu.bme.mit.train.sensor;

import hu.bme.mit.train.user.TrainUserImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;


import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainSensorImpl sensor;
    TrainController controller;
    TrainUser user;
    @Before
    public void before() {


      controller = mock(TrainController.class);
        user = new TrainUserImpl(controller);
        sensor = new TrainSensorImpl(controller,user);
    }
    @Test
    public void HighSpeedLimit() {
        //initial speed limit

        sensor.overrideSpeedLimit(2000);
        System.out.println(user.getAlarmState());
        Assert.assertEquals(true,user.getAlarmState());
    }
    @Test
    public void LowSpeedLimit(){
        sensor.overrideSpeedLimit(-30);
        System.out.println(user.getAlarmState());
        Assert.assertTrue(user.getAlarmState());
    }
    @Test
    public void AlarmWorkingSet(){
        user.setAlarmState(true);
        Assert.assertTrue(user.getAlarmState());
        user.setAlarmState(false);
        Assert.assertFalse(user.getAlarmState());
    }
    @Test
    public void TooMuchSpeed()
    {
        sensor.overrideSpeedLimit(1);
        Assert.assertTrue(user.getAlarmState());
    }
}
