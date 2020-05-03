package text;

import static org.junit.Assert.*;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bawei.dao.ArticleLight;
import com.bawei.entity.Article;
import com.github.pagehelper.PageInfo;
import com.zhenzhen.common.utils.HLUtils;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:es.xml")
@SuppressWarnings("all")
public class EsText {
	@Autowired
	private ArticleLight articleLight;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * 
	 * @Title: test
	 * @Description: 测试es添加
	 * @return: void
	 */
	@Test
	public void test() {
		Article article = new Article();
		article.setId(1111);
		article.setTitle("你好，我是一个大好人");
		article.setContent("大海任主题");
		articleLight.save(article);
	}

	/**
	 * 
	 * @Title: test1
	 * @Description: 测试es修改
	 * @return: void
	 */
	@Test
	public void test1() {
		Article article = new Article();
		article.setId(1111);
		article.setTitle("你好，我是一个大好人");
		article.setContent("大好人主题");
		articleLight.save(article);
	}

	/**
	 * 
	 * @Title: test2
	 * @Description: 测试es删除
	 * @return: void
	 */
	@Test
	public void test2() {
		articleLight.deleteById(1111);
	}

	/**
	 * 
	 * @Title: test3
	 * @Description: 测试es查询
	 * @return: void
	 */
	@Test
	public void test3() {
		List<Article> article = articleLight.findByContent("的");
		article.forEach(System.out::println);
	}
	/**
	 * 
	 * @Title: text4 
	 * @Description: 使用elasticsearchTemplate查询
	 * @return: void
	 */
	@Test
	public void text4() {
		// 排序
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		// 分页
		Pageable pageable = new PageRequest(0, 10);

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
				.withPageable(pageable).build();
		AggregatedPage<Article> forPage = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
		List<Article> list = forPage.getContent();
		list.forEach(System.out::println);
	}
	/**
	 * 
	 * @Title: text4 
	 * @Description: 高亮显示查询
	 * @return: void
	 */
	@Test
	public void text5() {
		PageInfo<Article> pageInfo = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, 1, 3, new String[] {"title"}, "id", "的");
		pageInfo.getList().forEach(System.out::println);
	}
}