package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.JSONjavamaster.JSONObject;
import com.example.JSONjavamaster.XML;

@RestController
public class RestAPIController {
	
    @RequestMapping(value="/xml2json",
    		method=RequestMethod.POST,
    		consumes="application/xml",
    		produces="application/json")
    public ResponseEntity<String> xtoj(HttpServletRequest request, //예제 파일에 ResponseEntity를 map으로 처리하던것을 string으로 변경 
			@RequestBody String xml) {
    	//Map<String,Object> map = new HashMap<String,Object>();
    	//map.put("xml2json", "success");
    	
    	JSONObject xmlJSONObj = XML.toJSONObject(xml.toString()); //xml을 json으로 변환처리 해주는 라이브러리 추가 https://github.com/stleary/JSON-java
         String jsonPrettyPrintString = xmlJSONObj.toString();
         //map.put("success",xmlJSONObj);
         //System.out.println(jsonPrettyPrintString);
         
    	System.out.println("xml2json success \n"+jsonPrettyPrintString+xml);
        return new ResponseEntity<String>(jsonPrettyPrintString, HttpStatus.OK);
          
    }
//    public static String convert(String json, String root) throws JSONException
//    {
//        JSONObject jsonFileObject = new JSONObject(json);
//        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+root+">" 
//                     + org.json.XML.toString(jsonFileObject) + "</"+root+">";
//        return xml;
//    }
    @RequestMapping(value="/json2xml",
    		method=RequestMethod.POST,
    		consumes="application/json",
    		produces="application/xml")
    public ResponseEntity<String> jtox(HttpServletRequest request, 
			@RequestBody String json) {
    	
    	//String xml = new XMLSerializer().write(xmlJSONObj);
    	 JSONObject json2Object = new JSONObject(json); //웹에서 입력한 json 데이터를 받아서 jsonobject 안에 담아서  XML로 파싱한다.
    	 String xml = "<root>" //데이터의 최상위 노드는  root로 가정한다. xml은 태그로만 변환된다.
                 + XML.toString(json2Object) + "</root>";
    	String xml1 = XML.toString(json2Object);
    	xml1 = xml1.toString();
    	 
    	System.out.println("json2xml success \n"+xml+json2Object+xml1);
        return new ResponseEntity<String>(xml1, HttpStatus.OK);
    }
    
    @RequestMapping(value="/status",
    		method=RequestMethod.GET,
    		produces="application/json")
    public ResponseEntity<Map<String,Object>> status() {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("status", "success");
        
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
    
    @RequestMapping(value="/uploadFile",
    		method=RequestMethod.POST,
    		produces="application/xml")
    public ResponseEntity<String> uploadJsonFile(HttpServletRequest request,
    		@RequestParam("file") MultipartFile file) throws IOException {
    	String json = new String(file.getBytes());
    	System.out.println("uploadJsonFile success \n" + json);
    	
        return new ResponseEntity<String>(json, HttpStatus.OK);
    }

}
