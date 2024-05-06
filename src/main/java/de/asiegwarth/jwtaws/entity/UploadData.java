package de.asiegwarth.jwtaws.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadData {

    @Value("file")
    private MultipartFile file;
    private String email;
    private ModelMap modelMap;
    private RedirectAttributes redirectAttributes;

    public UploadData() {
        this.modelMap = new ModelMap();
    }
    
}
