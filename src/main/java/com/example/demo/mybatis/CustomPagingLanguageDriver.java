package com.example.demo.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.session.Configuration;

@Slf4j
public class CustomPagingLanguageDriver extends RawLanguageDriver {
    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {

        return super.createParameterHandler(mappedStatement,parameterObject,boundSql);
    }

    // xml
    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {

        log.info("xml : {}", script.getStringBody());
        return super.createSqlSource(configuration,script,parameterType);
    }

    // 주석
    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        script = applyPaging(script);


        return super.createSqlSource(configuration,script,parameterType);
    }

    private String applyPaging(String script) {
        // SQL에 LIMIT와 OFFSET 구문 추가
        return script + " LIMIT 10 OFFSET #{page}-1";
    }
}
