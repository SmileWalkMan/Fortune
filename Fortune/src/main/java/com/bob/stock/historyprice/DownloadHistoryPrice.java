package com.bob.stock.historyprice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.bob.DownloadFromURL;

public class DownloadHistoryPrice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> strs = toArrayByFileReader1("stock/stocks.txt");
		int i = 1;
		Date date = new Date();
        SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYYMMdd");
        String currentDate = formatYYYY.format(date);
		for (String str : strs) {
			try {
				System.out.println(i + "/" + strs.size());
				// 深市股票前面加1
				if (str.startsWith("0") || str.startsWith("3"))
					DownloadFromURL.downLoadFromUrl("http://quotes.money.163.com/service/chddata.html?code=1" + str
							+ "&start=20001105&end="+currentDate+"&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP",
							str + "_historyprice.xls", "./stock/historyprice/");
				else // 沪市股票前面加0
					DownloadFromURL.downLoadFromUrl("http://quotes.money.163.com/service/chddata.html?code=0" + str
							+ "&start=20001105&end="+currentDate+"&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP",
							str + "_historyprice.xls", "./stock/historyprice/");
//	                 Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static ArrayList<String> toArrayByFileReader1(String name) {
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
