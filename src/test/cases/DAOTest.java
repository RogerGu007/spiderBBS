package cases;

import com.school.remote.NewsRomoteCaller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DAOTest {
    @Autowired
    NewsRomoteCaller newsRomoteCaller;

    @Test
    public void testDao(){
        //登录后，为上传做准备
        newsRomoteCaller.getNewsDetailByUrl("http://bbs.whu.edu.cn/wForum/disparticle.dphp?boardName=Job&ID=1110479648");

    }
}
