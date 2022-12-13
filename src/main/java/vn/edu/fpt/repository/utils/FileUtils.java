package vn.edu.fpt.repository.utils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/12/2022 - 09:31
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class FileUtils {

    public static String getFileSize(Long sizeInBytes){
        if(sizeInBytes < 1024){
            return String.format("%d %s", sizeInBytes, "Byte");
        }
        double sizeInKb = (double)sizeInBytes/1024;
        if(sizeInKb < 1024){
            return String.format("%.2f %s", sizeInKb, "KB");
        }
        double sizeInMb = (double) sizeInKb / 1024;
        if(sizeInMb < 1024){
            return String.format("%.2f %s", sizeInMb, "MB");
        }
        double sizeInGb = (double) sizeInMb / 1024;
        return String.format("%.2f %s", sizeInGb, "GB");
    }
}
