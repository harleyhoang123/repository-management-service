package vn.edu.fpt.repository.dto.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.repository.utils.RequestDataUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ToString
@SuperBuilder(toBuilder = true)
public abstract class ApprovalRequest extends AuditableRequest implements Serializable {

    private static final long serialVersionUID = -4546419306721595675L;
    protected String approvedBy;
    protected String approvedDateFrom;

    protected String approvedDateTo;

    public String getApprovedBy() {
        return RequestDataUtils.convertSearchableData(approvedBy);
    }

    public LocalDateTime getApprovedDateFrom() {
        return RequestDataUtils.convertDateFrom(approvedDateFrom);
    }

    public LocalDateTime getApprovedDateTo() {
        return RequestDataUtils.convertDateTo(approvedDateTo);
    }
}
