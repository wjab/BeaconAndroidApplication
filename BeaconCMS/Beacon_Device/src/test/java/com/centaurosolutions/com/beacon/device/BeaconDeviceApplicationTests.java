package com.centaurosolutions.com.beacon.device;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BeaconDeviceApplication.class)
@WebAppConfiguration
public class BeaconDeviceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
