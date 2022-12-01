package vn.edu.fpt.repository.service;

import vn.edu.fpt.repository.constant.ResponseStatusEnum;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.folder.CreateFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.GetFolderRequest;
import vn.edu.fpt.repository.dto.request.folder.UpdateFolderRequest;
import vn.edu.fpt.repository.dto.response.folder.CreateFolderResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderDetailResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:26
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface FolderService {

    CreateFolderResponse createFolderInRepository(String repositoryId, CreateFolderRequest request);

    CreateFolderResponse createFolderInFolder(String folderId, CreateFolderRequest request);

    void updateFolder(String folderId, UpdateFolderRequest request);

    void deleteFolder(String folderId);

    GetFolderDetailResponse getFolderDetail(String folderId);

    PageableResponse<GetFolderResponse> getFolderByRepositoryId(String repositoryId);
}
