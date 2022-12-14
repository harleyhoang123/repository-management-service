package vn.edu.fpt.repository.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 21/11/2022 - 14:22
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/repositories")
public interface RepositoryController {

    @PostMapping("/repository")
    ResponseEntity<GeneralResponse<CreateRepositoryResponse>> createRepository(@RequestBody CreateRepositoryRequest request);

    @PostMapping("/{repository-id}/folder")
    ResponseEntity<GeneralResponse<CreateFolderResponse>> createFolderInRepository(@PathVariable(name = "repository-id") String repositoryId, @RequestBody CreateFolderRequest request);

    @PutMapping("/{repository-id}")
    ResponseEntity<GeneralResponse<Object>> updateRepository(@PathVariable(name = "repository-id") String repositoryId, @RequestBody UpdateRepositoryRequest request);

    @DeleteMapping("/{repository-id}")
    ResponseEntity<GeneralResponse<Object>> deleteRepository(@PathVariable(name = "repository-id") String repositoryId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetRepositoryResponse>>> getRepository(
            @RequestParam(name = "repository-id", required = false) String repositoryId,
            @RequestParam(name = "repository-name", required = false) String repositoryName,
            @RequestParam(name = "repository-name-sort-by", required = false) String repositoryNameSortBy,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    );

    @DeleteMapping("/{repository-id}/{folder-id}")
    ResponseEntity<GeneralResponse<Object>> deleteFolderInRepository(@PathVariable("repository-id") String repositoryId ,@PathVariable("folder-id") String folderId);

}
