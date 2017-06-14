package com.flexink.domain.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.flexink.common.code.FxBootType.Deleted;
import com.flexink.common.domain.BaseJpaModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_FILE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommonFile extends BaseJpaModel<Long>{
	
	public CommonFile() {
		super();
	}
	public CommonFile(String id, String targetType, Long targetId, int sort) {
		this.id = Long.valueOf(id);
		this.targetType = targetType;
		this.targetId = String.valueOf(targetId);
		this.sort = sort;
	}
	
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="TARGET_TYPE", length=50)
    //Comment(value = "타겟 TYPE")
    private String targetType;

    @Column(name="TARGET_ID", length=100)
    //Comment(value = "타겟 ID")
    private String targetId;

    @Column(name="FILE_NM")
    //Comment(value = "실제 파일명")
    private String fileNm;

    @Column(name="SAVE_NM")
    //Comment(value = "저장 파일명")
    private String saveNm;
    
    @Column(name="SAVE_PATH", length=150)
    private String savePath;

    @Column(name="FILE_TYPE", length=30)
    //Comment(value = "파일 타입")
    private String fileType;

    @Column(name="EXTENSION", length=10)
    //Comment(value = "확장자")
    private String extension;

    @Column(name="FILE_SIZE")
    //Comment(value = "파일 크기")
    private Long fileSize;

    @Column(name="DEL_YN", length=1)
    @Enumerated(EnumType.STRING)
    //Comment(value = "삭제여부")
    private Deleted delYn = Deleted.N;

    @Column(name="FILE_DESC")
    //Comment(value = "설명")
    private String desc;

    @Column(name="SORT")
    //Comment(value = "정렬")
    private Integer sort;

    @JsonIgnore
    @Transient
    private String _preview;

    @JsonIgnore
    @Transient
    private String _thumbnail;

    @JsonIgnore
    @Transient
    private String _download;

    @JsonProperty("preview")
    public String preview() {
        if (StringUtils.isEmpty(_preview)) {
            return "/files/preview?id=" + getId();
        }
        return _preview;
    }

    @JsonProperty("thumbnail")
    public String thumbnail() {
        if (StringUtils.isEmpty(_thumbnail)) {
            return "/files/thumbnail?id=" + getId();
        }
        return _thumbnail;
    }

    @JsonProperty("download")
    public String download() {
        if (StringUtils.isEmpty(_download)) {
            return "/files/download?id=" + getId();
        }
        return _download;
    }

    @Transient
    public String getThumbnailFileName() {
        return FilenameUtils.getBaseName(getSaveNm()) + "-thumbnail" + "." + FilenameUtils.getExtension(getSaveNm());
    }
    
}
