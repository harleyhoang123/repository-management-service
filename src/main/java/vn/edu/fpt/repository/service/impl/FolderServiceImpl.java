package vn.edu.fpt.repository.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.common.UserInfoResponse;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.UpdateFolderRequest;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.CreateFolderResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;
import vn.edu.fpt.repository.entity.Folder;
import vn.edu.fpt.repository.entity._File;
import vn.edu.fpt.repository.entity._Repository;
import vn.edu.fpt.repository.exception.BusinessException;
import vn.edu.fpt.repository.repository.FileRepository;
import vn.edu.fpt.repository.repository.FolderRepository;
import vn.edu.fpt.repository.repository._RepositoryRepository;
import vn.edu.fpt.repository.service.FolderService;
import vn.edu.fpt.repository.service.S3BucketStorageService;
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
@Slf4j
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final AmazonS3 amazonS3;
    private final FolderRepository folderRepository;
    private final _RepositoryRepository repositoryRepository;
    private final S3BucketStorageService s3BucketStorageService;

    private final FileRepository fileRepository;
    private final UserInfoService userInfoService;
    @Value("${application.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public CreateFolderResponse createFolderInRepository(String repositoryId, CreateFolderRequest request) {
        _Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Repository ID not exist"));

        String path = String.format("%s%s", repository.getOriginalPath(), DataUtils.getFolderKey(request.getFolderName()));

        Folder folder = Folder.builder()
                .folderName(request.getFolderName())
                .description(request.getDescription())
                .folderKey(path)
                .build();

        try {
            folder = folderRepository.save(folder);
        } catch (Exception ex) {
            throw new BusinessException("Can't save folder to database: " + ex.getMessage());
        }

        List<Folder> currentFolder = repository.getFolders();
        currentFolder.add(folder);
        repository.setFolders(currentFolder);
        try {
            repositoryRepository.save(repository);
        } catch (Exception ex) {
            throw new BusinessException("Can't save repository to database: " + ex.getMessage());
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, emptyContent, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new BusinessException("Can't create folder in aws: " + ex.getMessage());
        }

        return CreateFolderResponse.builder()
                .folderId(folder.getFolderId())
                .build();
    }

    @Override
    public CreateFolderResponse createFolderInFolder(String folderId, CreateFolderRequest request) {
        Folder folderParent = folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder ID not exist"));

        String path = String.format("%s%s", folderParent.getFolderKey(), DataUtils.getFolderKey(request.getFolderName()));

        Folder folder = Folder.builder()
                .folderName(request.getFolderName())
                .description(request.getDescription())
                .folderKey(path)
                .build();

        try {
            folder = folderRepository.save(folder);
        } catch (Exception ex) {
            throw new BusinessException("Can't save folder to database: " + ex.getMessage());
        }

        List<Folder> currentFolder = folderParent.getFolders();

        currentFolder.add(folder);

        folderParent.setFolders(currentFolder);
        try {
            folderRepository.save(folderParent);
        } catch (Exception ex) {
            throw new BusinessException("Can't update folder parent in database: " + ex.getMessage());
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, emptyContent, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new BusinessException("Can't create folder in aws: " + ex.getMessage());
        }

        return CreateFolderResponse.builder()
                .folderId(folder.getFolderId())
                .build();
    }

    @Override
    public void updateFolder(String folderId, UpdateFolderRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder id not found"));
        if (Objects.nonNull(request.getFolderName())) {
            if (folderRepository.findByFolderName(request.getFolderName()).isPresent()) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder name already in database");
            }
            log.info("Update folder name: {}", request.getFolderName());
            folder.setFolderName(request.getFolderName());
        }
        try {
            folderRepository.save(folder);
            log.info("Update folder success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save folder in database when update folder: " + ex.getMessage());
        }
    }

    @Override
    public void deleteFolder(String folderId) {
        folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder ID not found"));
        try {
            folderRepository.deleteById(folderId);
            log.info("Delete folder: {} success", folderId);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete folder by ID: " + ex.getMessage());
        }
    }


    @Override
    public GetFolderDetailResponse getFolderDetail(String folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "folder ID not found"));
        List<Folder> folders = folder.getFolders();
        List<_File> files = folder.getFiles();
        return GetFolderDetailResponse.builder()
                .listFolder(folders.stream().map(this::convertToFolderResponse).collect(Collectors.toList()))
                .listFile(files.stream().map(this::convertToFileResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageableResponse<GetFolderResponse> getFolderByRepositoryId(String repositoryId) {
        _Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Repository ID not found"));
        List<Folder> folders = repository.getFolders();
        List<GetFolderResponse> folderResponses = folders.stream().map(this::convertToFolderResponse).collect(Collectors.toList());
        return new PageableResponse<>(folderResponses);
    }

    @Override
    public void deleteFolderInRepository(String repositoryId, String folderId) {
        _Repository repository = repositoryRepository.findById(repositoryId)
                        .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Repository Id not exist when delete folder in repository: "+ repositoryId));
        List<Folder> folders = repository.getFolders();
        Folder folder = folders.stream()
                .filter(v -> v.getFolderId().equals(folderId))
                .findAny()
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder Id not exist in repository"));
        folder.getFolders().forEach(v -> this.deleteFolderInFolder(folderId, v.getFolderId()));
        folder.getFiles().forEach(v -> this.deleteFileByFileId(v.getFileId()));

        if(folders.removeIf(v -> v.getFolderId().equals(folderId))){
            try {
                repository.setFolders(folders);
                repositoryRepository.save(repository);

                folderRepository.deleteById(folderId);

                log.info("Delete folder: {} success", folderId);
            } catch (Exception ex) {
                throw new BusinessException("Can't delete folder by Id: " + ex.getMessage());
            }
        }else{
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder Id: "+folderId+" not exist in repository: "+ repositoryId);
        }

    }

    private void deleteFileByFileId(String fileId){
        try {
            fileRepository.deleteById(fileId);
        }catch (Exception ex){
            throw new BusinessException("Can't delete file by id: "+ ex.getMessage());
        }
    }

    @Override
    public void deleteFolderInFolder(String folderId, String subFolderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder Id not exist: "+ folderId));
        List<Folder> folders = folder.getFolders();
        if(folders.removeIf(v -> v.getFolderId().equals(subFolderId))){
            try {
                folder.setFolders(folders);
                folderRepository.save(folder);
            }catch (Exception ex){
                throw new BusinessException("Can't update folder after delete sub folder in database: "+ ex.getMessage());
            }try {
                folderRepository.deleteById(subFolderId);
            }catch (Exception ex){
                throw new BusinessException("Can't delete folder: "+ ex.getMessage());
            }
        }else{
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder Id: "+subFolderId+" not exist in folder: "+ folderId);
        }
    }

    private GetFolderResponse convertToFolderResponse(Folder folder) {
        if (folder == null) {
            return null;
        }
        return GetFolderResponse.builder()
                .folderId(folder.getFolderId())
                .folderName(folder.getFolderName())
                .description(folder.getDescription())
                .lastModifiedDate(folder.getLastModifiedDate())
                .build();
    }

    private GetFileDetailResponse convertToFileResponse(_File file) {
        if (file == null) {
            return null;
        }

        return GetFileDetailResponse.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .description(file.getDescription())
                .publicURL(s3BucketStorageService.getPublicURL(file.getFileKey()))
                .size(file.getSize())
                .type(file.getType())
                .createdBy(UserInfoResponse.builder()
                        .accountId(file.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(file.getCreatedBy()))
                        .build())
                .createdDate(file.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(file.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(file.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(file.getLastModifiedDate())
                .build();

    }

}
