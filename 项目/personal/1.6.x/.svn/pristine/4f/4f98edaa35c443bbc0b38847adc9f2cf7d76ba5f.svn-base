package org.opoo.apps.dv.provider;

import java.io.File;

/**
 * 如果被转换对象是存储在文件系统中的，为了减少生成过多的临时文件，
 * 所以可以将 {@link ConvertibleObjectProvider} 同时实现 FileProvider 接口,
 * 为调用者提供原始文件。
 * 
 * @author lcj
 */
public interface FileProvider {
	/**
	 * 返回可转换对象的文件。
	 * @return
	 */
	File getFile();
}
