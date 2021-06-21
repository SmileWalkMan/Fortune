package com.bob.fund.historyprice;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.bob.DownloadFromURL;

//Step 1 x=下载基金列表
//http://fund.eastmoney.com/fund.html#os_0;isall_0;ft_;pt_1
//http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=ljjz,asc&page=4,200&dt=1621036599311&atfc=&onlySale=0
public class DownloadFund {

	public static String downloadOneFund(String fund) {

		String url = "http://fund.eastmoney.com/pingzhongdata/" + fund + ".js?v=20210514115823";
		System.out.println(url);
		try {
			InputStream is = DownloadFromURL.downLoadFromUrlAsInputstream(url);
			String result = IOUtils.toString(is);
			result = result.substring(result.indexOf("Data_ACWorthTrend"), result.indexOf("/*累计收益率走势*/"));
			result = result.substring(result.indexOf("=") + 1).trim();
			result = result.trim().substring(2, result.length() - 3);// 去除开始的[[和结尾的]];
			result = result.replaceAll("\\],\\[", "\n");
			FileOutputStream fos = new FileOutputStream("fund/historyprice/" + fund + ".txt", true);
			// true表示在文件末尾追加
			fos.write(result.getBytes());
			fos.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static ArrayList<String> getfundlist() {
		String file = "fund/funds.csv";
		ArrayList<String> list = new ArrayList<String>();
		try {
			BufferedReader bw = new BufferedReader(new FileReader(file));
			String line = null;
			// 因为不知道有几行数据，所以先存入list集合中
			while ((line = bw.readLine()) != null) {
				list.add(line.substring(1, 7));
				System.err.println(line.substring(1, 7));
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		for (String s : getfundlist()) {
			Thread.sleep(100);
			downloadOneFund(s);
		}
//		getfundlist();
	}

}
