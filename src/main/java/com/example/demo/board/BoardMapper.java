package com.example.demo.board;


import com.example.demo.mybatis.CustomPagingLanguageDriver;
import com.example.demo.util.CommonPagingDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {


    @Select("""
            SELECT  board_number as boardNumber
                    ,board_kind as boardKind
                    ,id 
                    ,regist_time as registTime
                    ,title
                    ,attachment_ox as attachmentOx
                    ,views
            FROM board
            WHERE board_kind = #{kind}
            AND   delete_ox = 'X'
            AND   blind_ox = 'X'
            ORDER BY board_kind, board_number DESC
            LIMIT 10 OFFSET #{page};
            """)
    public List<BoardDTO> getBoardList(@Param("kind") String Kind, @Param("page") int page);


    @Insert("""
            insert into board
            ( 
            board_number
            ,board_kind
            ,id
            ,regist_time
            ,delete_time
            ,update_time
            ,title
            ,content
            ,attachment_ox
            ,delete_ox
            ,attachment_code
            )
            SELECT
                IFNULL(MAX(board_number), 0) + 1,
                #{data.boardKind},
                #{data.id},
                NOW(),
                NOW(),
                NOW(),
                #{data.title},
                #{data.content},
                #{data.attachmentOx},
                'X',
                 IF(#{data.attachmentCode} = 0, NULL, #{data.attachmentCode})
            FROM
                board
            WHERE
                board_kind = #{data.boardKind};
            """)
    public void createBoard(@Param("data") BoardDTO board);


    @Select("""
        SELECT board_number as boardNumber
              ,board_kind as boardKind
              ,id
              ,regist_time as registTime
              ,delete_time as deleteTime
              ,update_time as updateTime
              ,title
              ,content
              ,attachment_ox as attachmentOx
              ,views
              ,attachment_code as attachmentCode
              ,delete_ox as deleteOx
        FROM board
        WHERE board_number = #{data.boardNumber}
        AND   board_kind = #{data.boardKind}
        AND   delete_ox = 'X'
        AND   blind_ox = 'X'
    """)
    BoardDTO detailBoard(@Param("data") BoardDTO boardDTO);


    @Delete("""
    DELETE FROM board
    WHERE board_kind = #{data.boardKind}
    AND   board_number= #{data.boardNumber}
    AND   id = #{data.id}
    AND   blind_ox = 'X'
    """)
    void deleteBoard(@Param("data") BoardDTO boardDTO);

    @Update("""
    UPDATE board
    SET     title = #{data.title}
            ,content = #{data.content}
            ,update_time = NOW()
            ,attachment_ox =  #{data.attachmentOx}
            ,attachment_code =  IF(#{data.attachmentCode} = 0 ,NULL, #{data.attachmentCode})
    WHERE board_number = #{data.boardNumber}
    AND   board_kind = #{data.boardKind}
    AND   id = #{data.id}
    AND   blind_ox = 'X'
    """)
    void updateBoard(@Param("data") BoardDTO boardDTO);

    @Update("""
    
    UPDATE board
    SET     views = views+1
    WHERE board_number = #{data.boardNumber}
    AND   board_kind = #{data.boardKind}
    AND   blind_ox = 'X'
    """)
    void updateViews(@Param("data") BoardDTO boardDTO);

    @Select("""
    SELECT  board_number as boardNumber
            ,board_kind as boardKind
            ,id 
            ,regist_time as registTime
            ,title
            ,attachment_ox as attachmentOx
            ,views
            ,blind_ox as blindOx
            ,blind_time as blindTime
    FROM board
    WHERE board_kind = #{kind}
    ORDER BY board_kind, board_number DESC
    LIMIT 10 OFFSET #{page}
    """)
    List<BoardDTO> selectBoardListByKind(@Param("kind") String kind,@Param("page")int page);

    @Select("""
    SELECT  COUNT(*)
    FROM board
    WHERE board_kind = #{kind}
    """)
    int selectBoardCountByKind(@Param("kind") String kind);

    @Update("""
    UPDATE  board
    SET     blind_ox = #{data.blindOx}
    WHERE   board_kind = #{data.boardKind}
    AND     board_number = #{data.boardNumber}
    """)
    void updateBoardBlind(@Param("data") BoardDTO boardDTO);

    @Select("""
            SELECT  board_number as boardNumber
                    ,board_kind as boardKind
                    ,id 
                    ,regist_time as registTime
                    ,title
                    ,attachment_ox as attachmentOx
                    ,views
            FROM BOARD
            WHERE board_kind = #{kind}
            AND   delete_ox = 'X'
            AND   blind_ox = 'X'
            ORDER BY board_kind, board_number DESC
            """)
    @Lang(CustomPagingLanguageDriver.class)
    List<BoardDTO> selectTestPaging(@Param("page") int page,@Param("kind") String kind);

    @Select("""
            SELECT  board_number as boardNumber
                    ,board_kind as boardKind
                    ,id 
                    ,regist_time as registTime
                    ,title
                    ,attachment_ox as attachmentOx
                    ,views
            FROM board
            WHERE board_kind = 'notice'
            AND   delete_ox = 'X'
            AND   blind_ox = 'X'
            ORDER BY board_kind, board_number DESC
            """)
    List<BoardDTO> selectTestPagingInterceptor(CommonPagingDTO commonPagingDTO);

    @Select("""
            SELECT  board_number as boardNumber
                    ,board_kind as boardKind
                    ,id 
                    ,regist_time as registTime
                    ,title
                    ,attachment_ox as attachmentOx
                    ,views
            FROM board
            WHERE board_kind = 'notice'
            AND   delete_ox = 'X'
            AND   blind_ox = 'X'
            ORDER BY board_kind, board_number DESC
            """)
    List<BoardDTO> selectTestPagingInterceptor2(@Param("page") CommonPagingDTO commonPagingDTO,String kind);

}
