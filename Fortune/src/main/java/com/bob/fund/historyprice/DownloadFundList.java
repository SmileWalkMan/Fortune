package com.bob.fund.historyprice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

import com.bob.DownloadFromURL;
//Step 1 x=下载基金列表
//http://fund.eastmoney.com/fund.html#os_0;isall_0;ft_;pt_1
//http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=ljjz,asc&page=4,200&dt=1621036599311&atfc=&onlySale=0
public class DownloadFundList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i=1;i<=55;i++) {
			String url="http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=ljjz,asc&page="+i+",200&dt=1621036599311&atfc=&onlySale=0";
			try {
				InputStream is=DownloadFromURL.downLoadFromUrlAsInputstream(url);
				String result = IOUtils.toString(is);
//				System.out.println(result);
				result=result.substring(result.indexOf("[[")+2, result.indexOf("]]"));
//				System.out.println(result);
				String funds[]=result.split("\\],\\[");
//				System.out.println(funds.length);
//				System.out.println(Arrays.toString(funds));
				for(String ss:funds)
				System.out.println(ss);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
