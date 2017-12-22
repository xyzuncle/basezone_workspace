package config;


import com.kq.common.service.BaseService;
import org.springframework.stereotype.Component;

@Component
public class FaceBackService implements BaseService{


    @Override
    public String say() {
        return "服务未知。。";
    }
}
