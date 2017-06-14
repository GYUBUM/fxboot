package com.flexink.common.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.flexink.common.file.service.CommonFileService;
import com.flexink.domain.file.CommonFile;
import com.flexink.vo.ParamsVo;
import com.flexink.vo.file.UploadParameters;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/files")
public class FileUploadController {

    @Autowired
    private CommonFileService commonFileService;
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CommonFile upload(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "targetType", required = false) String targetType,
            @RequestParam(value = "targetId", required = false) String targetId,
            @RequestParam(value = "sort", required = false, defaultValue = "0") int sort,
            @RequestParam(value = "deleteIfExist", required = false, defaultValue = "false") boolean deleteIfExist,
            @RequestParam(value = "desc", required = false) String desc,
            @RequestParam(value = "thumbnail", required = false, defaultValue = "false") boolean thumbnail,
            @RequestParam(value = "thumbnailWidth", required = false, defaultValue = "150") int thumbnailWidth,
            @RequestParam(value = "thumbnailHeight", required = false, defaultValue = "150") int thumbnailHeight) throws IOException {
    	
    	
        UploadParameters uploadParameters = new UploadParameters();
        uploadParameters.setMultipartFile(multipartFile);
        uploadParameters.setTargetType(targetType);
        uploadParameters.setTargetId(targetId);
        uploadParameters.setSort(sort);
        uploadParameters.setDeleteIfExist(deleteIfExist);
        uploadParameters.setDesc(desc);
        uploadParameters.setThumbnail(thumbnail);
        uploadParameters.setThumbnailWidth(thumbnailWidth);
        uploadParameters.setThumbnailHeight(thumbnailHeight);
        
        return commonFileService.upload(uploadParameters);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=UTF-8")	
    @ResponseBody
    public Page<CommonFile> list(ParamsVo paramsVo) {
        Page<CommonFile> files = commonFileService.getList(paramsVo);
        return files;
    }
    
    @DeleteMapping
    public ResponseEntity<CommonFile> delete(@RequestBody List<CommonFile> files) throws IOException {
    	commonFileService.deleteFiles(files);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CommonFile> updateOrDelete(@RequestBody List<CommonFile> file) {
        commonFileService.updateOrDelete(file);
        return file;
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void preview(HttpServletResponse response, @RequestParam Long id) throws IOException {
        commonFileService.preview(response, id);
    }
    
    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public void previews(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        commonFileService.preview(response, id);
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @RequestParam Long id) throws IOException {
        commonFileService.thumbnail(response, id);
    }
    
    @RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
    public void thumbnails(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
    	commonFileService.thumbnail(response, id);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam Long id) throws IOException {
        return commonFileService.downloadById(id);
    }
    
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloads(@PathVariable("id") Long id) throws IOException {
    	return commonFileService.downloadById(id);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, params = {"targetType", "targetId"})
    public ResponseEntity<byte[]> downloadByTargetTypeAndTargetId(@RequestParam String targetType, @RequestParam String targetId) throws IOException {
        return commonFileService.downloadByTargetTypeAndTargetId(targetType, targetId);
    }
    
    @PostMapping(value="/ckeditorImageUplaod")
    public void ckeditorImageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "upload") MultipartFile multipartFile, ParamsVo params) throws IOException {
    	
    	UploadParameters uploadParameters = new UploadParameters();
        uploadParameters.setMultipartFile(multipartFile);
        uploadParameters.setThumbnail(false);
        CommonFile file = commonFileService.upload(uploadParameters);
        String fileUrl = request.getContextPath() + "/files/preview/" + file.getId();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        @Cleanup PrintWriter writer = response.getWriter();
        
        String print = "<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
        		+ params.getString("CKEditorFuncNum") + ",'" + fileUrl + "','이미지를 업로드 하였습니다.')</script>";
        
        writer.println(print);
        writer.flush();
    }
    
    
}
