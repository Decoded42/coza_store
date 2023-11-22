package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.FileEntity;
import com.cybersoft.cozastore.repository.FileRepository;
import com.cybersoft.cozastore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;

@Service
public class FileService implements FileServiceImp {



    @Autowired
    private FileRepository fileRepository;
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String typeFile = multipartFile.getContentType();
        byte[] data = multipartFile.getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setType(typeFile);
        fileEntity.setData(data);

        FileEntity result = fileRepository.save(fileEntity);

        if(result != null) {
            return "upload file thành công" + fileName;
        }

        return "upload file không thành công";
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {

        FileEntity file = fileRepository.findByName(fileName);
        return file.getData();
    }

}
