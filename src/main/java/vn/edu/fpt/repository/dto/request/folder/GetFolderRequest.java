package vn.edu.fpt.repository.dto.request.folder;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.repository.dto.common.AuditableRequest;
import vn.edu.fpt.repository.utils.RequestDataUtils;

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
public class GetFolderRequest extends AuditableRequest implements Serializable{

    private String folderId;
    private String foldName;
    private String description;
    public ObjectId getFolderId() {
        return RequestDataUtils.convertObjectId(folderId);
    }
    public String getFoldName() {
        return RequestDataUtils.convertSearchableData(foldName);
    }
    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }
}
