package varen.main;

import goyal.weather.GlobalWeather;
import goyal.weather.GlobalWeatherSoap;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import net.webservicex.GeoIPServiceSoap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IPLocator{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    GeoIPService geoIP=new GeoIPService();
		GeoIPServiceSoap geosoapIP=geoIP.getGeoIPServiceSoap();
		GeoIP geo=geosoapIP.getGeoIP("103.4.252.66");
		System.out.println(geo.getCountryName());
		GlobalWeather global=new GlobalWeather();
		GlobalWeatherSoap weatherSoap=global.getGlobalWeatherSoap();
		String cityXml=weatherSoap.getCitiesByCountry("India");
	//	System.out.println(cityXml);
		//System.out.println(temper);
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
		try {
			docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(new StringReader(cityXml)));
			doc.normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Table");
			System.out.println("----------------------------");
			 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					String city=eElement.getElementsByTagName("City").item(0).getTextContent();
					
					String temper=weatherSoap.getWeather(city, "India");
					int index = city.indexOf("/");
					//System.out.println(index);
					if(index!=-1)
					{
					city = city.substring(0,index);
					}
					city.trim();
					String notfound="Data Not Found";
					if(!temper.contains(notfound))
					{
					Document tempDoc=docBuilder.parse(new InputSource(new StringReader(temper)));
					Node node=tempDoc.getElementsByTagName("Temperature").item(0);
					System.out.println("City :"+city );
					System.out.println("Temperature :"+node.getTextContent());
					}
					
		 
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}

}