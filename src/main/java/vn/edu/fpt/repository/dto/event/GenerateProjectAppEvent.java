package vn.edu.fpt.repository.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/12/2022 - 11:00
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenerateProjectAppEvent implements Serializable {

    private static final long serialVersionUID = -8724387457493635242L;
    private String projectId;
    private String accountId;
    private String projectName;
}
