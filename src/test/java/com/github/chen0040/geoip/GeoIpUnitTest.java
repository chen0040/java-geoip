package com.github.chen0040.geoip;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;


/**
 * Created by xschen on 18/3/2017.
 */
public class GeoIpUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(GeoIpUnitTest.class);

   @Test
   public void test_getCountry() throws IOException, GeoIp2Exception {
      GeoIp geoIp = new GeoIp();
      CountryResponse country = geoIp.getCountry("13.93.122.38");
      logger.info("country: {}",country.getCountry().getName());
   }
}
