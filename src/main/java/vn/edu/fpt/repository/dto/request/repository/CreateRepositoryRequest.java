package vn.edu.fpt.repository.dto.request.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class CreateRepositoryRequest implements Serializable {

    private static final long serialVersionUID = 6079224555880198738L;
    private String projectId;
    private String repositoryName;
    private String description;
}
