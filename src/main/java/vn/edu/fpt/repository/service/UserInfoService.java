package vn.edu.fpt.repository.service;

import vn.edu.fpt.repository.dto.cache.UserInfo;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:26
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface UserInfoService {

    UserInfo getUserInfo(String accountId);
}
