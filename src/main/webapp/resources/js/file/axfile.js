/**
 * 
 */
var API_SERVER = contextPath;
var axFileConfig = {
	fileLength: 10,
	fileSize: 20,
	fileLengthErrMsg: "You can not upload more than {0} files.",
	fileSizeErrMsg: "You can not upload more than file Size {0}",
	fileDeleteMsg: "Are you sure you want to delete it?",
	fileTypeMsg: "Type check failed.",
	fileType: [],
    debug: true,
    target: $('[data-ax5uploader="upload1"]'),
    form: {
        action: API_SERVER + "/files/upload?thumbnail=true",
        fileName: "file"
    },
    multiple: true,
    manualUpload: false,
    progressBox: true,
    progressBoxDirection: "left",
    dropZone: {
        target: $('[data-uploaded-box="upload1"]')
    },
    uploadedBox: {
        target: $('[data-uploaded-box="upload1"]'),
        icon: {
            "download": '<i class="fa fa-download" aria-hidden="true"></i>',
            "delete": '<i class="fa fa-minus-circle" aria-hidden="true"></i>'
        },
        columnKeys: {
            apiServerUrl: API_SERVER,
            name: "fileNm",
            type: "extension",
            size: "fileSize",
            uploadedName: "saveNm",
            thumbnail: "thumbnail"
        },
        lang: {
            supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">Drop files here or click to upload.</div>',
            emptyListMsg: '<div class="text-center" style="padding-top: 30px;">Empty of List.</div>'
        },
        onchange: function () {
 
        },
        onclick: function () {
            var fileIndex = this.fileIndex;
            var file = this.uploadedFiles[fileIndex];
            switch (this.cellType) {
                case "delete":
		            DIALOG.confirm({
		                title: "",
		                msg: axFileConfig.fileDeleteMsg,
		            }, function () {
		                if (this.key == "ok") {
		                    $.ajax({
		                        contentType: "application/json",
		                        method: "DELETE",
		                        url: API_SERVER + "/files",
		                        data: JSON.stringify([{
		                            id: file.id
		                        }]),
		                        success: function (res) {
		                            if (res.error) {
		                                alert(res.error.message);
		                                return;
		                            }
		                            UPLOAD.removeFile(fileIndex);
		                        }
		                    });
		                }
		            });
		            break;
                case "download":
		            if (file.download) {
		                location.href = API_SERVER + file.download;
		            }
		            break;
            }
        }
    },
    validateSelectedFiles: function () {
    	// 파일타입 체크
    	if(this.selectedFiles.length > 0) {
    		var selectedFiles = this.selectedFiles;
    		
    		var arr = _.filter(selectedFiles, function(file) {
    			var filterResult = false;
    			if(axFileConfig.fileType.length > 0) {
    				var typeCheck = false;
        			_.forEach(axFileConfig.fileType, function(t) {
        				if(!typeCheck && _.lowerCase(file.type).indexOf(_.lowerCase(t)) > -1) {
        					typeCheck = true;
        				}
        			});
        			filterResult = !typeCheck;
    			}
    			return filterResult;
    		});
    		if(arr.length > 0) {
    			var message = String.format(axFileConfig.fileTypeMsg);
                alert(message);
    			return false;
    		}
    	}
    	// 파일 사이즈 체크
    	if (this.selectedFiles.length > 0) {
    		var selectedFiles = this.selectedFiles;
    		var arr = _.filter(selectedFiles, function(file) {
    			var check = false;
    			if(file.size > axFileConfig.fileSize*1024*1024) {
    				check = true;
    			}
    			return check;
    		});
    		if(arr.length > 0 ) {
    			var message = String.format(axFileConfig.fileSizeErrMsg, axFileConfig.fileSize);
                alert(message);
                return false;
    		}
    	}
    	// 파일 갯수 체크
        if (this.uploadedFiles.length + this.selectedFiles.length > axFileConfig.fileLength) {
        	var message = String.format(axFileConfig.fileLengthErrMsg, axFileConfig.fileLength);
            alert(message);
            return false;
        }
        
        return true;
    },
    onprogress: function () {
 
    },
    onuploaderror: function () {
    	console.log(this);
        console.log(this.error);
        DIALOG.alert(this.error.message);
    },
    onuploaded: function () {
    },
    onuploadComplete: function () {
 
    }
}

var axfile = {
	init: function() {
		DIALOG = new ax5.ui.dialog({
            title: ""
        }),
        UPLOAD = new ax5.ui.uploader(axFileConfig)
	},
	setUploadedFiles: function(params) {
		$.ajax({
            method: "GET",
            url: API_SERVER + "/files",
            data: params,
            contentType: "application/json",
            success: function (res) {
                UPLOAD.setUploadedFiles(res.content);
            }
        });
	},
	getUploadFiles: function() {
		return ax5.util.deepCopy(UPLOAD.uploadedFiles);
	},
	getUploadedFileIds: function() {
		var fileIds = [];
        UPLOAD.uploadedFiles.forEach(function (f) {
        	fileIds.push(f.id);
        });
        return {fileIds : fileIds};
	},
	deleteFiles: function() {
		var deleteFiles = [];
        UPLOAD.uploadedFiles.forEach(function (f) {
            deleteFiles.push({id: f.id});
        });
        $.ajax({
            contentType: "application/json",
            method: "DELETE",
            url: API_SERVER + "/files",
            data: JSON.stringify(deleteFiles),
            success: function (res) {
                if (res.error) {
                    alert(res.error.message);
                    return;
                }
                UPLOAD.removeFileAll();
            }
        });
	}
}

String.format = function() {
    var theString = arguments[0];
    
    for (var i = 1; i < arguments.length; i++) {
        var regEx = new RegExp("\\{" + (i - 1) + "\\}", "gm");
        theString = theString.replace(regEx, arguments[i]);
    }
    
    return theString;
}