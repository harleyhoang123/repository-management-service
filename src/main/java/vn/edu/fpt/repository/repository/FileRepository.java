package vn.edu.fpt.repository.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.repository.entity._File;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:59
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface FileRepository extends MongoRepository<_File, String> {
}
