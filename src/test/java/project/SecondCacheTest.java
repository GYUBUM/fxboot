package project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.flexink.FXBootApplication;
import com.flexink.domain.board.BoardType;
import com.flexink.sample.service.BoardTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=FXBootApplication.class)
@WebAppConfiguration
public class SecondCacheTest {

	@Autowired
    private BoardTypeService boardTypeService;
	
	@Test
    public void test() throws Exception {
        /*BoardType b1 = boardTypeService.getBoardType("SAMPLE");
        BoardType b2 = boardTypeService.getBoardType("SAMPLE");*/
    }
}
