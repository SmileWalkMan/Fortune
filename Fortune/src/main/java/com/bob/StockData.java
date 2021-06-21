package com.bob;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 
 * @author bob
 * 
 */
//http://basic.10jqka.com.cn/api/stock/export.php?export=debt&type=year&code=300138
//http://basic.10jqka.com.cn/api/stock/export.php?export=cash&type=year&code=300138
//http://basic.10jqka.com.cn/api/stock/export.php?export=benefit&type=year&code=300138
public class StockData {

	public static ArrayList<String> GetStockCodes(String name) {
		// 使用ArrayList来存储每行读取到的字符串
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(name);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			// 按行读取字符串
			while ((str = bf.readLine()) != null) {
				arrayList.add(str.split(" ")[1]);
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

}
