package com.bob.stock.financialreport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.bob.StockData;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class AnalysisFinancialReport{
	public static String BENEFIT="benefit";
	public static String DEBT="debt";
	public static String CASH="cash";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> Nbenifit=toArrayByFileReader1("NBenifit.txt");
		ArrayList<String> codes = StockData.GetStockCodes("s_code.txt");
		Collections.sort(codes);
		int n=0;
		for(int i=0;i<codes.size();i++) {
			HashMap<String,double[]> bMap = new HashMap<String,double[]>();
			ReadCaiBaoXSL(bMap,codes.get(i),BENEFIT);
			ReadCaiBaoXSL(bMap,codes.get(i),CASH);
			ReadCaiBaoXSL(bMap,codes.get(i),DEBT);
			System.out.println(i+"/"+codes.size()+":"+codes.get(i));
			double[] vs=bMap.get("经营活动产生的现金流量净额(元)");
			
			
			boolean sat=true;
			if(vs==null) {
				continue;
			}
			for(double d :vs) {
				if(d<0&&d!=Double.MIN_VALUE) {
					sat=false;
					break;
				}
			}
			if(sat) {
				n++;
				
				for(double d :vs) {
					System.out.print(", "+d);
				}
				System.out.println();
				System.out.println(n+"/"+i+":Yes");
			}else
				System.out.println("No");
//			for(String key:bMap.keySet())
//	        {
//	         System.out.print(key+": ");
//	         double[] vs = bMap.get(key);
//	         for(double d :vs)
//	        	 System.out.print(", "+d);
//	         System.out.println();
//	        }
		}

	}

	public static void ReadCaiBaoXSL(HashMap<String,double[]> bMap,String code,String type) {
		// TODO Auto-generated method stub
//		ArrayList<String> Nbenifit=toArrayByFileReader1("NBenifit.txt");
		try {
			Workbook workbook = Workbook.getWorkbook(new File("caibao/"+code+"_"+type+".xls"));
			Sheet sheet = workbook.getSheet(0);
			int nc = sheet.getColumns();
			nc = nc<11?nc:11;
			int nr=sheet.getRows();
			for(int i=1;i<nr;i++) {
				Cell TempNCell =sheet.getCell(0,i); 
				if("".equals(TempNCell.getContents().trim()) )
					continue;
				double[] dvalue = new double[nc-1];
				for(int k=1;k<nc;k++) {
				    Cell tempCell =sheet.getCell(k,i); 
				    if (tempCell.getType() == CellType.NUMBER) 
				    { 
				    	dvalue[k-1] = ((NumberCell)tempCell).getValue(); 
				    } else {
				    	dvalue[k-1]=Double.MIN_VALUE;
				    }
				}
				bMap.put(TempNCell.getContents(),dvalue);
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				arrayList.add(str.trim());
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayList;
	}
}
