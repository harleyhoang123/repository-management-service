package vn.edu.fpt.repository.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.repository.CreateRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.GetRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.UpdateRepositoryRequest;
import vn.edu.fpt.repository.dto.response.repository.CreateRepositoryResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryDetailResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryResponse;
import vn.edu.fpt.repository.entity._Repository;
import vn.edu.fpt.repository.exception.BusinessException;
import vn.edu.fpt.repository.repository._RepositoryRepository;
import vn.edu.fpt.repository.service.RepositoryService;
import vn.edu.fpt.repository.utils.DataUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

    @Value("${application.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public CreateRepositoryResponse createRepository(CreateRepositoryRequest request) {
        _Repository repository = _Repository.builder()
                .projectId(request.getProjectId())
                .repositoryName(request.getRepositoryName())
                .description(request.getDescription())
                .build();
        try {
            repository = repositoryRepository.save(repository);
        }catch (Exception ex){
            throw new BusinessException("Can't save new repository to database: "+ ex.getMessage());
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, DataUtils.getFolderKey(request.getRepositoryName()), emptyContent, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        }catch (Exception ex){
            throw new BusinessException("Can't create repository in aws: "+ ex.getMessage());
        }
        return CreateRepositoryResponse.builder()
                .folderId(repository.getRepositoryId())
                .build();
    }

    @Override
    public void updateRepository(String repositoryId, UpdateRepositoryRequest request) {

    }

    @Override
    public void deleteRepository(String repositoryId) {

    }

    @Override
    public PageableResponse<GetRepositoryResponse> getRepository(GetRepositoryRequest request) {
        return null;
    }

    @Override
    public GetRepositoryDetailResponse getRepositoryDetail(String repositoryId) {
        return null;
    }
}
