package vn.edu.fpt.repository.dto.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 02:25
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public abstract class ApprovalResponse extends AuditableResponse{

    private static final long serialVersionUID = 2686124803741357495L;
    protected String status;
    protected String approvedBy;
    protected String approvedDate;
}
