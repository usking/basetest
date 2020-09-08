package com.sz.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

public class Test03 {

	public static void main(String[] args) {
		try {
			
			String approvalJson="{\r\n" + 
					"    \"0\": {\r\n" + 
					"        \"type\": \"faqiren\",\r\n" + 
					"        \"title\": \"发起人\",\r\n" + 
					"        \"range\": {\r\n" + 
					"            \"t\": \"\",\r\n" + 
					"            \"d\": \"\",\r\n" + 
					"            \"g\": \"\",\r\n" + 
					"            \"r\": \"\",\r\n" + 
					"            \"c\": \"\",\r\n" + 
					"            \"p\": \"\",\r\n" + 
					"            \"s\": \"\",\r\n" + 
					"            \"u\": \"\"\r\n" + 
					"        },\r\n" + 
					"        \"auth\": {\r\n" + 
					"            \"1\": \"1\",\r\n" + 
					"            \"2\": \"1\",\r\n" + 
					"            \"3\": \"1\",\r\n" + 
					"            \"4\": \"1\",\r\n" + 
					"            \"5\": \"1\"\r\n" + 
					"        },\r\n" + 
					"        \"link_text\": \"\"\r\n" + 
					"    },\r\n" + 
					"    \"1\": {\r\n" + 
					"        \"type\": \"approval\",\r\n" + 
					"        \"andor\": \"1\",\r\n" + 
					"        \"range\": {\r\n" + 
					"            \"t\": \"4018784\",\r\n" + 
					"            \"d\": \"\",\r\n" + 
					"            \"g\": \"\",\r\n" + 
					"            \"r\": \"\",\r\n" + 
					"            \"c\": \"\",\r\n" + 
					"            \"p\": \"\",\r\n" + 
					"            \"s\": \"\",\r\n" + 
					"            \"u\": \"\"\r\n" + 
					"        },\r\n" + 
					"        \"auth\": {\r\n" + 
					"            \"1\": \"2\",\r\n" + 
					"            \"2\": \"2\",\r\n" + 
					"            \"3\": \"2\",\r\n" + 
					"            \"4\": \"2\",\r\n" + 
					"            \"5\": \"2\"\r\n" + 
					"        },\r\n" + 
					"        \"title\": \"审批人\",\r\n" + 
					"        \"link_text\": \"迟03\"\r\n" + 
					"    },\r\n" + 
					"    \"2\": {\r\n" + 
					"        \"1\": {\r\n" + 
					"            \"case\": {\r\n" + 
					"                \"1\": [\r\n" + 
					"                    {\r\n" + 
					"                        \"selected\": [\r\n" + 
					"                            \"语文\"\r\n" + 
					"                        ],\r\n" + 
					"                        \"value\": \"[\\\"语文\\\"]\",\r\n" + 
					"                        \"type\": \"in\",\r\n" + 
					"                        \"link_text\": \"语文\"\r\n" + 
					"                    }\r\n" + 
					"                ],\r\n" + 
					"                \"title\": \"条件1\",\r\n" + 
					"                \"link_text\": \"不设置条件\"\r\n" + 
					"            },\r\n" + 
					"            \"next\": {},\r\n" + 
					"            \"link_text\": \"单选下拉框: 语文\"\r\n" + 
					"        },\r\n" + 
					"        \"2\": {\r\n" + 
					"            \"case\": {\r\n" + 
					"                \"1\": [\r\n" + 
					"                    {\r\n" + 
					"                        \"selected\": [\r\n" + 
					"                            \"体育\"\r\n" + 
					"                        ],\r\n" + 
					"                        \"value\": \"[\\\"体育\\\"]\",\r\n" + 
					"                        \"type\": \"in\",\r\n" + 
					"                        \"link_text\": \"体育\"\r\n" + 
					"                    }\r\n" + 
					"                ],\r\n" + 
					"                \"title\": \"条件1\",\r\n" + 
					"                \"link_text\": \"不设置条件\"\r\n" + 
					"            },\r\n" + 
					"            \"next\": {\r\n" + 
					"                \"1\": {\r\n" + 
					"                    \"type\": \"approval\",\r\n" + 
					"                    \"andor\": \"1\",\r\n" + 
					"                    \"range\": {\r\n" + 
					"                        \"t\": \"2010543\",\r\n" + 
					"                        \"d\": \"\",\r\n" + 
					"                        \"g\": \"\",\r\n" + 
					"                        \"r\": \"\",\r\n" + 
					"                        \"c\": \"\",\r\n" + 
					"                        \"p\": \"\",\r\n" + 
					"                        \"s\": \"\",\r\n" + 
					"                        \"u\": \"\"\r\n" + 
					"                    },\r\n" + 
					"                    \"auth\": {\r\n" + 
					"                        \"1\": \"2\",\r\n" + 
					"                        \"2\": \"2\",\r\n" + 
					"                        \"3\": \"2\",\r\n" + 
					"                        \"4\": \"2\",\r\n" + 
					"                        \"5\": \"2\"\r\n" + 
					"                    },\r\n" + 
					"                    \"title\": \"审批人\",\r\n" + 
					"                    \"link_text\": \"张166\"\r\n" + 
					"                },\r\n" + 
					"                \"2\": {\r\n" + 
					"                    \"1\": {\r\n" + 
					"                        \"case\": {\r\n" + 
					"                            \"3\": [\r\n" + 
					"                                {\r\n" + 
					"                                    \"value\": \"200\",\r\n" + 
					"                                    \"type\": \">=\",\r\n" + 
					"                                    \"link_text\": \"介于200~300之间\"\r\n" + 
					"                                },\r\n" + 
					"                                {\r\n" + 
					"                                    \"value\": \"300\",\r\n" + 
					"                                    \"type\": \"<=\"\r\n" + 
					"                                }\r\n" + 
					"                            ],\r\n" + 
					"                            \"title\": \"条件1\",\r\n" + 
					"                            \"link_text\": \"不设置条件\"\r\n" + 
					"                        },\r\n" + 
					"                        \"next\": {\r\n" + 
					"                            \"1\": {\r\n" + 
					"                                \"type\": \"approval\",\r\n" + 
					"                                \"andor\": \"1\",\r\n" + 
					"                                \"range\": {\r\n" + 
					"                                    \"t\": \"2010129\",\r\n" + 
					"                                    \"d\": \"\",\r\n" + 
					"                                    \"g\": \"\",\r\n" + 
					"                                    \"r\": \"\",\r\n" + 
					"                                    \"c\": \"\",\r\n" + 
					"                                    \"p\": \"\",\r\n" + 
					"                                    \"s\": \"\",\r\n" + 
					"                                    \"u\": \"\"\r\n" + 
					"                                },\r\n" + 
					"                                \"auth\": {\r\n" + 
					"                                    \"1\": \"2\",\r\n" + 
					"                                    \"2\": \"2\",\r\n" + 
					"                                    \"3\": \"2\",\r\n" + 
					"                                    \"4\": \"2\",\r\n" + 
					"                                    \"5\": \"2\"\r\n" + 
					"                                },\r\n" + 
					"                                \"title\": \"审批人\",\r\n" + 
					"                                \"link_text\": \"张联通1\"\r\n" + 
					"                            }\r\n" + 
					"                        },\r\n" + 
					"                        \"link_text\": \"金额输入框（元）: 介于200~300之间\"\r\n" + 
					"                    },\r\n" + 
					"                    \"2\": {\r\n" + 
					"                        \"case\": {\r\n" + 
					"                            \"3\": [\r\n" + 
					"                                {\r\n" + 
					"                                    \"value\": \"500\",\r\n" + 
					"                                    \"type\": \">\",\r\n" + 
					"                                    \"link_text\": \"大于500\"\r\n" + 
					"                                }\r\n" + 
					"                            ],\r\n" + 
					"                            \"title\": \"条件2\",\r\n" + 
					"                            \"link_text\": \"不设置条件\"\r\n" + 
					"                        },\r\n" + 
					"                        \"next\": {\r\n" + 
					"                            \"1\": {\r\n" + 
					"                                \"type\": \"approval\",\r\n" + 
					"                                \"andor\": \"1\",\r\n" + 
					"                                \"range\": {\r\n" + 
					"                                    \"t\": \"2010476\",\r\n" + 
					"                                    \"d\": \"\",\r\n" + 
					"                                    \"g\": \"\",\r\n" + 
					"                                    \"r\": \"\",\r\n" + 
					"                                    \"c\": \"\",\r\n" + 
					"                                    \"p\": \"\",\r\n" + 
					"                                    \"s\": \"\",\r\n" + 
					"                                    \"u\": \"\"\r\n" + 
					"                                },\r\n" + 
					"                                \"auth\": {\r\n" + 
					"                                    \"1\": \"2\",\r\n" + 
					"                                    \"2\": \"2\",\r\n" + 
					"                                    \"3\": \"2\",\r\n" + 
					"                                    \"4\": \"2\",\r\n" + 
					"                                    \"5\": \"2\"\r\n" + 
					"                                },\r\n" + 
					"                                \"title\": \"审批人\",\r\n" + 
					"                                \"link_text\": \"房移动\"\r\n" + 
					"                            }\r\n" + 
					"                        },\r\n" + 
					"                        \"link_text\": \"金额输入框（元）: 大于500\"\r\n" + 
					"                    },\r\n" + 
					"                    \"type\": \"switch\",\r\n" + 
					"                    \"title\": \"\"\r\n" + 
					"                },\r\n" + 
					"                \"3\": {\r\n" + 
					"                    \"1\": {\r\n" + 
					"                        \"case\": {\r\n" + 
					"                            \"5\": [\r\n" + 
					"                                {\r\n" + 
					"                                    \"value\": \"100\",\r\n" + 
					"                                    \"type\": \"<\",\r\n" + 
					"                                    \"link_text\": \"小于100\"\r\n" + 
					"                                }\r\n" + 
					"                            ],\r\n" + 
					"                            \"title\": \"条件1\",\r\n" + 
					"                            \"link_text\": \"不设置条件\"\r\n" + 
					"                        },\r\n" + 
					"                        \"next\": {\r\n" + 
					"                            \"1\": {\r\n" + 
					"                                \"type\": \"approval\",\r\n" + 
					"                                \"andor\": \"1\",\r\n" + 
					"                                \"range\": {\r\n" + 
					"                                    \"t\": \"2010437\",\r\n" + 
					"                                    \"d\": \"\",\r\n" + 
					"                                    \"g\": \"\",\r\n" + 
					"                                    \"r\": \"\",\r\n" + 
					"                                    \"c\": \"\",\r\n" + 
					"                                    \"p\": \"\",\r\n" + 
					"                                    \"s\": \"\",\r\n" + 
					"                                    \"u\": \"\"\r\n" + 
					"                                },\r\n" + 
					"                                \"auth\": {\r\n" + 
					"                                    \"1\": \"2\",\r\n" + 
					"                                    \"2\": \"2\",\r\n" + 
					"                                    \"3\": \"2\",\r\n" + 
					"                                    \"4\": \"2\",\r\n" + 
					"                                    \"5\": \"2\"\r\n" + 
					"                                },\r\n" + 
					"                                \"title\": \"审批人\",\r\n" + 
					"                                \"link_text\": \"房联通\"\r\n" + 
					"                            }\r\n" + 
					"                        },\r\n" + 
					"                        \"link_text\": \"数字输入框: 小于100\"\r\n" + 
					"                    },\r\n" + 
					"                    \"2\": {\r\n" + 
					"                        \"case\": {\r\n" + 
					"                            \"5\": [\r\n" + 
					"                                {\r\n" + 
					"                                    \"value\": \"100\",\r\n" + 
					"                                    \"type\": \">\",\r\n" + 
					"                                    \"link_text\": \"大于100\"\r\n" + 
					"                                }\r\n" + 
					"                            ],\r\n" + 
					"                            \"title\": \"条件2\",\r\n" + 
					"                            \"link_text\": \"不设置条件\"\r\n" + 
					"                        },\r\n" + 
					"                        \"next\": {\r\n" + 
					"                            \"1\": {\r\n" + 
					"                                \"type\": \"approval\",\r\n" + 
					"                                \"andor\": \"1\",\r\n" + 
					"                                \"range\": {\r\n" + 
					"                                    \"t\": \"2010387\",\r\n" + 
					"                                    \"d\": \"\",\r\n" + 
					"                                    \"g\": \"\",\r\n" + 
					"                                    \"r\": \"\",\r\n" + 
					"                                    \"c\": \"\",\r\n" + 
					"                                    \"p\": \"\",\r\n" + 
					"                                    \"s\": \"\",\r\n" + 
					"                                    \"u\": \"\"\r\n" + 
					"                                },\r\n" + 
					"                                \"auth\": {\r\n" + 
					"                                    \"1\": \"2\",\r\n" + 
					"                                    \"2\": \"2\",\r\n" + 
					"                                    \"3\": \"2\",\r\n" + 
					"                                    \"4\": \"2\",\r\n" + 
					"                                    \"5\": \"2\"\r\n" + 
					"                                },\r\n" + 
					"                                \"title\": \"审批人\",\r\n" + 
					"                                \"link_text\": \"房电信\"\r\n" + 
					"                            }\r\n" + 
					"                        },\r\n" + 
					"                        \"link_text\": \"数字输入框: 大于100\"\r\n" + 
					"                    },\r\n" + 
					"                    \"type\": \"switch\",\r\n" + 
					"                    \"title\": \"\"\r\n" + 
					"                }\r\n" + 
					"            },\r\n" + 
					"            \"link_text\": \"单选下拉框: 体育\"\r\n" + 
					"        },\r\n" + 
					"        \"type\": \"switch\",\r\n" + 
					"        \"title\": \"\"\r\n" + 
					"    },\r\n" + 
					"    \"3\": {\r\n" + 
					"        \"type\": \"approval\",\r\n" + 
					"        \"andor\": \"1\",\r\n" + 
					"        \"range\": {\r\n" + 
					"            \"t\": \"4018731\",\r\n" + 
					"            \"d\": \"\",\r\n" + 
					"            \"g\": \"\",\r\n" + 
					"            \"r\": \"\",\r\n" + 
					"            \"c\": \"\",\r\n" + 
					"            \"p\": \"\",\r\n" + 
					"            \"s\": \"\",\r\n" + 
					"            \"u\": \"\"\r\n" + 
					"        },\r\n" + 
					"        \"auth\": {\r\n" + 
					"            \"1\": \"2\",\r\n" + 
					"            \"2\": \"2\",\r\n" + 
					"            \"3\": \"2\",\r\n" + 
					"            \"4\": \"2\",\r\n" + 
					"            \"5\": \"2\"\r\n" + 
					"        },\r\n" + 
					"        \"title\": \"审批人\",\r\n" + 
					"        \"link_text\": \"杨昆\"\r\n" + 
					"    }\r\n" + 
					"}";
			
			String str="2,2,next,3,1,next,2";
			
			getCurrentNode(approvalJson,str);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}
	
	
	public static void getCurrentNode(String rootJson,String currentNode) {
		String[] nodes=currentNode.split(",");
		JSONObject rootJsonObj=JSONObject.fromObject(rootJson);
		//JSONObject eleJsonObj=getEleJsonObj(rootJsonObj, nodes);
		while(true) {
			JSONObject eleJsonObj=getEleJsonObj(rootJsonObj, nodes);
			if(eleJsonObj.isNullObject()) {
				if(nodes.length==1) {
					currentNode="end";
					break;
				}
				
				if(nodes.length<=2) {
					currentNode=String.valueOf(Integer.parseInt(nodes[0])+1);
				}else {
					List<String> nodeList=new ArrayList<>();
					for(String nod : nodes) {
						nodeList.add(nod);
					}
					nodeList.remove(nodeList.size()-1);//去掉最后一个
					String node=nodeList.get(nodeList.size()-1);
					if("next".equals(node)) {
						nodeList.remove(nodeList.size()-1);//去掉next
						nodeList.remove(nodeList.size()-1);//去掉next的前一个
						//这时的最后一个+1
						node=nodeList.get(nodeList.size()-1);
						node=String.valueOf(Integer.parseInt(node)+1);
						nodeList.set(nodeList.size()-1, node);
						nodes=nodeList.toArray(new String[nodeList.size()]);
					}else {
						node=String.valueOf(Integer.parseInt(node)+1);
						nodeList.set(nodeList.size()-1, node);
						nodes=nodeList.toArray(new String[nodeList.size()]);
					}
					currentNode=StringUtils.join(nodes,",");
				}
				nodes= currentNode.split(",");
				//eleJsonObj=getEleJsonObj(rootJsonObj, nodes);
			}else {
				break;
			}
		}
		System.out.println(currentNode);
	}
	
	public static JSONObject getEleJsonObj(JSONObject rootJsonObj,String[] nodes) {
		int nodeIndex=0;
		JSONObject eleJsonObj=rootJsonObj;
		do {
			eleJsonObj=eleJsonObj.getJSONObject(nodes[nodeIndex]);
			nodeIndex++;
			if(nodes.length==nodeIndex) {
				break;
			}
		}while(true);
		return eleJsonObj;
	}
}
