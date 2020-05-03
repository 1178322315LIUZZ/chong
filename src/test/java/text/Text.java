package text;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.bawei.util.FileUtilIO;

public class Text {

	@Test
	public void test() throws Exception {
		Document document = Jsoup.connect("http://news.sohu.com/").get();
		Elements select = document.select(".list16");
		for (Element element : select) {
			Elements select2 = element.select("a");
			for (Element element2 : select2) {
				//获取标题
				String attr = element2.attr("title");
				attr.replace("|", "").replace(" | ", "").replace("\\", "").replace(":", "").replace("*", "").replace("\"", "").replace("?", "").replace("<", "").replace(">", "");
				//获取文章链接地址
				String attr2 = element2.attr("href");
				if(!attr2.startsWith("https")) {
					attr2="https:"+attr2;
				}
				Document document2 = Jsoup.connect(attr2).get();
				Elements select3 = document2.select("article");
				for (Element element3 : select3) {
					//获取文章内容
					String text = element3.text();
					//System.out.println(text);
					//将获取的文章存入本地
					FileUtilIO.writeFile("D:/文章/"+attr+".txt", text, "utf-8");
				}
			}
		}
	}

}
