package vn.edu.fpt.repository.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.repository.controller.FileController;
import vn.edu.fpt.repository.dto.common.GeneralResponse;
import vn.edu.fpt.repository.dto.common.PageableResponse;
import vn.edu.fpt.repository.dto.request.file.CreateFileRequest;
import vn.edu.fpt.repository.dto.request.file.UpdateFileRequest;
import vn.edu.fpt.repository.dto.response.file.CreateFileResponse;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;
import vn.edu.fpt.repository.dto.response.file.GetFileResponse;
import vn.edu.fpt.repository.factory.ResponseFactory;
import vn.edu.fpt.repository.service.FileService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 18:31
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class FileControllerImpl implements FileController {

    private final ResponseFactory responseFactory;
    private final FileService fileService;

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateFile(String fileId, UpdateFileRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteFile(String fileId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetFileResponse>>> getFile() {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<GetFileDetailResponse>> getFileDetail(String fileId) {
        return null;
    }
}
