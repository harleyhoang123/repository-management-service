package vn.edu.fpt.repository.dto.request.folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

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
public class UpdateFolderRequest implements Serializable {
    private static final long serialVersionUID = 5455592805095253334L;
    private String folderName;
    private String description;



}
