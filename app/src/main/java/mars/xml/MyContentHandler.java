package mars.xml;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyContentHandler extends DefaultHandler {
	private String hisname, address, money, sex, status;
	private String tagName;
    Student student = new Student();
	public void startDocument() throws SAXException {
		Log.v("startDocument()","````````开始解析整个XML文档````````");

	}

	public void endDocument() throws SAXException {
		Log.v("endDocument()","````````结束解析整个XML文档````````");
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
        Log.v("startElement()","开始对该元素解析>>>>>>>>>>>>>");
        tagName = localName;
		if (localName.equals("worker")) {
            Log.v("startElement()","该标签有"+attr.getLength()+"个属性！");
			for (int i = 0; i < attr.getLength(); i++) {
                if(attr.getLocalName(i).equals("id")) {
                    student.setId( attr.getValue(i));
                }
                else if (attr.getLocalName(i).equals("domID")){
                    student.setDomId(attr.getValue(i));
                }
                else if (attr.getLocalName(i).equals("studioID")){
                    student.setStudioId(attr.getValue(i));
                }

				Log.v("startElement()","第"+i+"个属性是："+attr.getLocalName(i) + "是" + attr.getValue(i));
			}
		}
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        Log.v("endElement()","结束对该元素解析<<<<<<<<<<<<<<<");
		//在workr标签解析完之后，会打印出所有得到的数据
        tagName = "";
        if (localName.equals("worker")) {
			this.printout();

		}
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
        Log.v("characters()","获取特性中----------");
		if (tagName.equals("name")) {
            hisname = new String(ch, start, length);
            student.setName( hisname);
            Log.v("characters()","成功获取特性***");
        }
		else if (tagName.equals("sex")) {
            sex = new String(ch, start, length);
            student.setSex(sex);
            Log.v("characters()","成功获取特性***");
        }
		else if (tagName.equals("status")) {
            status = new String(ch, start, length);
            student.setStatus(status);
            Log.v("characters()","成功获取特性***");
        }
		else if (tagName.equals("address")){
            address = new String(ch, start, length);
            student.setAddress(address);
            Log.v("characters()","成功获取特性***");
        }
		else if (tagName.equals("money")) {
            money = new String(ch, start, length);
            student.setMoney(money);
            Log.v("characters()","成功获取特性***");
        }
        else Log.v("characters()","没有特性！！！");

	}

	private void printout() {
		System.out.print("name: ");
		System.out.println(hisname);
		System.out.print("sex: ");
		System.out.println(sex);
		System.out.print("status: ");
		System.out.println(status);
		System.out.print("address: ");
		System.out.println(address);
		System.out.print("money: ");
		System.out.println(money);
		System.out.println();
	}

}
