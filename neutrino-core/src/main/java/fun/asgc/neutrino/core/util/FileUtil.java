/**
 * Copyright (c) 2022 aoshiguchen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fun.asgc.neutrino.core.util;

import fun.asgc.neutrino.core.constant.MetaDataConstant;
import fun.asgc.neutrino.core.exception.InternalException;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 *
 * @author: aoshiguchen
 * @date: 2022/6/16
 */
public class FileUtil {

	/**
	 * 根据文件路径获取输入流
	 * @param path
	 * @return
	 */
	public static InputStream getInputStream(String path) throws FileNotFoundException {
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path 不能为空!");
		}
		if (path.startsWith(MetaDataConstant.CLASSPATH_RESOURCE_IDENTIFIER)) {
			String subPath = path.substring(MetaDataConstant.CLASSPATH_RESOURCE_IDENTIFIER.length());
			return FileUtil.class.getResourceAsStream(subPath);
		}
		return new FileInputStream(path);
	}

	/**
	 * 根据文件路径获取输出流
	 * @param path
	 * @return
	 */
	public static OutputStream getOutputStream(String path) throws FileNotFoundException {
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path 不能为空!");
		}
		if (path.startsWith(MetaDataConstant.CLASSPATH_RESOURCE_IDENTIFIER)) {
			throw new InternalException("不支持路径以'" + MetaDataConstant.CLASSPATH_IDENTIFIER + "'开头");
		}
		return new FileOutputStream(path);
	}

	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readContentAsString(String path) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(path)))){
			return br.lines().collect(Collectors.joining("\n"));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 读取文件内容
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String readContentAsString(InputStream in) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))){
			return br.lines().collect(Collectors.joining("\n"));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String> readContentAsStringList(String path) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(path)))){
			return br.lines().collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 读取字节内容
	 * @param path
	 * @return
	 */
	public static byte[] readBytes(String path) {
		try (InputStream in = getInputStream(path)){
			return readBytes(in);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] readBytes(File file) {
		try (InputStream in = new FileInputStream(file)){
			return readBytes(in);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] readBytes(InputStream in) throws IOException {
		byte[] bytes = new byte[1024];
		int length = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((length = in.read(bytes)) != -1) {
			baos.write(bytes, 0, length);
		}
		return baos.toByteArray();
	}

	public static void write(String path, String content) {
//		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8)){
//			writer.write(content);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getOutputStream(path)))){
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File getDirectory(String path) {
		if (path.endsWith("/./")) {
			path = path.substring(0, path.length() - 2);
		}
		File file = new File(path);
		if (file.isDirectory() || path.endsWith("/")) {
			return file;
		}
		return file.getParentFile();
	}

	public static void makeDirs(String path) {
		try {
			File file = getDirectory(path);
			if (null != file && !file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			//  ignore
		}
	}

	public static boolean deleteDirs(String path) {
		File file = getDirectory(path);
		try {
			FileDeleteStrategy.FORCE.delete(file);
		} catch (Exception e) {
			file = getDirectory(file.getPath());
			try {
				FileDeleteStrategy.FORCE.delete(file);
			} catch (IOException ex) {
				return false;
			}
		}
		return true;
	}

	public static File save(String path, String fileName, String content) {
		if (!path.endsWith("/")) {
			path += "/";
		}
		makeDirs(path);
		write(path + fileName, content);
		return new File(path + "/" + fileName);
	}

	public static void unzipJar(String destinationDir, String jarPath) throws IOException {
		File file = new File(jarPath);
		JarFile jar = new JarFile(file);
		for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
			JarEntry entry = (JarEntry) enums.nextElement();
			String fileName = destinationDir + File.separator + entry.getName();
			File f = new File(fileName);
			if (fileName.endsWith("/")) {
				f.mkdirs();
			}
		}
		for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
			JarEntry entry = (JarEntry) enums.nextElement();
			String fileName = destinationDir + File.separator + entry.getName();
			File f = new File(fileName);
			if (!fileName.endsWith("/")) {
				InputStream is = jar.getInputStream(entry);
				FileOutputStream fos = new FileOutputStream(f);
				while (is.available() > 0) {
					fos.write(is.read());
				}
				fos.close();
				is.close();
			}
		}
	}

	public static String getFileSuffix(String path) {
		if (StringUtils.isBlank(path)) {
			return "";
		}
		int index = path.lastIndexOf(".");
		if (index >= 0) {
			return path.substring(index);
		}
		return "";
	}
}
