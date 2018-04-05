package cases;

import com.school.dao.INewsDAO;
import com.school.entity.NewsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DAOTest {

    @Autowired
    private INewsDAO newsDAO;

    @Test
    public void testDao() {
        List<NewsDTO> newsList = newsDAO.selectAllNews();
        System.out.println(newsList.get(0).getmSubject());
    }
}
