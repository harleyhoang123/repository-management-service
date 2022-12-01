package vn.edu.fpt.repository.dto.response.repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.repository.dto.common.AuditableResponse;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 15:53
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"repositoryId", "repositoryName", "description"})
public class GetRepositoryResponse extends AuditableResponse implements Serializable {
    private static final long serialVersionUID = -47705008137120225L;
    private String repositoryId;
    private String repositoryName;
    private String description;
}
