package vn.edu.fpt.repository.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.repository.dto.common.GeneralResponse;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.file.AddFileToFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.UpdateFolderRequest;
import vn.edu.fpt.repository.dto.response.file.AddFileToFolderResponse;
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

    @PostMapping(value = "/{folder-id}/file")
    ResponseEntity<GeneralResponse<AddFileToFolderResponse>> addFileToFolder(@PathVariable(name = "folder-id") String folderId, @RequestBody AddFileToFolderRequest request);

    @PutMapping("/{folder-id}")
    ResponseEntity<GeneralResponse<Object>> updateFolder(@PathVariable(name = "folder-id") String folderId, @RequestBody UpdateFolderRequest request);

    @GetMapping("/{folder-id}")
    ResponseEntity<GeneralResponse<GetFolderDetailResponse>> getFolderDetail(@PathVariable(name = "folder-id") String folderId);

    @DeleteMapping("/{folder-id}/folders/{sub-folder-id}")
    ResponseEntity<GeneralResponse<Object>> deleteFolderInFolder(@PathVariable(name = "folder-id") String folderId, @PathVariable(name = "sub-folder-id") String subFolderId);

    @DeleteMapping("/{folder-id}/files/{file-id}")
    ResponseEntity<GeneralResponse<Object>> deleteFile(@PathVariable(name = "folder-id") String folderId, @PathVariable(name = "file-id") String fileId);
}
