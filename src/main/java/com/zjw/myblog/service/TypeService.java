package com.zjw.myblog.service;

import com.zjw.myblog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type getType(Long id);

    Type saveType(Type type);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    Type updateType(Long id , Type type);

    void deleteType(Long id);

    List<Type> listTypeTop(Integer size);

}
