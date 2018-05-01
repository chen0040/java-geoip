# java-geoip

A simple library for converting ip address to country

# Install



# Usage

The sample codes below shows how to use the GeoIp class in your application:

```java
import com.github.chen0040.geoip.GeoIp;

public class Demo {
    public static void main(String[] args) {
        GeoIp geoIp = new GeoIp();
        final String ipAddress = "13.93.122.38";
        CountryResponse country = geoIp.getCountry(ipAddress);
        System.out.pritnln("Country from which ip " + ipAddress + " comes from is " + country.getCountry().getName());
    }
}
``` 
