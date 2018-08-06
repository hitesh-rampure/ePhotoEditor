package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by jchheda on 03/16/2017.
 */

public class FileBO
    {

        final SoapClient soapClient = new SoapClient();
        public String ErrorText = "";
        private String serviceUrl = "http://192.168.25.36:10002/eServiceTechService_2.5/";

        public ArrayList<Map<Object, Object>> GetAllFiles(String company, String SONo, String SOSNo, String unitNo, String fileType)
            {
                ArrayList<Map<Object, Object>> result = null;
                try
                    {
                        soapClient.setServiceUrl("http://192.168.25.36:10001/eServiceTechService_2.5/ServiceManager/ServiceOrderService.svc");
                        soapClient.setSoapAction("IServiceOrder/GetAllFiles");
                        String soapBody = String.format("<GetAllFiles xmlns=\"http://tempuri.org/\"><Company>%s</Company>" +
                                        "<ServiceOrderNumber>%s</ServiceOrderNumber><ServiceOrderSegment>%s</ServiceOrderSegment>" +
                                        "<UnitNo>%s</UnitNo><IsDataRequired>0</IsDataRequired><FileType>%s</FileType></GetAllFiles>",
                                company, SONo, SOSNo, unitNo, fileType);
                        soapClient.setSoapBody(soapBody);
                        InputStream response = soapClient.queryTheServer();
                        if (soapClient.ErrorText != null && !soapClient.ErrorText.equals(""))
                            ErrorText = soapClient.ErrorText;
                        else if (response == null)
                            ErrorText = AppConstants.COMMON_ERROR_MESSAGE;
                        else if (response != null)
                            {
                                try
                                    {
                                        result = ArrayInputStreamToArrayDictionary(response, "GetAllFilesResult", "objAnnotationDetails");
                                    } catch (Exception e)
                                    {
                                        ErrorText = e.getMessage();
                                    }
                            }
                    } catch (Exception e)
                    {
                        ErrorText = e.getMessage();
                    }
                return result;
            }

        public static ArrayList<Map<Object, Object>> ArrayInputStreamToArrayDictionary(InputStream is, String rootTag, String arrayElements)
            {
                ArrayList<Map<Object, Object>> result = null;
                try
                    {
                        DocumentBuilderFactory dbFactory_ = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = dbFactory_.newDocumentBuilder();
                        Document doc = builder.parse(is, null);
                        NodeList nodes = doc.getElementsByTagName(rootTag);
                        if (nodes != null && nodes.getLength() > 0)
                            {
                                result = ArrayNodeToArrayDictionary(nodes.item(0).getChildNodes(), arrayElements);
                            }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return result;
            }

        public static ArrayList<Map<Object, Object>> ArrayNodeToArrayDictionary(NodeList nodes, String arrayElements)
            {
                ArrayList<Map<Object, Object>> result = null;
                try
                    {
                        if (nodes != null && nodes.getLength() > 0)
                            {
                                result = new ArrayList<Map<Object, Object>>();

                                for (int i = 0; i < nodes.getLength(); i++)
                                    {
                                        Node node = nodes.item(i);
                                        Map<Object, Object> singleObject = NodeToDictionary(node.getChildNodes(), arrayElements);
                                        result.add(singleObject);
                                    }
                            }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return result;
            }

        public static Map<Object, Object> NodeToDictionary(NodeList nodes, String arrayElements)
            {
                Map<Object, Object> result = null;

                ArrayList<String> arrayItems = new ArrayList(Arrays.asList(arrayElements.split("\\s*,\\s*")));
                try
                    {
                        if (nodes != null && nodes.getLength() > 0)
                            {
                                result = new HashMap<Object, Object>();
                                for (int i = 0; i < nodes.getLength(); i++)
                                    {
                                        Node node = nodes.item(i);
                                        String key = "";
                                        if (node.getNodeType() == Node.ELEMENT_NODE)
                                            key = ((Element) node).getTagName().contains(":") ? ((Element) node).getTagName().split(":")[1] : ((Element) node).getTagName();
                                        else if (node.getNodeType() == Node.TEXT_NODE)
                                            key = node.getTextContent().contains(":") ? node.getTextContent().split(":")[1] : node.getTextContent();
                                        else
                                            continue;
                                        if (arrayItems.size() > 0 && arrayItems.contains(key))
                                            {
                                                result.put(key, ArrayNodeToArrayDictionary(node.getChildNodes(), arrayElements));
                                            }
                                        else if (node instanceof Element && node.getChildNodes().getLength() > 1)
                                            {
                                                if (!result.containsKey(key))
                                                    result.put(key, NodeToDictionary(node.getChildNodes(), arrayElements));
                                                else
                                                    {
                                                        Object value = result.get(key);
                                                        List<Object> list = null;
                                                        if (value instanceof List)
                                                            {
                                                                list = (List<Object>) value;
                                                                list.add(NodeToDictionary(node.getChildNodes(), arrayElements));
                                                            }
                                                        else
                                                            {
                                                                list = new ArrayList<>();
                                                                list.add(value);
                                                                list.add(NodeToDictionary(node.getChildNodes(), arrayElements));
                                                            }
                                                        result.put(key, list);
                                                    }
                                            }
                                        else if (node instanceof Element && node.getChildNodes().getLength() == 1)
                                            {
                                                if (node.getChildNodes().item(0) != null && node.getChildNodes().item(0).getNodeValue() != null)
                                                    result.put(key, node.getChildNodes().item(0).getNodeValue().trim());
                                                else if (node.getChildNodes().item(0) != null && node.getChildNodes().item(0).getChildNodes().getLength() == 1)
                                                    {
                                                        if (node.getChildNodes().item(0).getChildNodes().item(0) != null && node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue() != null)
                                                            result.put(key, node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue().trim());
                                                    }
                                                else if (node.getChildNodes().item(0) != null
                                                        && node.getChildNodes().item(0).hasChildNodes() && node.getChildNodes().item(0).getChildNodes().getLength() > 1)
                                                    result.put(key, NodeToDictionary(node.getChildNodes(), arrayElements));
                                            }
                                        else if (node instanceof Element)
                                            {
                                                result.put(key, ((Element) node).getTextContent().trim());
                                            }
                                        else
                                            {
                                                result.put(key, key);
                                            }
                                    }
                            }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return result;
            }

        public Map<Object, Object> SaveChunkFiles(String fileName, Map<Object, Object> chunk, OnWebServiceResponse onWebServiceResponse)
            {
                Map<Object, Object> result = null;
                try
                    {


                        soapClient.setServiceUrl(serviceUrl + AppConstants.ServiceOrder_Service);//"http://192.168.90.150:7001/ServiceManager/ServiceOrderService.svc"); ///eServiceTechService_2.5/ServiceManager/ServiceOrderService.svc");

                        //soapClient.setServiceUrl("http://192.168.90.150:7001/ServiceManager/ServiceOrderService.svc");
                        soapClient.setSoapAction("IServiceOrder/SaveChunkFiles");
                        String soapBody = String.format("<SaveChunkFiles xmlns=\"http://tempuri.org/\">" +
                                        "<Files xmlns:d4p1=\"http://schemas.datacontract.org/2004/07/eServiceTech.ServiceTechManager.DataContracts\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                                        "<d4p1:FilesUserData>" +
                                        "<d4p1:ChunkName><![CDATA[%s]]></d4p1:ChunkName>" +
                                        "<d4p1:Company><![CDATA[%s]]></d4p1:Company>" +
                                        "<d4p1:DateUploaded><![CDATA[%s]]></d4p1:DateUploaded>" +
                                        "<d4p1:EmployeeName><![CDATA[%s]]></d4p1:EmployeeName>" +
                                        "<d4p1:EmployeeNo><![CDATA[%s]]></d4p1:EmployeeNo>" +
                                        "<d4p1:FileData></d4p1:FileData>" +
                                        "<d4p1:FileId><![CDATA[%s]]></d4p1:FileId>" +
                                        "<d4p1:FileName><![CDATA[%s]]></d4p1:FileName>" +
                                        "<d4p1:FileType><![CDATA[%s]]></d4p1:FileType>" +
                                        "<d4p1:FileUrl i:nil=\"true\"></d4p1:FileUrl>" +
                                        "<d4p1:OriginalData></d4p1:OriginalData>" +
                                        "<d4p1:OriginalUrl i:nil=\"true\"></d4p1:OriginalUrl>" +
                                        "<d4p1:ServiceOrderNumber><![CDATA[%s]]></d4p1:ServiceOrderNumber>" +
                                        "<d4p1:ServiceOrderSegment><![CDATA[%s]]></d4p1:ServiceOrderSegment>" +
                                        "<d4p1:ThumbnailData></d4p1:ThumbnailData>" +
                                        "<d4p1:ThumbnailUrl i:nil=\"true\"></d4p1:ThumbnailUrl>" +
                                        "<d4p1:Title><![CDATA[%s]]></d4p1:Title>" +
                                        "<d4p1:UnitNo><![CDATA[%s]]></d4p1:UnitNo>" +
                                        "<d4p1:chunkData><![CDATA[%s]]></d4p1:chunkData>" +
                                        "%s" +
                                        "</d4p1:FilesUserData>" +
                                        "</Files>" +
                                        "</SaveChunkFiles>",
                                chunk.get("ChunkName").toString(),
                                "550",
                                "2018-07-31T09:54:39",
                                "LEGEN FRON",// SessionHelper.LoggedInEmployee.EmployeeData.get("EmployeeName").toString(),
                                "008", //  SessionHelper.getCredentials().getEmployeeNo(),
                                "0",
                                fileName,
                                "others",
                                "XYZ100337",
                                "10",
                                "akshay",
                                "FM0000775",
                                chunk.get("ChunkData").toString(),
                                ""
                        );
                        soapClient.setSoapBody(soapBody);
                        InputStream response = soapClient.queryTheServer();
                        if (soapClient.ErrorText != null && !soapClient.ErrorText.equals(""))
                            ErrorText = soapClient.ErrorText;
                        else if (response == null)
                            ErrorText = AppConstants.COMMON_ERROR_MESSAGE;
                        else if (response != null)
                            {
                                try
                                    {
                                        result = InputStreamToDictionary(response, "SaveChunkFilesResult");
                                        if (result != null && result.size() > 0)
                                            {

                                                onWebServiceResponse.onSuccess();
                                                
//                        CDHelper.UpdateFileChunkUploaded(fileName, chunk.get("ChunkID").toString(),
//                                Boolean.valueOf(result.get("Status").toString()));
                                                if (result.get("Status") != null && Boolean.valueOf(result.get("Status").toString()))
                                                    {
                                                        //                if (result.get("FileId") != null && Integer.valueOf(result.get("FileId").toString()) > 0)

                                                        //CDHelper.UpdateFileId(fileName, result.get("FileId").toString());
//                            if (CDHelper.isAllChunksUploaded(fileName))
//                                CDHelper.deleteFileChunks(fileName);

                                                        result.get("Status");
                                                    }
                                            }
                                    } catch (Exception e)
                                    {
                                        ErrorText = e.getMessage();
                                        e.printStackTrace();
                                        onWebServiceResponse.onFailure();
                                    }
                            }
                    } catch (Exception ex)
                    {
                        ErrorText = ex.getMessage();
                        ex.printStackTrace();
                        onWebServiceResponse.onFailure();
                    }
                finally
                    {
                        if (ErrorText != null && !ErrorText.equals(""))
                            // CDHelper.UpdateUploadStatus(fileName);
                            Log.e("", "");
                    }
                return result;
            }

        public static Map<Object, Object> InputStreamToDictionary(InputStream is, String rootTag)
            {
                Map<Object, Object> result = null;
                try
                    {
                        DocumentBuilderFactory dbFactory_ = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = dbFactory_.newDocumentBuilder();
                        Document doc = builder.parse(is, null);
                        NodeList nodes = doc.getElementsByTagName(rootTag);
                        if (nodes != null && nodes.getLength() > 0)
                            {
                                result = NodeToDictionary(nodes.item(0).getChildNodes());
                            }
                    } catch (SAXParseException se)
                    {
                        se.printStackTrace();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return result;
            }

        public static Map<Object, Object> NodeToDictionary(NodeList nodes)
            {
                Map<Object, Object> result = null;
                try
                    {
                        if (nodes != null && nodes.getLength() > 0)
                            {
                                result = new HashMap<Object, Object>();
                                for (int i = 0; i < nodes.getLength(); i++)
                                    {
                                        Node node = nodes.item(i);
                                        String key = "";
                                        if (node.getNodeType() == Node.ELEMENT_NODE)
                                            key = ((Element) node).getTagName().contains(":") ? ((Element) node).getTagName().split(":")[1] : ((Element) node).getTagName();
                                        else if (node.getNodeType() == Node.TEXT_NODE)
                                            key = node.getTextContent().contains(":") ? node.getTextContent().split(":")[1] : node.getTextContent();
                                        else
                                            continue;
                                        if (node instanceof Element && node.getChildNodes().getLength() > 1)
                                            {
                                                if (!result.containsKey(key))
                                                    result.put(key, NodeToDictionary(node.getChildNodes()));
                                                else
                                                    {
                                                        Object value = result.get(key);
                                                        List<Object> list = null;
                                                        if (value instanceof List)
                                                            {
                                                                list = (List<Object>) value;
                                                                list.add(NodeToDictionary(node.getChildNodes()));
                                                            }
                                                        else
                                                            {
                                                                list = new ArrayList<>();
                                                                list.add(value);
                                                                list.add(NodeToDictionary(node.getChildNodes()));
                                                            }
                                                        result.put(key, list);
                                                    }
                                            }
                                        else if (node instanceof Element && node.getChildNodes().getLength() == 1)
                                            {
                                                if (node.getChildNodes().item(0) != null && node.getChildNodes().item(0).getNodeValue() != null)
                                                    result.put(key, node.getChildNodes().item(0).getNodeValue().trim());
                                                else if (node.getChildNodes().item(0) != null && node.getChildNodes().item(0).getChildNodes().getLength() == 1)
                                                    {
                                                        if (node.getChildNodes().item(0).getChildNodes().item(0) != null && node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue() != null)
                                                            result.put(key, node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue().trim());
                                                    }
                                                else if (node.getChildNodes().item(0) != null
                                                        && node.getChildNodes().item(0).hasChildNodes() && node.getChildNodes().item(0).getChildNodes().getLength() > 1)
                                                    result.put(key, NodeToDictionary(node.getChildNodes()));
                                            }
                                        else
                                            {
                                                result.put(key, ((Element) node).getTextContent().trim());
                                            }
                                    }
                            }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return result;
            }


        public byte[] GetFileFromUrl(String Url)
            {
                byte[] result = null;
                try
                    {
                        soapClient.setServiceUrl(Url);
                        result = soapClient.queryTheServerForFile();
                        if (soapClient.ErrorText != null && !soapClient.ErrorText.equals(""))
                            ErrorText = soapClient.ErrorText;
                        else if (result == null)
                            ErrorText = AppConstants.COMMON_ERROR_MESSAGE;

                    } catch (Exception e)
                    {
                        ErrorText = e.getMessage();
                    }
                return result;
            }
    }
