package com.zjw.myblog.service;

import com.zjw.myblog.pojo.Tag;
import com.zjw.myblog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag getTag(Long id);

    Tag saveTag(Tag tag);

    Page<Tag> listTag(Pageable pageable);


    void deleteTag(Long id);

    List<Tag> listTag(String tagIds);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);
}
