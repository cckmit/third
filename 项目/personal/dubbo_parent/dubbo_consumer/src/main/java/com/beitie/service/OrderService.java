package com.beitie.service;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/20
 */
@SPI
public interface OrderService {
    void initData();
}
