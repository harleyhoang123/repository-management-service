package vn.edu.fpt.repository.dto.response.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.repository.dto.common.AuditableResponse;
import vn.edu.fpt.repository.utils.RequestDataUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 15:56
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@JsonPropertyOrder({"fileId", "fileName", "publicURL","description", "type", "size"})
public class GetFileDetailResponse extends AuditableResponse {
    private static final long serialVersionUID = 4108794241636458273L;
    private String fileId;
    private String fileName;
    private String publicURL;
    private String description;
    private String size;
    private String type;
}
