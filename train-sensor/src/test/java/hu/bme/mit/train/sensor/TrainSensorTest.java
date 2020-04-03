package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
    @Mock TrainController mockController;
    @Mock TrainUser mockUser;
    TrainSensor sensor;

    // Init test cases with Mockito
    @Before
    public void before() {
        mockController = mock(TrainController.class);
        mockUser = mock(TrainUser.class);
        sensor = new TrainSensorImpl(mockController, mockUser);
    }

    // Setting the speedLimit value as valid should be successful
    @Test
    public void testSuccessfulOverrideSpeedLimit() {
        sensor.overrideSpeedLimit(50);
        Assert.assertEquals(50, sensor.getSpeedLimit());
    }

    // Setting a negative speedLimit value should call the setAlarmState(true) method
    @Test
    public void testNegSpeedLimit() {
        sensor.overrideSpeedLimit(-10);
        verify(mockUser).setAlarmState(true);
    }

    // Setting a speedLimit higher than 500 should call the setAlarmState(true) method
    @Test
    public void testTooHighSpeedLimit() {
        sensor.overrideSpeedLimit(1200);
        verify(mockUser).setAlarmState(true);
    }

    // Setting a too low speedLimit value compared to the reference speed should call the setAlarmState(true) method
    @Test
    public void testTooLowSpeedLimit() {
        when(mockController.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(50);
        verify(mockUser).setAlarmState(true);
    }
}
