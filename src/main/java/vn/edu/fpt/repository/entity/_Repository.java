package vn.edu.fpt.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.repository.entity.common.Auditor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:36
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "repositories")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class _Repository extends Auditor {

    private static final long serialVersionUID = -8706257655691576745L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String repositoryId;
    @Field(name = "project_id")
    private String projectId;
    @Field(name = "original_path")
    private String originalPath;
    @Field(name = "repository_name")
    private String repositoryName;
    @Field(name = "description")
    private String description;
    @Field(name = "folders")
    @DBRef(lazy = true)
    @Builder.Default
    private List<Folder> folders = new ArrayList<>();
}
