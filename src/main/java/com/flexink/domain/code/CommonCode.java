package com.flexink.domain.code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.flexink.common.code.FxBootType;
import com.flexink.common.code.FxBootType.Used;
import com.flexink.common.domain.BaseJpaModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "T_COMMON_CODE")
@IdClass(CommonCodeId.class)
public class CommonCode extends BaseJpaModel<CommonCodeId> {

    @Id
    @Column(name = "GROUP_CD", length = 100)
    @NotNull(message = "분류코드를 입력하세요")
    @Size(min = 1, max = 100, message = "분류코드를 100자리 이하로 입력하세요")
    private String groupCd;

    @Column(name = "GROUP_NM", length = 100)
    @NotNull(message = "그룹명을 입력하세요")
    @Size(min = 1, max = 100, message = "그룹명을 100자리 이하로 입력하세요")
    private String groupNm;

    @Id
    @Column(name = "CODE", length = 100)
    @NotNull(message = "코드를 입력하세요")
    @Size(min = 1, max = 100, message = "코드를 100자리 이하로 입력하세요")
    private String code;

    @Column(name = "NAME", length = 50)
    @NotNull(message = "코드명을 입력하세요")
    @Size(min = 1, max = 50, message = "코드명을 50자리 이하로 입력하세요")
    private String name;

    @Column(name = "SORT", precision = 3)
    private Integer sort;

    @Column(name = "DATA1", length = 100)
    private String data1;

    @Column(name = "DATA2", length = 100)
    private String data2;

    @Column(name = "DATA3", length = 100)
    private String data3;

    @Column(name = "DATA4", precision = 10)
    private Integer data4;

    @Column(name = "DATA5", precision = 10)
    private Integer data5;

    @Column(name = "REMARK", length = 200)
    private String remark;

    @Column(name = "USE_YN", length = 1)
    @Enumerated(EnumType.STRING)
    private FxBootType.Used useYn = Used.Y;
    
    @Override
    public CommonCodeId getId() {
        return CommonCodeId.of(groupCd, code);
    }

    public static CommonCode of(String groupCd, String groupNm, String code, String name, int sort) {
        CommonCode commonCode = new CommonCode();
        commonCode.setGroupCd(groupCd);
        commonCode.setGroupNm(groupNm);
        commonCode.setCode(code);
        commonCode.setName(name);
        commonCode.setSort(sort);
        return commonCode;
    }

    @Override public boolean equals(Object obj) {

        boolean result = false;

        if (obj instanceof CommonCode) {
            CommonCode commonCode = (CommonCode) obj;
            return this.getId().equals(commonCode.getId()) ? true : false;
        }

        return result;
    }
}
