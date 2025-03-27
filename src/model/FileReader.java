package model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileReader
{
	private String path;
	private Scanner inputReader = null;
		
	public FileReader(String path) {
		this.path = path;
	}
	
	public boolean initReader() {
		try
		{
			FileInputStream inputStream = new FileInputStream(path);
			inputReader = new Scanner(inputStream);
		} catch(FileNotFoundException e) {
			return false;
		}
		return true;
	}
	
	public ArrayList<ArrayList<String>> readFile() {
		ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
		initReader();
		String l = "";
		while (inputReader.hasNextLine()) {
			ArrayList<String> line = new ArrayList<String>();
			l = inputReader.nextLine();
			StringTokenizer token = new StringTokenizer(l, " ");
			while(token.hasMoreTokens()) {
				
				line.add(token.nextToken());
			}
			info.add(line);
		}
		closeReader();
		return info;
	}
	
	public void closeReader() {
		inputReader.close();
	}
}