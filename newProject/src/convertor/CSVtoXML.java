package convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVtoXML {
	
	public List<List<String>> parseCSVFile(File file){
		
		List<List<String>> lines = new ArrayList<>();
		List<String> rows = new ArrayList<>();
		String path = file.getAbsolutePath();
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line = reader.readLine();
			while(line != null) {
				rows = Arrays.asList(line.split(","));
				lines.add(rows);
				line = reader.readLine();
			}
			reader.close();
		}catch (IOException e) {
			System.out.println(e);
		}
		return lines;
	}
	
	public int rowsCount(List<List<String>> fileData) {
		return fileData.size();
	}

	public int getColumns(List<List<String>> fileData) {
		List<String> getNumberOfColumns = fileData.get(0);
		return getNumberOfColumns.size();
	}
	
	public List<String> getHeader(List<List<String>> fileData){
		List<String> headerInfo = new ArrayList<>();
		headerInfo = fileData.get(0);
		return headerInfo;
	}
	
	public String printXML(String values, String header) {
		String outPut;
		StringBuilder strOutput = new StringBuilder();
		strOutput.append("<");
		strOutput.append(header);
		strOutput.append(">");
		strOutput.append(values);
		strOutput.append("</");
		strOutput.append(header);
		strOutput.append(">");
		outPut = strOutput.toString();
		return outPut;
		
	}
	
	public static void main(String[] args) throws IOException {
		
		CSVtoXML objectXML = new CSVtoXML();
		String filePath = "P:\\WorkSheet.csv";
		int rows,columns = 0;
		List<String> headerValues = new ArrayList<>();
		File file = new File(filePath);
		File fileXML = new File("generatedXML.xml");
		if(!fileXML.exists()) {
			fileXML.createNewFile();
		}
		PrintWriter pr = new PrintWriter(fileXML);
		
		List<List<String>> fileData = objectXML.parseCSVFile(file);
		rows = objectXML.rowsCount(fileData);
		columns = objectXML.getColumns(fileData);
		headerValues = objectXML.getHeader(fileData);
		String[] stringArray = headerValues.toArray(new String[0]);
		//processing data to XML
//		pr.println(""); - add if there is a parent prefix on the XML
		for(int i = 0;i < rows; i++) {
			if(i == 0) {
				continue; // adding this to skip the first line
			}
			pr.println("<Row>");
			for(int j = 0; j < columns;j++) {
				String a = fileData.get(i).get(j);
				String b;
				String obj = stringArray[j];
				b = objectXML.printXML(a,obj);
				pr.println(b);
			}
			pr.println("</Row>");
		}
		pr.close();
	}
}
