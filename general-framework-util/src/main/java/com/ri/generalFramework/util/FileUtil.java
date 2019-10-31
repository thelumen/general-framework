package com.ri.generalFramework.util;

import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	/**
	 * 创建文件 同时创建文件所在的文件夹
	 * @param file
	 * @throws IOException
	 */
	public static void createFile(File file) throws IOException{
		File fileDir = new File(file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator)));
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		file.createNewFile();
	}
	/**
	 * 根据路径获取文件--文件不存在则创建
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String filePath) throws IOException{
		File file = new File(filePath);
		if(!file.exists()){
			createFile(file);
		}
		return file;
	}
	/**
	 * 获取路径下所有文件列表
	 * @param filePath
	 * @return
	 */
	public static String[] getFileList(String filePath){
		File inputFiles = new File(filePath);
		String[] fileArr = inputFiles.list();
		if(fileArr == null){
			fileArr = new String[]{};
		}
		Arrays.sort(fileArr);
		return fileArr;
	}
	public static FileWriter getFileWriter(File file) throws IOException{
		FileWriter fileWriter = new FileWriter(file);
		return fileWriter;
	}
	public static FileReader getFileReader(File file) throws IOException{
		FileReader fileReader = new FileReader(file);
		return fileReader;
	}
	public static BufferedWriter getBuffeWriter(FileWriter fileWriter){
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		return bufferedWriter;
	}
	public static BufferedReader getBufferedReader(FileReader fileReader){
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}
	
	public static void closeWriter(Writer writer) throws IOException{
		if(writer != null){
			try {
				writer.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}
	public static void closeReader(Reader reader) throws IOException{
		if(reader != null){
			try {
				reader.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}
	public static void copyFile(String fromPath,String toPath) throws IOException{
		BufferedReader fileReader = null;
		BufferedWriter fileWriter = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fromPath),"UTF-8"));
			fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFile(toPath)),"UTF-8"));
			String read = "";
			while((read = fileReader.readLine())!=null){
				fileWriter.write(read);
				fileWriter.newLine();
			}
			fileWriter.flush();
		} catch (IOException e) {
			throw e;
		}finally{
			closeReader(fileReader);
			closeWriter(fileWriter);
		}
	}
	public static void copyFile(InputStream in,OutputStream out) throws IOException{
		BufferedReader fileReader = null;
		BufferedWriter fileWriter = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			fileWriter = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			String read = "";
			while((read = fileReader.readLine())!=null){
				fileWriter.write(read);
				fileWriter.newLine();
			}
			fileWriter.flush();
		} catch (IOException e) {
			throw e;
		}finally{
			FileUtil.closeReader(fileReader);
			FileUtil.closeWriter(fileWriter);
		}
	}
	public static void zipFiles(File[] srcFiles, File zipFile) throws Exception {
        // 判断压缩后的文件存在不，不存在则创建
		zipFile.delete();
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;

        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (int i = 0; i < srcFiles.length; i++) {
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles[i]);
                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                zipEntry = new ZipEntry(srcFiles[i].getName());
                zipOutputStream.putNextEntry(zipEntry);
                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
            }
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw e;
        }finally{
       	 if(zipOutputStream != null){
       		 try {
       			 zipOutputStream.close();
       		 } catch (IOException e) {
       			 throw e;
       		 }
       	 }
       	 if(fileInputStream != null){
       		 try {
       			 fileInputStream.close();
       		 } catch (IOException e) {
       			 throw e;
       		 }
       	 }
       	 if(fileOutputStream != null){
       		 try {
       			 fileOutputStream.close();
       		 } catch (IOException e) {
       			 throw e;
       		 }
       	 }
        }
    }
}
