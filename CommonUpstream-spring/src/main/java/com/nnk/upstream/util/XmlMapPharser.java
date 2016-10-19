package com.nnk.upstream.util;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/27
 * Time: 17:09
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * xml字符串解析器实现类.<br>
 * xml字符串转换为Map对象.<br>
 * 转换后的数据类型为Map、List、String三种数据类型.<br>
 *
 *
 */

public class XmlMapPharser {

    public static final String NODE_ELEMENT_NAME = "root";
    public static final String NODE_DEFAULT_VALUE = "";

    private String rootName;            //根节点
    private String defaultNullValue;    //节点没有值的情况下默认值

    private static XMLInputFactory factory = XMLInputFactory.newInstance();
    static {
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
    }
    public XmlMapPharser(){
        init();
    }
    /* (non-Javadoc)
     * @see com.juxtapose.xml.parser.IXMLParser#parse(java.lang.String)
     */
    public Map<String, Object> parse(String xml) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringReader stringReader = null;
        try{
            stringReader = new StringReader(xml);
            XMLStreamReader reader = factory.createXMLStreamReader(stringReader);
            map = parse(reader);
        }catch(Throwable t){
            throw new RuntimeException(t);
        }finally{
            if(null != stringReader){
                try {
                    stringReader.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return map;
    }

    /* (non-Javadoc)
     * @see com.juxtapose.xml.parser.IXMLParser#init()
     */
    private void init() {
        if(this.getRootName() == null){
            this.setRootName(NODE_ELEMENT_NAME);
            this.setDefaultNullValue(NODE_DEFAULT_VALUE);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map<String, Object> parse(XMLStreamReader reader) throws Throwable{
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> currentMap = map;
        int event = reader.getEventType();
        Map<String,Integer> mapNumber = new HashMap<String, Integer>();
        //xml标签顺序
        List<String> names = new ArrayList<String>();
        List<String> attrs = new ArrayList<String>();
        NodeAmount nodeAmount = new NodeAmount();
        int taglength = 0;
        String tagName = null;
        String tagValue = this.defaultNullValue;
        while(true){
            switch (event) {
                case XMLStreamConstants.START_DOCUMENT:
                    break;
                case XMLStreamConstants.ATTRIBUTE:

                    break;
                case XMLStreamConstants.START_ELEMENT:
                    tagValue = this.defaultNullValue;
                    tagName = reader.getLocalName();
                    if(this.rootName.equals(tagName)){
                        break;
                    }

                    if(mapNumber.containsKey(tagName)){
                        Integer number = mapNumber.get(tagName);
                        number++;
                        mapNumber.put(tagName,number);
                        tagName = tagName+"-"+number;
                        names.add(tagName);
                    }else {
                        names.add(tagName);
                        mapNumber.put(tagName,1);
                    }
                    taglength++;
                    currentMap = map;
                    for (int i=0;i<reader.getAttributeCount();i++){
                        String attribute = "attribute-"+reader.getAttributeLocalName(i);
                        //放入熟悉类表
                        attrs.add(attribute);
                        Object object = currentMap.get(tagName);
                        if(null == object){
                            Map attrmap = new HashMap();
                            attrmap.put(attribute,reader.getAttributeValue(i));
                            currentMap.put(tagName,attrmap);
                        }else if(object instanceof Map){
                            Map object1 = (Map) object;
                            object1.put(attribute,reader.getAttributeValue(i));
                        }
                    }
                    if(taglength > 1){
                        for(int i=0;i< taglength-1;i++){
                            //root>student>attribute-version>attribute-name>name>age>
                            String name = names.get(i);
                            Object object = currentMap.get(name);
                            if(null == object){
                                object = new HashMap<String, Object>();
                                currentMap.put(names.get(i), object);
                                currentMap = (Map<String, Object>)object;
                            }else{
                                int currentTagNameSize = nodeAmount.getSize(i + 1 + "" + names.get(i));
                                if( currentTagNameSize > 1){
                                    if(object instanceof Map){
                                        List parentList = new ArrayList();
                                        parentList.add(object);
                                        Map tempMap = new HashMap();
                                        parentList.add(tempMap);
                                        currentMap.put(names.get(i), parentList);
                                        currentMap = tempMap;
                                    }else if(object instanceof List){
                                        List parentList = (List)object;
                                        int parentListSize = parentList.size();
                                        if(parentListSize != currentTagNameSize){
                                            Map tempMap = new HashMap();
                                            parentList.add(tempMap);
                                            currentMap = tempMap;
                                        }else{
                                            Map tempMap = (Map) parentList.get(parentList.size()-1);
                                            currentMap = tempMap;
                                        }
                                    }
                                }else{
                                    currentMap = (Map<String, Object>)object;
                                }
                            }
                        }
                    }
                    nodeAmount.add(names.size() + tagName);

                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagValue = reader.getText();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    tagName = reader.getLocalName();
                    if(this.rootName.equals(tagName)){
                        break;
                    }

                    currentMap = map;
                    if(taglength > 1){
                        for(int i=0;i< taglength-1;i++){
                            Object object = currentMap.get(names.get(i));
                            if(null == object){
                                //nothing to do
                            }else{
                                if(object instanceof List){
                                    List list = (List)object;
                                    currentMap = (Map)list.get(list.size() -1);
                                }else if(object instanceof Map){
                                    currentMap = (Map)object;
                                }
                            }
                        }
                    }

                    Object oldValue = currentMap.get(tagName);
                    if(!currentMap.containsKey(tagName)){
                        currentMap.put(tagName, tagValue);
                        nodeAmount.remove(names.size() + tagName);
                    }else{
                        if(oldValue instanceof List){
                            List list = (List)oldValue;
                            if(list.size() > 0){
                                Object obj = list.get(0);
                                if(obj instanceof String){
                                    ((List)oldValue).add(tagValue);
                                    nodeAmount.remove(names.size() + tagName);
                                }
                            }
                        }else if(oldValue instanceof Map){

                        }else{
                            List tmpList = new ArrayList();
                            currentMap.put(tagName, tmpList);
                            tmpList.add(oldValue);
                            tmpList.add(tagValue);
                            nodeAmount.remove(names.size() + tagName);
                        }
                    }

                    tagValue = this.defaultNullValue;
                    names.remove(names.size()-1);
                    taglength--;
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    break;
            }

            if (!reader.hasNext()) {
                break;
            }
            event = reader.next();
        }
        return map;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getDefaultNullValue() {
        return defaultNullValue;
    }

    public void setDefaultNullValue(String defaultNullValue) {
        this.defaultNullValue = defaultNullValue;
    }

    class NodeAmount{
        private Map<String, AtomicInteger> map =new HashMap<String, AtomicInteger>();

        public void add(String nodeName){
            AtomicInteger integer = map.get(nodeName);
            if(null == integer){
                integer = new AtomicInteger(0);
                map.put(nodeName, integer);
            }
            integer.incrementAndGet();//+1
        }

        public void remove(String nodeName){
            AtomicInteger integer = map.get(nodeName);
            if(null != integer){
                integer.decrementAndGet();
            }
        }

        public int getSize(String nodeName){
            AtomicInteger integer = map.get(nodeName);
            if(null == integer){
                integer = new AtomicInteger(0);
                map.put(nodeName, integer);
            }
            return integer.intValue();
        }
    }

}