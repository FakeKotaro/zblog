package com.zjw.myblog.service;

import com.zjw.myblog.dao.BlogDao;
import com.zjw.myblog.pojo.Blog;
import com.zjw.myblog.pojo.Type;
import com.zjw.myblog.util.MarkdownUtils;
import com.zjw.myblog.util.MyBeanUtils;
import com.zjw.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getOne(id);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }

    @Override
    public void deleteBolg(Long id) {
        blogDao.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog oldBlog = blogDao.getOne(id);
        if (oldBlog == null) {
            return null;
        }
        BeanUtils.copyProperties(blog,oldBlog, MyBeanUtils.getNullPropertyNames(blog));
        oldBlog.setUpdateTime(new Date());
        return blogDao.save(oldBlog);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogDao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query , pageable);
    }

    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = blogDao.getOne(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        blogDao.updateViews(id);
        return b;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        Map<String,List<Blog>> map = new HashMap<>();
        List<String> years = blogDao.findGroupYear();
        for (String year : years) {
            map.put(year , blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }
}
