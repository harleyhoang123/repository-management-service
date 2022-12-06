package vn.edu.fpt.repository.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.repository.entity.MemberInfo;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/12/2022 - 14:09
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface MemberInfoRepository extends MongoRepository<MemberInfo, String> {
}
