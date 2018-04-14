package com.school.dao;

import com.school.entity.PublisherDTO;

import java.util.Map;

public interface IPublisherDAO {

    int insert(PublisherDTO publisherDTO);

    PublisherDTO findByUsername(String username);
}
