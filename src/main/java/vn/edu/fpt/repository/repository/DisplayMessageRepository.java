package vn.edu.fpt.repository.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.repository.entity.DisplayMessage;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:45
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface DisplayMessageRepository {

    Optional<DisplayMessage> findByCodeAndLanguage(String code, String language);

}
