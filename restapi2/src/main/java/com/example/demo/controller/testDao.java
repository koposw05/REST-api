package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import resultVO.resultVO;

@Repository
public class testDao {
 
    @Autowired
    private JdbcTemplate template;
 
    public void insertNewContent() { //첫 데이터 생성
        template.update("INSERT INTO test(process,success,fail,total) VALUES('XMLtoJSON',0,0,0)"); 
        template.update("INSERT INTO test(process,success,fail,total) VALUES('JSONtoXML',0,0,0)");
        
    }
    
    public int getCount() { 
        return this.template.queryForObject("select count(*) from test",Integer.class);
      }
    public List<Map<String, Object>> getRead(resultVO resultVO) {
        return this.template.queryForList("select * from test order by process desc");
      }
   
    public void updateSuccessX2J() {
        template.update("update test set success = success + 1,total = success + fail  where process = 'XMLtoJSON'");
        
        //System.out.println("success sql \n" );
    }
    public void updateFailX2J() {
    	template.update("update test set fail = fail + 1,total = success + fail  where process = 'XMLtoJSON'");
        //System.out.println("fail sql \n");
    }
    public void updateSuccessJ2X() {
        template.update("update test set success = success + 1,total = success + fail where process = 'JSONtoXML'");
        
        //System.out.println("success sql \n" );
    }
    public void updateFailJ2X() {
    	template.update("update test set fail = fail + 1,total = success + fail where process = 'JSONtoXML'");
        //System.out.println("fail sql \n");
    }
}
