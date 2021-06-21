package com.bob.stock.historyprice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.csvreader.CsvReader;

public class AnalysisHistoryPrice {

	static int satifyNum = 0;
	static int totalNum = 0;

	/**
	 * 一个股票前面连续多少天涨幅大于某个值有多少个
	 * @param filePath
	 * @param count 前面连续多少个满足条件
	 * @param percent 涨幅大于多少为满足条件
	 * @return
	 */
	public static int fun1(String filePath, int count, double percent) {
		satifyNum = 0;
		totalNum = 0;
		double previous[] = new double[count];
		ArrayList<Double> ar = new ArrayList<Double>();
		ArrayList<String> dateAr = new ArrayList<String>();
		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(filePath);

			// 读表头
			csvReader.readHeaders();
			while (csvReader.readRecord()) {
//				// 读一整行
//				System.out.println(csvReader.getRawRecord());
//				// 读这行的某一列
//				System.out.println("涨跌幅："+csvReader.get(9));
				if (!"None".equals(csvReader.get(9))) {
					ar.add(Double.parseDouble(csvReader.get(9)));
					dateAr.add(csvReader.get(0));
				}
			}

			for (int i = 0; i < ar.size() - count - 1; i++) {
				int n = 0;
				for (int k = i + 1; k <= i + count; k++) {
					if (ar.get(k) > 0)
						n++;
				}
				if (n == count) {
					totalNum++;
					if (ar.get(i) > percent) {
						System.out.print("(" + dateAr.get(i) + ":" + ar.get(i) + "):");
						for (int k = i + 1; k <= i + count; k++) {
							System.out.print("(" + dateAr.get(k) + ":" + ar.get(k) + ") ");
						}
						System.out.println();
						satifyNum++;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filePath + ":" + satifyNum + "/" + totalNum);
		return satifyNum;
	}
	public static void fun1_percent() {
		String dir[] = new File("historyprice").list();
		int total = 0;
		int num = 0;
		for (int i = 0; i < 2000 && i < dir.length; i++) {
			int n = AnalysisHistoryPrice.fun1("historyprice/" + dir[i],3, 0.0);
			total += totalNum;
			num += satifyNum;
		}
		System.out.println(num + "/" + total + ":" + num / total);
	}
	
	/**
	 * 一个股票，当天涨幅大于多少值，前面多少天内有多少天有涨，
	 * e.g. 今天是第N个交易日涨幅大于P%，N-M到N-1天里有多少天是涨的
	 * @param filePath
	 * @param count 前面连续多少个满足条件
	 * @param percent 涨幅大于多少为满足条件
	 * @return
	 */
	public static int fun2(String filePath, int N, int M, double P) {
		satifyNum = 0;
		totalNum = 0;
		double previous[] = new double[N];
		ArrayList<Double> ar = new ArrayList<Double>();
		ArrayList<String> dateAr = new ArrayList<String>();
		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(filePath);

			// 读表头
			csvReader.readHeaders();
			while (csvReader.readRecord()) {
//				// 读一整行
//				System.out.println(csvReader.getRawRecord());
//				// 读这行的某一列
//				System.out.println("涨跌幅："+csvReader.get(9));
				if (!"None".equals(csvReader.get(9))) {
					ar.add(Double.parseDouble(csvReader.get(9)));
					dateAr.add(csvReader.get(0));
				}
			}
			System.out.println(filePath+":"+ar.size());
			for(int i=0;i<ar.size()-N-1;i++) {
				double current=ar.get(i);
				if(current>=P) {
					totalNum++;
					int sn=0;
					for(int k=i+N;k>i;k--) {
						double pCurrent=ar.get(k);
						if(pCurrent>0) {
							sn++;
						}
					}
					if(sn>=M) {
						satifyNum++;
					}
				}
//				else System.out.println(current);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(filePath + ":" + satifyNum + "/" + totalNum);
		return satifyNum;
	}
	public static void fun2_percent() {
		String dir[] = new File("historyprice").list();
		int total = 0;
		int num = 0;
		for (int i = 0; i < 3000 && i < dir.length; i++) {
			int n = AnalysisHistoryPrice.fun2("historyprice/" + dir[i],100, 50, 5.0);
			total += totalNum;
			num += satifyNum;
		}
		System.out.println(num + "/" + total + ":" + (double)num / (double)total);
	}
	public static void main(String[] args) {
		fun2_percent();
	}
}
