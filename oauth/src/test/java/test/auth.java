package test;

import com.kq.auth.OuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试类的目的是 通过代码来获取token
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OuthApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class auth {

    @Value("${server.port}")
    private int port;



    /**
     * 测试password方式的token模式
     */
    @Test
    public void test(){
        //通过用户名密码，clientID 和 secret 来获取body
        ResponseEntity<String> response = new TestRestTemplate("android", "android").postForEntity("http://localhost:" + "8086" + "/oauth/token?grant_type=password&username=wyf&password=wyf", null, String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());

        String text = response.getBody();

        System.out.println(text);
    }

    @Test
    public void test2(){
        String userId = "";
        String satelliteType = "";
        String satelliteId = "";
        String sensorId = "";
        String[] tempDate = {"GF1_WFV","GF1_PMS","GF1B_PMS","GF1_XXX","GF1D_PMS","GF2_PMS"};
        Map<String, String> resultMap = new HashMap<>();
        for(int i=0;i<tempDate.length;i++){
            String[] tempSatelliteId = tempDate[i].split("_");
            satelliteId = tempSatelliteId[0];
            if(resultMap.get(satelliteId)==null){
                resultMap.put(satelliteId,tempSatelliteId[1]+",");
            }else if(resultMap.get(satelliteId)!=null){
                String tempSensorId = resultMap.get(satelliteId);
                String tempResult = tempSensorId + tempSatelliteId[1] + ",";
                resultMap.put(satelliteId, tempResult);
            }
        }

        System.out.println(resultMap);
    }



}
