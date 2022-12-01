package vn.edu.fpt.repository.dto.response.folder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 15:55
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"folderId","folderName"})
public class GetFolderResponse implements Serializable{
    private static final long serialVersionUID = -7918752112103180312L;
    private String folderId;
    private String folderName;
    private String description;
}
