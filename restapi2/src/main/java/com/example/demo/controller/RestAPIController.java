package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.JSONjava.JSONObject;
import com.example.JSONjava.XML;
import resultVO.resultVO;


@RestController
public class RestAPIController {
	@Autowired
	private testDao testDao;
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
			@RequestBody String json) throws TransformerFactoryConfigurationError, TransformerException {

		//String xml = new XMLSerializer().write(xmlJSONObj);
		JSONObject json2Object = new JSONObject(json); //웹에서 입력한 json 데이터를 받아서 jsonobject 안에 담아서  XML로 파싱한다.
		String xml = "<root>" //데이터의 최상위 노드는  root로 가정한다. xml은 태그로만 변환된다.
				+ XML.toString(json2Object) + "</root>";
		
		//json - xml xml들여쓰기
		try { //https://myshittycode.com/2014/02/10/java-properly-indenting-xml-string/
			  //https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
		    Document document = DocumentBuilderFactory.newInstance()
		            .newDocumentBuilder()
		            .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
		 
		    XPath xPath = XPathFactory.newInstance().newXPath();
		    NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
		                                                  document,
		                                                  XPathConstants.NODESET);
		 
		    for (int i = 0; i < nodeList.getLength(); ++i) {
		        Node node = nodeList.item(i);
		        node.getParentNode().removeChild(node);
		    }
		 
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		 
		    StringWriter stringWriter = new StringWriter();
		    StreamResult streamResult = new StreamResult(stringWriter);
		 
		    transformer.transform(new DOMSource(document), streamResult);
		    xml = stringWriter.toString();
		    System.out.println(stringWriter.toString());
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		
//		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		InputSource source = new InputSource(new StringReader(json));
//		
//		System.out.println("source"+source);
//		DOMResult result = new DOMResult();
//		System.out.println("result"+result);
//		transformer.transform(new SAXSource(new JsonXmlReader("namespace", true, "details"), source), result);
//		result.getNode();
//		String xml = result.toString();
//		System.out.println("result"+result.getNode().getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0));
		System.out.println("json2xml success \n"+xml+"\n"+json2Object);
		return new ResponseEntity<String>(xml, HttpStatus.OK);
	}
	
	
//	@RequestMapping(value="/status",
//			method=RequestMethod.GET,
//			produces="application/json")
//	public ResponseEntity<Map<String, Object>> status() {
//		Map<String,Object> map = new HashMap<String,Object>();
//		int index=0;
//		List<Map<String, Object>> count = testDao.getRead();
//		for (Map<String, Object> map1 : count) {
//
//			System.out.println("index >> " + index);
//			System.out.println("map1 >> " + map);
//			for (Map.Entry<String, Object> entry : map1.entrySet()) {
//
//				String key = entry.getKey();
//
//				Object value = entry.getValue();
//
//				map.put(key,value);
//
//			}
//
//			index++;
//
//		}
//
//		return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
//	}
	
	@RequestMapping("/status")
    private ModelAndView home() throws Exception {
        List<Map<String, Object>> userList = testDao.getRead(new resultVO());
        ModelAndView mv = new ModelAndView("/status");
        mv.addObject("userList", userList);
        System.out.println("userList >> " + userList);
        return mv;
    }
	@RequestMapping(value = "/getRecordList.do") //통계 게시판 호출 데이터 전송
	 public @ResponseBody Object getRecordList(HttpServletRequest request,
	   HttpServletResponse response,
	   @ModelAttribute("resultVO") resultVO resultVO){
	  
	  Map<String, Object> mp = new HashMap<String, Object>();
	  mp.put("data", testDao.getRead(resultVO));
	  
	  Object result = mp;
	  
	  return result;
	}
	@RequestMapping(value="/successXtoJCount")
	public String successXtoJUpdate() {
		testDao.updateSuccessX2J();		
		return "guide";

	}
	@RequestMapping(value="/failXtoJCount")
	public String failXtoJUpda() {
		testDao.updateFailX2J();		
		return "guide";

	}
	@RequestMapping(value="/successJtoXCount")
	public String successJtoXUpdate() {
		testDao.updateSuccessJ2X();		
		return "guide";

	}
	@RequestMapping(value="/failJtoXCount")
	public String failJtoXUpda() {
		testDao.updateFailJ2X();		
		return "guide";

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
