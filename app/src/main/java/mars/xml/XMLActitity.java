package mars.xml;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import mars.utils.HttpDownloader;

public class XMLActitity extends Activity {

    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button parseButton = (Button) findViewById(R.id.parseButton);
        parseButton.setOnClickListener(new ParseButtonListener());
		textView = (TextView) findViewById(R.id.tv_show);
    }

    class ParseButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {

            //handler.post(runnable);   //  这样不行，主线程不允许，非要用Thread.start()。
            new Thread(runnable).start();

            /*HttpDownloader hd = new HttpDownloader();
            String resultStr = hd.download("http://192.168.1.104:5709/server/w.xml");
            textView.setText(resultStr);
            Log.v("下载到的",resultStr+"");
            try{
                //创建一个SAXParserFactory
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader reader = factory.newSAXParser().getXMLReader();
                //为XMLReader设置内容处理器
                reader.setContentHandler(new MyContentHandler());
                //开始解析文件
                reader.parse(new InputSource(new StringReader(resultStr)));
            }
            catch(Exception e){
                e.printStackTrace();
            }*/
		}
    }

    private class XMLMessage {
        String down ;
        public String toString(){
            return down;
        }
    }

     private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(""+msg.obj);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            HttpDownloader hd = new HttpDownloader();
            String resultStr = hd.download("http://192.168.1.104:5709/server/w.xml");
            Message message = handler.obtainMessage();
            XMLMessage xmlMessage = new XMLMessage();
            xmlMessage.down = resultStr;
            message.obj = xmlMessage;
            handler.sendMessage(message);
            Log.v("下载到的",resultStr+"");
            try{
                //创建一个SAXParserFactory
                SAXParserFactory factory = SAXParserFactory.newInstance();
                Log.v("SAXParserFactory","首先创建一个SAXParserFactory");
                XMLReader reader = factory.newSAXParser().getXMLReader();
                Log.v("getXMLReader()","为SAXParserFactory设置阅读器");
                //为XMLReader设置内容处理器
                reader.setContentHandler(new MyContentHandler());
                Log.v("setContentHandler","为XMLReader设置内容处理器");
                //开始解析文件
                reader.parse(new InputSource(new StringReader(resultStr)));// 不要这一步 好像也可以解析，上一步已经开始解析了。
                Log.v("reader.parse","开始用该阅读器解析xml文件");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    };

}