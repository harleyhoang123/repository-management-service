package vn.edu.fpt.repository.service;

import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.repository.CreateRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.GetRepositoryRequest;
import vn.edu.fpt.repository.dto.request.repository.UpdateRepositoryRequest;
import vn.edu.fpt.repository.dto.response.repository.CreateRepositoryResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryDetailResponse;
import vn.edu.fpt.repository.dto.response.repository.GetRepositoryResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:26
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface RepositoryService {

    CreateRepositoryResponse createRepository(CreateRepositoryRequest request);

    void updateRepository(String repositoryId, UpdateRepositoryRequest request);

    void deleteRepository(String repositoryId);

    PageableResponse<GetRepositoryResponse> getRepository(GetRepositoryRequest request);

    GetRepositoryDetailResponse getRepositoryDetail(String repositoryId);
}
