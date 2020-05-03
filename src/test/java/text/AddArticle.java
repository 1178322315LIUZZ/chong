package text;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.bawei.entity.Article;
import com.zhenzhen.common.utils.FileUtilIO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:producer.xml")
@SuppressWarnings("all")
public class AddArticle {
	@Autowired
	KafkaTemplate kafkaTemplate;
	@Test
	public void test() throws Exception {
		File file = new File("D:\\文章");
		File[] files = file.listFiles();
		for (File file2 : files) {
			Article article = new Article();
			String title = file2.getName();
			title=title.replace(".txt", "");
			String content = FileUtilIO.readFile(file2, "utf8");
			article.setTitle(title);
			article.setContent(content);
			String jsonString = JSON.toJSONString(article);
			kafkaTemplate.send("ssn", jsonString);
		}
	}

}
