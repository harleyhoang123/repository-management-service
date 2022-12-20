package vn.edu.fpt.repository.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/12/2022 - 14:07
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Getter
@RequiredArgsConstructor
public enum RepositoryRoleEnum {

    OWNER("OWNER"),
    MANAGER("MANAGER"),
    MEMBER("MEMBER");

    private final String role;
}
