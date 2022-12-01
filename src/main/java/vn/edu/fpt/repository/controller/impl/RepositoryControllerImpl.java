package vn.edu.fpt.repository.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.controller.RepositoryController;
import vn.edu.fpt.repository.dto.common.GeneralResponse;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.repository.CreateRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.GetRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.UpdateRepositoryRequest;
import vn.edu.fpt.repository.dto.response.folder.CreateFolderResponse;
import vn.edu.fpt.repository.dto.response.repository.CreateRepositoryResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryDetailResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryResponse;
import vn.edu.fpt.repository.factory.ResponseFactory;
import vn.edu.fpt.repository.service.FolderService;
import vn.edu.fpt.repository.service.RepositoryService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 17:20
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class RepositoryControllerImpl implements RepositoryController {

    private final ResponseFactory responseFactory;
    private final RepositoryService repositoryService;
    private final FolderService folderService;

    @Override
    public ResponseEntity<GeneralResponse<CreateRepositoryResponse>> createRepository(CreateRepositoryRequest request) {
        return responseFactory.response(repositoryService.createRepository(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateFolderResponse>> createFolderInRepository(String repositoryId, CreateFolderRequest request) {
        return responseFactory.response(folderService.createFolderInRepository(repositoryId, request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateRepository(String repositoryId, UpdateRepositoryRequest request) {
        repositoryService.updateRepository(repositoryId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteRepository(String repositoryId) {
        repositoryService.deleteRepository(repositoryId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetRepositoryResponse>>> getRepository() {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<GetRepositoryDetailResponse>> getRepositoryDetail(String repositoryId) {
        return null;
    }
}
