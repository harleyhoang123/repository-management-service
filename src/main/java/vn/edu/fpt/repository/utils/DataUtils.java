package vn.edu.fpt.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 17:47
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataUtils {

    public static String getFolderKey(String folderName){
        return String.format("%s/", folderName);
    }
}
