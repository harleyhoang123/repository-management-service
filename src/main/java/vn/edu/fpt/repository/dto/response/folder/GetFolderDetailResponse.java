package vn.edu.fpt.repository.dto.response.folder;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.repository.dto.response.file.GetFileDetailResponse;
import vn.edu.fpt.repository.entity.Folder;

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
@JsonPropertyOrder({"folderResponses", "fileResponses"})
public class GetFolderDetailResponse implements Serializable {
    private static final long serialVersionUID = 6789006574100263413L;
    private Folder parentFolder;
    private List<GetFolderResponse> listFolder;
    private List<GetFileDetailResponse> listFile;
}
