package com.example.demo.file;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    @Insert("""
        insert INTO attachment
        (
        file_name
        ,org_file_name
        ,upload_date
        )
        VALUES
        (
        #{data.fileName}
        ,#{data.orgFileName}
        ,NOW()
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "attachmentCode")
    void fileInsert(@Param("data") FileDTO fileDTO);

    @Select("""
        SELECT  attachment_code as attachmentCode
                ,file_name as fileName
                ,org_file_name as orgFileName
                ,upload_date as uploadDate
        FROM attachment
        WHERE attachment_code = #{code}
    """)
    FileDTO fileSelect(@Param("code") int code);

    @Delete("""
        DELETE FROM attachment
        WHERE attachment_code = #{code}
    """)
    void  deleteFile(@Param("code") int code);


}
