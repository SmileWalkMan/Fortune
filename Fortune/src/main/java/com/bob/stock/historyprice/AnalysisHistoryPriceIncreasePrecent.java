package com.bob.stock.historyprice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.csvreader.CsvReader;

public class AnalysisHistoryPriceIncreasePrecent {

	static int satifyNum = 0;
	static int totalNum = 0;

	public static int fun1_1000_Num=0;
	public static int fun1_Num=0;
	/**
	 * 持有多少天，上涨的概率，
	 * 小于-10% 概率A
	 * -10%～-5% 概率B
	 * -5%-0 概率C
	 * 0～5% 概率D
	 * 5%～10% 概率E
	 * 大于10% 概率F
	 * @param filePath
	 * @param days 持有多少天
	 * @return
	 */
	public static void fun1(String filePath, int days,double p_total,double p_max, boolean outputAlways) {
		int a=0;
		int b=0;
		int c=0;
		int d=0;
		int e=0;
		int f=0;
		ArrayList<Double> ar = new ArrayList<Double>();
		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(filePath);

			// 读表头
			csvReader.readHeaders();
			while (csvReader.readRecord()) {

				if (!"None".equals(csvReader.get(3))&&csvReader.get(3)!=null&&!"".equals(csvReader.get(3))) {
					ar.add(Double.parseDouble(csvReader.get(3)));
				}
			}
			if(ar.size()<1000)return;
			for(int i=0;i<ar.size()-days-2;i++) {
				double p=(ar.get(i)-ar.get(i+days))/ar.get(i+days);
				if(p<-0.1){
					a++;
				}else if(p>=-0.1&p<-0.05){
					b++;
				}else if(p>=-0.05&p<0){
					c++;
				}else if(p>=0&p<0.05){
					d++;
				}else if(p>=0.05&p<0.1){
					e++;
				}else {
					f++;
				}
			}
			if(outputAlways || ((double) (d + e + f)) / ((double) (ar.size() - days - 2))>=p_total
					&& ((double) f) / ((double) (ar.size() - days - 2))>=p_max) {
				fun1_Num++;
				String fcode=filePath.substring(filePath.lastIndexOf("/")+1,filePath.indexOf("."));
				System.out.println("股票代码；"+fcode+":交易总天数:"+ar.size()+"天");
				System.out.println("<下跌的概率>" + ((double) (a + b + c)) / ((double) (ar.size() - days - 2)));
				System.out.println("(跌幅大于10%的概率)"+((double) a) / ((double) (ar.size() - days - 2)));
				System.out.println("(跌幅在10%到5%之间的概率)"+((double) b) / ((double) (ar.size() - days - 2)));
				System.out.println("(跌幅在5%到0%之间的概率)"+((double) c) / ((double) (ar.size() - days - 2)));
				System.out.println("(涨幅在0%到5%之间的概率)"+((double) d) / ((double) (ar.size() - days - 2)));
				System.out.println("(涨幅在5%到10%之间的概率)"+((double) e) / ((double) (ar.size() - days - 2)));
				System.out.println("(涨幅幅大于10%的概率)"+((double) f) / ((double) (ar.size() - days - 2)));
				System.out.println("<上涨的概率>" + ((double) (d + e + f)) / ((double) (ar.size() - days - 2)));
				System.out.println();
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}

	}
	
	public static void fun2_percent() {
		String dir[] = new File("historyprice").list();
		for (int i = 0; i < dir.length; i++) {
			AnalysisHistoryPriceIncreasePrecent.fun1("historyprice/" + dir[i],360,0.95,0.85,false);
		}
	}
	public static void main(String[] args) {
		fun2_percent();
	}
}
