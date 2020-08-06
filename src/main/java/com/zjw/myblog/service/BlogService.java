package com.zjw.myblog.service;

import com.zjw.myblog.pojo.Blog;
import com.zjw.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    void  deleteBolg(Long id);

    Page<Blog> listBlog(Pageable pageable , BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Blog updateBlog(Long id , Blog blog);

    List<Blog> listRecommendBlogTop(Integer size);

    Page<Blog> listBlog(String query, Pageable pageable);

    Blog getBlogAndConvert(Long id);

    Map<String,List<Blog>> archiveBlog();

    Page<Blog> listBlog(Pageable pageable, Long tagId);

    Long countBlog();
}
