package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int addFile(File file) {
        return fileMapper.addFile(file);
    }

    public boolean isDupilicateFileName(Integer UserId, String FileName) {
        return (fileMapper.duplicateFile(UserId, FileName) != null) ;
    }

    public List<File> UserFiles(Integer UserId) {
        return fileMapper.userfiles(UserId);
    }

    public File GetFile(Integer FileId) {
        return fileMapper.getFile(FileId);
    }

    public Integer DeleteFile(Integer FileId) {
        return fileMapper.deleteFile(FileId);
    }

}
