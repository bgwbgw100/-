package com.example.demo.util;


public class CommonPagingExecute<T>  {


    public CustomTwoReturn<T, CommonPagingDTO> execute(CommonPagingExecuteImp<T> commonPagingExecuteImp,DynamicMapper dynamicMapper) {
        CommonPagingDTO commonPagingDTO = new CommonPagingDTO();
        T execute = commonPagingExecuteImp.execute(commonPagingDTO);
        commonPagingDTO.setExecute(true);
        int cnt = dynamicMapper.intDynamicSelect(commonPagingDTO.getQuery());
        commonPagingDTO.setCnt(cnt);
        commonPagingDTO.settingPaging();
        return  new CustomTwoReturn<T, CommonPagingDTO>(execute, commonPagingDTO);
    }

    public CustomTwoReturn<T, CommonPagingDTO> execute(CommonPagingExecuteImp<T> commonPagingExecuteImp,DynamicMapper dynamicMapper,CommonPagingDTO commonPagingDTO) {
        T execute = commonPagingExecuteImp.execute(commonPagingDTO);
        commonPagingDTO.setExecute(true);
        int cnt = dynamicMapper.intDynamicSelect(commonPagingDTO.getQuery());
        commonPagingDTO.setCnt(cnt);
        commonPagingDTO.settingPaging();
        return  new CustomTwoReturn<T, CommonPagingDTO>(execute, commonPagingDTO);
    }
}
