package de.asiegwarth.jwtaws.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("upload")
@Data
public class UploadProperties {

	private String baseDir = "upload-dir";

}
