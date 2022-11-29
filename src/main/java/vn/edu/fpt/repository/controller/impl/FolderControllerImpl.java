package vn.edu.fpt.repository.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.controller.FolderController;
import vn.edu.fpt.repository.dto.common.GeneralResponse;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.file.CreateFileRequest;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.UpdateFolderRequest;
import vn.edu.fpt.repository.dto.response.file.CreateFileResponse;
import vn.edu.fpt.repository.dto.response.folder.CreateFolderResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;
import vn.edu.fpt.repository.factory.ResponseFactory;
import vn.edu.fpt.repository.service.FileService;
import vn.edu.fpt.repository.service.FolderService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 17:59
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class FolderControllerImpl implements FolderController {

    private final ResponseFactory responseFactory;
    private final FolderService folderService;
    private final FileService fileService;

    @Override
    public ResponseEntity<GeneralResponse<CreateFolderResponse>> createFolderInFolder(String folderId, CreateFolderRequest request) {
        return responseFactory.response(folderService.createFolderInFolder(folderId, request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateFileResponse>> addFileToFolder(String folderId, CreateFileRequest request) {
        return responseFactory.response(fileService.addFileToFolder(folderId, request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateFolder(String folderId, UpdateFolderRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetFolderResponse>>> getFolder() {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<GetFolderDetailResponse>> getFolderDetail(String folderId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteFolder(String folderId) {
        return null;
    }
}
