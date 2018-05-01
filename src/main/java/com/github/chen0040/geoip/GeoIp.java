package com.github.chen0040.geoip;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;


/**
 * Created by xschen on 18/3/2017.
 */
public class GeoIp implements AutoCloseable {

   private DatabaseReader client;

   private static final Logger logger = LoggerFactory.getLogger(GeoIp.class);

   public GeoIp(){

   }

   public synchronized void init() throws IOException {

      File dbfile = GeoIpFileUtils.copyToLocal("GeoLite2-Country.mmdb", "/tmp/GeoLite2-Country.mmdb");
       client =  new DatabaseReader.Builder(dbfile).build();
   }

   public CountryResponse getCountry(String ipAddress) throws IOException, GeoIp2Exception {
      if(client == null) {
         init();
      }
      return client.country(InetAddress.getByName(ipAddress));
   }

   public String getCountryName(String ipAddress) {
      CountryResponse countryResponse = null;
      try {
         countryResponse = getCountry(ipAddress);
      }
      catch (IOException | GeoIp2Exception e) {
         logger.error("Failed to get country", e);
      }
      if (countryResponse != null) {
         if (countryResponse.getCountry() != null) {
            return countryResponse.getCountry().getName();
         }
      }
      return "";
   }

   public CityResponse getCity(String ipAddress) throws IOException, GeoIp2Exception {
      if(client == null) {
         init();
      }
      return client.city(InetAddress.getByName(ipAddress));
   }

   @Override public void close() throws Exception {
      if(client != null){
         client.close();
         client = null;
      }
   }
}
