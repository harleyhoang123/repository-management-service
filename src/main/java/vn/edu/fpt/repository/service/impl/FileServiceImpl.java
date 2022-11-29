package vn.edu.fpt.repository.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.file.CreateFileRequest;
import vn.edu.fpt.repository.dto.request.file.GetFileRequest;
import vn.edu.fpt.repository.dto.request.file.UpdateFileRequest;
import vn.edu.fpt.repository.dto.response.file.CreateFileResponse;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;
import vn.edu.fpt.repository.dto.response.file.GetFileResponse;
import vn.edu.fpt.repository.entity.Folder;
import vn.edu.fpt.repository.entity._File;
import vn.edu.fpt.repository.exception.BusinessException;
import vn.edu.fpt.repository.factory.ResponseFactory;
import vn.edu.fpt.repository.repository.FileRepository;
import vn.edu.fpt.repository.repository.FolderRepository;
import vn.edu.fpt.repository.service.FileService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class FileServiceImpl implements FileService {

    private final AmazonS3 amazonS3;
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    @Value("${application.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public CreateFileResponse addFileToFolder(String folderId, CreateFileRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Folder ID not exist"));

        String fileName = request.getFile().getOriginalFilename();
        String path = String.format("%s%s", folder.getFullPath(), fileName);

        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        try (OutputStream os = Files.newOutputStream(Path.of(convFile.getPath()))) {
            os.write(request.getFile().getBytes());

            _File file = _File.builder()
                    .fileName(fileName)
                    .description(request.getDescription())
                    .fullPath(path)
                    .build();
            try {
                file = fileRepository.save(file);
            } catch (Exception ex) {
                throw new BusinessException("");
            }

            List<_File> currentFile = folder.getFiles();
            currentFile.add(file);
            folder.setFiles(currentFile);
            try {
                folderRepository.save(folder);
            } catch (Exception ex) {
                throw new BusinessException("");
            }

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(convFile.length());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, convFile);
            putObjectRequest.setMetadata(metadata);
            try {
                amazonS3.putObject(putObjectRequest);
            }catch (Exception ex){
                throw new BusinessException("Can't create folder in aws: "+ ex.getMessage());
            }

            return CreateFileResponse.builder()
                    .fileId(file.getFileId())
                    .build();
        } catch (Exception ex) {
            throw new BusinessException("");
        } finally {
            try {
                Files.delete(convFile.getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new BusinessException("");
            }
        }
    }

    @Override
    public void updateFile(String fileId, UpdateFileRequest request) {

    }

    @Override
    public void deleteFile(String fileId) {

    }

    @Override
    public PageableResponse<GetFileResponse> getFile(GetFileRequest request) {
        return null;
    }

    @Override
    public GetFileDetailResponse getFileDetail(String fileId) {
        return null;
    }
}
