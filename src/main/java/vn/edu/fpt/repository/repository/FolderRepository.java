package vn.edu.fpt.repository.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.repository.entity.Folder;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 17:00
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface FolderRepository extends MongoRepository<Folder, String> {
    Optional<Folder> findByFolderName(String folderName);

}
