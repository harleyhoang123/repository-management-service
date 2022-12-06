package vn.edu.fpt.repository.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.dto.common.CreateFileRequest;
import vn.edu.fpt.repository.dto.common.UserInfoResponse;
import vn.edu.fpt.repository.dto.request.file.AddFileToFolderRequest;
import vn.edu.fpt.repository.dto.request.file.UpdateFileRequest;
import vn.edu.fpt.repository.dto.response.file.AddFileToFolderResponse;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;
import vn.edu.fpt.repository.entity.Folder;
import vn.edu.fpt.repository.entity._File;
import vn.edu.fpt.repository.exception.BusinessException;
import vn.edu.fpt.repository.repository.FileRepository;
import vn.edu.fpt.repository.repository.FolderRepository;
import vn.edu.fpt.repository.service.FileService;
import vn.edu.fpt.repository.service.S3BucketStorageService;
import vn.edu.fpt.repository.service.UserInfoService;
import vn.edu.fpt.repository.utils.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final UserInfoService userInfoService;
    private final S3BucketStorageService s3BucketStorageService;

    @Override
    @Transactional
    public AddFileToFolderResponse addFileToFolder(String folderId, AddFileToFolderRequest request) {
        CreateFileRequest fileRequest = request.getFile();
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder ID not exist"));
        String fileKey = folder.getFolderKey() + UUID.randomUUID();
        s3BucketStorageService.uploadFile(fileRequest, fileKey);
        _File file = _File.builder()
                .fileName(fileRequest.getName())
                .description(request.getDescription())
                .fileKey(fileKey)
                .type(fileRequest.getName().split("\\.")[1])
                .length(fileRequest.getSize())
                .size(FileUtils.getFileSize(fileRequest.getSize()))
                .mimeType(fileRequest.getMimeType())
                .build();
        try {
            file = fileRepository.save(file);
            log.info("Save file success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save file to database: "+ ex.getMessage());
        }

        List<_File> currentFile = folder.getFiles();
        currentFile.add(file);
        folder.setFiles(currentFile);
        try {
            folderRepository.save(folder);
            log.info("Update folder success");
        } catch (Exception ex) {
            throw new BusinessException("Can't update folder to database: "+ ex.getMessage());
        }

        return AddFileToFolderResponse.builder()
                .fileId(file.getFileId())
                .build();
    }

    @Override
    public void updateFile(String fileId, UpdateFileRequest request) {
        _File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "File id not found"));

        if (Objects.nonNull(request.getFileName())) {
            if (fileRepository.findByFileName(request.getFileName()).isPresent()) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "File name already in database");
            }
            log.info("Update file name: {}", request.getFileName());
            file.setFileName(request.getFileName());
        }
        if (Objects.nonNull(request.getDescription())) {
            log.info("Update file description: {}", request.getDescription());
            file.setDescription(request.getDescription());
        }

        try {
            fileRepository.save(file);
            log.info("Update file success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save file in database when update file: " + ex.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileId) {
        fileRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "file ID not found"));
        try {
            fileRepository.deleteById(fileId);
            log.info("Delete file: {} success", fileId);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete file by ID: " + ex.getMessage());
        }
    }

    @Override
    public GetFileDetailResponse getFileDetail(String fileId) {
        _File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "file ID not found"));

        return GetFileDetailResponse.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .description(file.getDescription())
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

    @Override
    public void downloadFile(String fileId, HttpServletResponse response) {
        _File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "File ID not exist"));
        s3BucketStorageService.downloadFile(file, response);

    }
}
