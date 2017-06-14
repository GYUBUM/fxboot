package com.flexink.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.flexink.common.code.FxBootType;
import com.flexink.common.context.AppContextManager;
import com.flexink.domain.code.CommonCode;
import com.flexink.sample.service.CommonCodeService;

public class CommonCodeUtils {
	
    public static List<CommonCode> get(String groupCd) {
        CommonCode param = new CommonCode();
        param.setGroupCd(groupCd);
        param.setUseYn(FxBootType.Used.Y);
        return AppContextManager.getBean(CommonCodeService.class).getList(param);
    }

    public static Map<String, List<CommonCode>> getAllByMap() {
        CommonCode param = new CommonCode();
        param.setUseYn(FxBootType.Used.Y);
        List<CommonCode> commonCodes = AppContextManager.getBean(CommonCodeService.class).getList(param);

        //Map<String, List<CommonCode>> commonCodeMap = commonCodes.stream().collect(groupingBy(CommonCode::getGroupCd));
        Map<String, List<CommonCode>> commonCodeMap = new HashMap<String, List<CommonCode>>();
        
    	
        Set<String> groupCodes = new HashSet<String>();
    	for(CommonCode code : commonCodes) {
    		groupCodes.add(code.getGroupCd());
    	}
    	
    	List<CommonCode> subList = null;
    	for(String groupCode : groupCodes) {
    		subList = new ArrayList<CommonCode>();
    		for(CommonCode commonCode : commonCodes) {
    			if(groupCode.equals(commonCode.getGroupCd())) {
    				subList.add(commonCode);
    			}
    		}
    		commonCodeMap.put(groupCode, subList);
    	}
    	
    	return commonCodeMap;
    }

    public static String getAllByJson() {
        return JsonUtils.toJson(getAllByMap());
    }


}
