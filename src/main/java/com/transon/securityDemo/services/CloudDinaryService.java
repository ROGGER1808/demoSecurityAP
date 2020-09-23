package com.transon.securityDemo.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.transon.securityDemo.responseModel.ResponseUploadFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudDinaryService {

    private final Cloudinary cloudinary;

    public CloudDinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public ResponseUploadFile uploadFile(MultipartFile file){
        Map<String, String> options = new HashMap<>();
        options.put("folder","inventory");
        try {
            File myfile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(myfile, options);
            String url = uploadResult.get("url").toString();
            String publicId = uploadResult.get("public_id").toString();
            return new ResponseUploadFile(url, publicId);
        } catch (IOException e) {
            throw new RuntimeException(e + "Error upload file!");
        }
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    public boolean destroyFile(String public_id){
        try {
            if (!public_id.isEmpty()){
                Map result = cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
                if (result.get("result").equals("not found")){
                    throw new RuntimeException("Error destroy file!");
                }
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e + "Error destroy file!");
        }
    }
}
