package vn.edu.fpt.repository.dto.request.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.repository.dto.common.CreateFileRequest;

import java.io.Serializable;

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
public class AddFileToFolderRequest implements Serializable {

    private static final long serialVersionUID = 6122689413136882722L;
    private CreateFileRequest file;
    private String description;
}
