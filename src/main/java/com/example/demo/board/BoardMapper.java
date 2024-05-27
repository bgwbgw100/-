package com.example.demo.board;


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
            FROM BOARD
            WHERE board_kind = #{kind}
            AND   delete_ox = 'X'
            ORDER BY board_kind, board_number DESC
            LIMIT 10 OFFSET #{page};
            """)
    public List<BoardDTO> getBoardList(@Param("kind") String Kind, @Param("page") int page);


    @Insert("""
            insert into BOARD
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
                BOARD
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
    """)
    BoardDTO detailBoard(@Param("data") BoardDTO boardDTO);


    @Delete("""
    DELETE FROM board
    WHERE board_kind = #{data.boardKind}
    AND   board_number= #{data.boardNumber}
    AND   id = #{data.id}
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
    """)
    void updateBoard(@Param("data") BoardDTO boardDTO);

    @Update("""
    
    UPDATE board
    SET     views = views+1
    WHERE board_number = #{data.boardNumber}
    AND   board_kind = #{data.boardKind}
    """)
    void updateViews(@Param("data") BoardDTO boardDTO);



}
