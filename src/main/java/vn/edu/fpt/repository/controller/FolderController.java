package vn.edu.fpt.repository.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.repository.dto.common.GeneralResponse;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.file.CreateFileRequest;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.UpdateFolderRequest;
import vn.edu.fpt.repository.dto.response.file.CreateFileResponse;
import vn.edu.fpt.repository.dto.response.folder.CreateFolderResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 15:24
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/folders")
public interface FolderController {

    @PostMapping("/{folder-id}")
    ResponseEntity<GeneralResponse<CreateFolderResponse>> createFolderInFolder(@PathVariable(name = "folder-id") String folderId, @RequestBody CreateFolderRequest request);

    @PostMapping(value = "/{folder-id}/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<GeneralResponse<CreateFileResponse>> addFileToFolder(@PathVariable(name = "folder-id") String folderId, @ModelAttribute CreateFileRequest request);

    @PutMapping("/{folder-id}")
    ResponseEntity<GeneralResponse<Object>> updateFolder(@PathVariable(name = "folder-id") String folderId, @RequestBody UpdateFolderRequest request);

    @GetMapping("/{repository-id}/folders")
    ResponseEntity<GeneralResponse<PageableResponse<GetFolderResponse>>> getFolderByRepository(@PathVariable("repository-id") String repositoryId);

    @GetMapping("/{folder-id}/detail")
    ResponseEntity<GeneralResponse<GetFolderDetailResponse>> getFolderDetail(@PathVariable(name = "folder-id") String folderId);

    @DeleteMapping("/{folder-id}")
    ResponseEntity<GeneralResponse<Object>> deleteFolder(@PathVariable(name = "folder-id") String folderId);
}
