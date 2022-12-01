package vn.edu.fpt.repository.dto.response.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.repository.dto.common.AuditableResponse;
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
@SuperBuilder
@JsonPropertyOrder({"fileId", "fileName", "description"})
public class GetFileDetailResponse extends AuditableResponse implements Serializable {
    private static final long serialVersionUID = 4108794241636458273L;
    private String fileId;
    private String fileName;
    private String description;

    public ObjectId getFileId() {
        return RequestDataUtils.convertObjectId(fileId);
    }

    public String getFileName() {
        return RequestDataUtils.convertSearchableData(fileName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }
}
