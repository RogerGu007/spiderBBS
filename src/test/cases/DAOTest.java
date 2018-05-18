package cases;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.Utils;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DAOTest {


    @Test
    public void testDao() throws HttpProcessException {
        //登录后，为上传做准备
        HttpConfig config = HttpConfig.custom();

        String url= "http://localhost:8080/rest/postmsg/35/postmessage";//上传地址
        String[] filePaths = {"E:\\example\\httpclientutil-master\\pom.xml"};//待上传的文件路径

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dto", "我是");
        config.url(url) //设定上传地址
                .encoding("GB2312") //设定编码，否则可能会引起中文乱码或导致上传失败
                .files(filePaths,"myfile",true)//.files(filePaths)，如果服务器端有验证input 的name值，则请传递第二个参数，如果上传失败，则尝试第三个参数设置为true
                .map(map);//其他需要提交的参数

        Utils.debug();//开启打印日志，调用 Utils.debug(false);关闭打印日志
        String r = HttpClientUtil.upload(config);//上传
        System.out.println(r);
    }
}
