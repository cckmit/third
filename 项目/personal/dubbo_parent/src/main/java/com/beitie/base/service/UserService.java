package com.beitie.base.service;

import com.beitie.base.bean.User;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/18
 */
public interface UserService {
    User findUserByUid(String uid);
}
