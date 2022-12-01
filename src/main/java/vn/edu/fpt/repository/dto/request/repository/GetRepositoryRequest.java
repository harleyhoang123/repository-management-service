package vn.edu.fpt.repository.dto.request.repository;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.repository.dto.common.AuditableRequest;
import vn.edu.fpt.repository.utils.RequestDataUtils;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 15:56
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetRepositoryRequest extends AuditableRequest implements Serializable {

    private static final long serialVersionUID = 7863198725662680713L;
    private String repositoryId;
    private String repositoryName;
    private String description;

    public ObjectId getRepositoryId() {
        return RequestDataUtils.convertObjectId(repositoryId);
    }

    public String getRepositoryName() {
        return RequestDataUtils.convertSearchableData(repositoryName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }
}
