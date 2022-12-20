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
@Document(collection = "folders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Folder extends Auditor {

    private static final long serialVersionUID = -1912384207546948792L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String folderId;
    @Field(name = "folder_name")
    private String folderName;
    @Field(name = "folder_key")
    private String folderKey;
    @Field(name = "description")
    private String description;
    @Field(name = "folders")
    @Builder.Default
    @DBRef(lazy = true)
    private List<Folder> folders = new ArrayList<>();
    @Field(name = "files")
    @Builder.Default
    @DBRef(lazy = true)
    private List<_File> files = new ArrayList<>();
}
