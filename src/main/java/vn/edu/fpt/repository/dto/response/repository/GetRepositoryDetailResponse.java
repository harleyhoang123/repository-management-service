package vn.edu.fpt.repository.dto.response.repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.repository.dto.common.AuditableResponse;
import vn.edu.fpt.repository.dto.response.folder.GetFolderResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 15:53
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@JsonPropertyOrder({"fileId","fileName","description","folderResponses"})
public class GetRepositoryDetailResponse extends AuditableResponse implements Serializable {
    private static final long serialVersionUID = -5041686173203792020L;
    private String repositoryId;
    private String repositoryName;
    private String description;
    private List<GetFolderResponse> folderResponses;
}
