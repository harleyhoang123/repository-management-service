package vn.edu.fpt.repository.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.GetFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.UpdateFolderRequest;
import vn.edu.fpt.repository.dto.response.folder.CreateFolderResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;
import vn.edu.fpt.repository.entity.Folder;
import vn.edu.fpt.repository.entity._Repository;
import vn.edu.fpt.repository.exception.BusinessException;
import vn.edu.fpt.repository.repository.FolderRepository;
import vn.edu.fpt.repository.repository._RepositoryRepository;
import vn.edu.fpt.repository.service.FolderService;
import vn.edu.fpt.repository.utils.DataUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:42
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final AmazonS3 amazonS3;
    private final FolderRepository folderRepository;
    private final _RepositoryRepository repositoryRepository;
    @Value("${application.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public CreateFolderResponse createFolderInRepository(String repositoryId, CreateFolderRequest request) {
        _Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Repository ID not exist"));

        String path = String.format("%s%s", DataUtils.getFolderKey(repository.getRepositoryName()), DataUtils.getFolderKey(request.getFolderName()));

        Folder folder = Folder.builder()
                .folderName(request.getFolderName())
                .description(request.getDescription())
                .fullPath(path)
                .build();

        try {
            folder = folderRepository.save(folder);
        }catch (Exception ex){
            throw new BusinessException("Can't save folder to database: "+ ex.getMessage());
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, emptyContent, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        }catch (Exception ex){
            throw new BusinessException("Can't create folder in aws: "+ ex.getMessage());
        }

        return CreateFolderResponse.builder()
                .folderId(folder.getFolderId())
                .build();
    }

    @Override
    public CreateFolderResponse createFolderInFolder(String folderId, CreateFolderRequest request) {
        Folder folderParent = folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder ID not exist"));

        String path = String.format("%s%s", folderParent.getFullPath(), DataUtils.getFolderKey(request.getFolderName()));

        Folder folder = Folder.builder()
                .folderName(request.getFolderName())
                .description(request.getDescription())
                .fullPath(path)
                .build();

        try {
            folder = folderRepository.save(folder);
        }catch (Exception ex){
            throw new BusinessException("Can't save folder to database: "+ ex.getMessage());
        }

        List<Folder> currentFolder = folderParent.getFolders();

        currentFolder.add(folder);

        folderParent.setFolders(currentFolder);
        try {
            folderRepository.save(folderParent);
        }catch (Exception ex){
            throw new BusinessException("Can't update folder parent in database: "+ ex.getMessage());
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, emptyContent, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        }catch (Exception ex){
            throw new BusinessException("Can't create folder in aws: "+ ex.getMessage());
        }

        return CreateFolderResponse.builder()
                .folderId(folder.getFolderId())
                .build();
    }

    @Override
    public void updateFolder(String folderId, UpdateFolderRequest request) {

    }

    @Override
    public void deleteFolder(String folderId) {

    }

    @Override
    public PageableResponse<GetFolderResponse> getFolder(GetFolderRequest request) {
        return null;
    }

    @Override
    public GetFolderDetailResponse getFolderDetail(String folderId) {
        return null;
    }

}
