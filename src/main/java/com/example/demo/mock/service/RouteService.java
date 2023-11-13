package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.RoutePO;

public interface RouteService {
    RoutePO selectByUrl(Long projectId,String url);
}
