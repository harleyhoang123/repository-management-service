package vn.edu.fpt.repository.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.common.UserInfoResponse;
import vn.edu.fpt.repository.dto.request.repository.CreateRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.GetRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.UpdateRepositoryRequest;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;
import vn.edu.fpt.repository.dto.response.repository.CreateRepositoryResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryDetailResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryResponse;
import vn.edu.fpt.repository.entity.Folder;
import vn.edu.fpt.repository.entity._File;
import vn.edu.fpt.repository.entity._Repository;
import vn.edu.fpt.repository.exception.BusinessException;
import vn.edu.fpt.repository.repository.BaseMongoRepository;
import vn.edu.fpt.repository.repository._RepositoryRepository;
import vn.edu.fpt.repository.service.RepositoryService;
import vn.edu.fpt.repository.service.UserInfoService;
import vn.edu.fpt.repository.utils.DataUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:42
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class RepositoryServiceImpl implements RepositoryService {

    private final _RepositoryRepository repositoryRepository;
    private final AmazonS3 amazonS3;
    private final MongoTemplate mongoTemplate;
    private final UserInfoService userInfoService;
    @Value("${application.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public CreateRepositoryResponse createRepository(CreateRepositoryRequest request) {
        String path = String.format("%s", DataUtils.getFolderKey(request.getRepositoryName()));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, DataUtils.getFolderKey(request.getRepositoryName()), emptyContent, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        }catch (Exception ex){
            throw new BusinessException("Can't create repository in aws: "+ ex.getMessage());
        }

        _Repository repository = _Repository.builder()
                .repositoryId(request.getProjectId())
                .originalPath(path)
                .build();
        try {
            repository = repositoryRepository.save(repository);
        }catch (Exception ex){
            throw new BusinessException("Can't save new repository to database: "+ ex.getMessage());
        }
        return CreateRepositoryResponse.builder()
                .repositoryId(repository.getRepositoryId())
                .build();
    }

    @Override
    public void updateRepository(String repositoryId, UpdateRepositoryRequest request) {
        _Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Repository id not found"));

        try {
            repositoryRepository.save(repository);
            log.info("Update repository success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save repository in database when update repository: " + ex.getMessage());
        }
    }

    @Override
    public void deleteRepository(String repositoryId) {
        repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Repository ID not found"));
        try {
            repositoryRepository.deleteById(repositoryId);
            log.info("Delete repository: {} success", repositoryId);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete repository by ID: " + ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetRepositoryResponse> getRepository(GetRepositoryRequest request) {
        Query query = new Query();
        if(Objects.nonNull(request.getRepositoryId())){
            query.addCriteria(Criteria.where("_id").is(request.getRepositoryId()));
        }
        if(Objects.nonNull(request.getRepositoryName())){
            query.addCriteria(Criteria.where("repository_name").regex(request.getRepositoryName()));
        }
        if(Objects.nonNull(request.getDescription())){
            query.addCriteria(Criteria.where("description").regex(request.getDescription()));
        }

        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, _Repository.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<_Repository> repositories = mongoTemplate.find(query, _Repository.class);

        List<GetRepositoryResponse> repositoryResponses = repositories.stream().map(this::convertToRepositoryResponse).collect(Collectors.toList());

        return new PageableResponse<>(request, totalElements, repositoryResponses);
    }


    @Override
    public GetRepositoryResponse convertToRepositoryResponse(_Repository repository) {

        return GetRepositoryResponse.builder()
                .repositoryId(repository.getRepositoryId())
                .build();
    }

}
