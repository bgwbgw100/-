package com.example.demo.mybatis;

import com.example.demo.util.CommonPagingDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        ),
})
public class QueryInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // StatementHandler 객체를 가져옴
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();


        BoundSql boundSql = statementHandler.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();

        // 파라미터가 Map인지 확인합니다. 파라미터에 이름을 지정해주거나 두 개 이상이면 Map 형태
        if (parameterObject instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) parameterObject;

            CommonPagingDTO commonPagingDTO = null;
            // Map의 각 엔트리를 검사하여 페이징 클래스 타입의 파라미터가 있는지 확인합니다.
            for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
                if (entry.getValue() instanceof CommonPagingDTO) {
                    commonPagingDTO = (CommonPagingDTO) entry.getValue();


                    String modifiedSql = "";

                    // 공통페이징을 받으면 쿼리 수정
                    String originalSql = boundSql.getSql();

                    commonPagingDTO.setQuery(applyCount(originalSql));

                    modifiedSql = applyPaging(originalSql, commonPagingDTO);

                    // 리플렉션을 사용하여 수정된 SQL을 BoundSql에 설정합니다.
                    setField(boundSql, "sql", modifiedSql);

                    break;
                }
            }


        } else if (parameterObject instanceof CommonPagingDTO) { // 파라미터로 오브젝트 하나 넣었을 때
            CommonPagingDTO commonPagingDTO = (CommonPagingDTO) parameterObject;

            String originalSql = boundSql.getSql();
            String modifiedSql = "";

            commonPagingDTO.setQuery(applyCount(originalSql));

            modifiedSql = applyPaging(originalSql, commonPagingDTO);


            setField(boundSql, "sql", modifiedSql);

        }

        // 실제 SQL 실행
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }


    private String applyPaging(String script, CommonPagingDTO commonPagingDTO) {


        // SQL에 LIMIT와 OFFSET 구문 추가
        return new StringBuilder().append(script).append(" LIMIT ").append(commonPagingDTO.getPostPage()).append(" OFFSET ").append(commonPagingDTO.getPerPage() - 1).toString();
    }
    private String applyCount(String script){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(*) AS CNT\n").append("FROM (").append(script).append(") cnt_table");
        return builder.toString();
    }
}
