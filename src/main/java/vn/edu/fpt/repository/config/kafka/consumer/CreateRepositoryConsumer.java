package vn.edu.fpt.repository.config.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.edu.fpt.repository.service.RepositoryService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/12/2022 - 10:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
public class CreateRepositoryConsumer extends Consumer{

    private final RepositoryService repositoryService;

    @Override
    @KafkaListener(id = "createRepositoryConsumer", topics = "flab.repository.create-repository", groupId = "flab.repository")
    protected void listen(String value, String topic, String key) {
        super.listen(value, topic, key);
        repositoryService.createRepository(value);
    }
}
