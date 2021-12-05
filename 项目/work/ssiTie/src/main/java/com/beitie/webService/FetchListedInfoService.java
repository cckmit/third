package com.beitie.webService;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/12/1
 */
@WebService
public interface FetchListedInfoService {
    String fetchInfo(String text);
}
