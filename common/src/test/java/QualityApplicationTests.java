
import com.kq.common.ip.IPinfo;
import com.kq.common.util.IPUtil;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;


public class QualityApplicationTests {


@Test
public void test() throws IOException, GeoIp2Exception {

    Map result = IPinfo.getIPtoName("58.20.16.130");
    System.out.println(result);
}

}
