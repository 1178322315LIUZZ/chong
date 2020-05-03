package com.bawei.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bawei.entity.Article;


public interface ArticleLight extends ElasticsearchRepository<Article, Integer>{
	List<Article> findByContent(String content);
}
