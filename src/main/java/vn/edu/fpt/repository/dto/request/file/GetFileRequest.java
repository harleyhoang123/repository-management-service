package vn.edu.fpt.repository.dto.request.file;

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

//@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetFileRequest extends AuditableRequest {
    private String fileId;
    private String fileName;
    private String description;

    public String getFileName() {
        return RequestDataUtils.convertSearchableData(fileName);
    }

    public ObjectId getFileId() {
        return RequestDataUtils.convertObjectId(fileId);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }
}
