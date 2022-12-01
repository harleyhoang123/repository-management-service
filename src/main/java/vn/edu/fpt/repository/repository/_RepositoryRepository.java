package vn.edu.fpt.repository.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.repository.entity._File;
import vn.edu.fpt.repository.entity._Repository;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:58
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface _RepositoryRepository extends MongoRepository<_Repository, String> {
    Optional<_Repository> findByRepositoryName(String repoName);
}
