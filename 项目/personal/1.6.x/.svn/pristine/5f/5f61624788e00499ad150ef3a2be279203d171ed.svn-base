package org.opoo.apps.file.openoffice.sheet.cli;

import java.io.File;
import java.net.ConnectException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.FilenameUtils;
import org.opoo.apps.file.openoffice.RefreshableOpenOfficeDocumentConverter;
import org.opoo.apps.file.openoffice.sheet.SpreadsheetRowOptimalHeightRefresher;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;


/**
 * 使用命令行式的的指令刷新Excel,ODS文件，设置所有的行为”最合适行高“，并根据参数设定
 * 边距。
 * 命令行根据输出文件的格式还可以进行格式转换。
 * 该功能是基于OPENOFFICE实现的，能处理的文件格式和处理后能转换的文件格式依赖于OpenOffice
 * 的支持。
 * 
 * 命令行参数：
 * <pre>
 *  -f,--output-format <arg>   output format (e.g. pdf) 输出文档的格式
 *  -m,--margin <arg>          Margin of cell，表格边距
 *  -p,--port <arg>            OpenOffice.org port OpenOffice服务运行的端口
 *  -v,--verbose               verbose
 * </pre>
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class XLSRowOptimalHeightCommand {
	private static final Option OPTION_OUTPUT_FORMAT = new Option("f", "output-format", true, "output format (e.g. pdf)");
	private static final Option OPTION_PORT = new Option("p", "port", true, "OpenOffice.org port");
	private static final Option OPTION_VERBOSE = new Option("v", "verbose",	false, "verbose");
	private static final Option OPTION_MARGIN = new Option("m", "margin", true, "Margin of cell");
	//private static final Option OPTION_BORDER = new Option("b", "border", true, "Border of cell");
	private static final Options OPTIONS = initOptions();

	private static final int EXIT_CODE_CONNECTION_FAILED = 1;
	private static final int EXIT_CODE_TOO_FEW_ARGS = 255;

	private static Options initOptions() {
		Options options = new Options();
		options.addOption(OPTION_OUTPUT_FORMAT);
		options.addOption(OPTION_PORT);
		options.addOption(OPTION_VERBOSE);
		options.addOption(OPTION_MARGIN);
		//options.addOption(OPTION_BORDER);
		return options;
	}

	public static void main(String[] arguments) throws Exception {
		CommandLineParser commandLineParser = new PosixParser();
		CommandLine commandLine = commandLineParser.parse(OPTIONS, arguments);

		int port = SocketOpenOfficeConnection.DEFAULT_PORT;
		if (commandLine.hasOption(OPTION_PORT.getOpt())) {
			port = Integer.parseInt(commandLine.getOptionValue(OPTION_PORT.getOpt()));
		}

		String outputFormat = null;
		if (commandLine.hasOption(OPTION_OUTPUT_FORMAT.getOpt())) {
			outputFormat = commandLine.getOptionValue(OPTION_OUTPUT_FORMAT.getOpt());
		}
		
		int margin = 135;
		if(commandLine.hasOption(OPTION_MARGIN.getOpt())){
			margin = Integer.parseInt(commandLine.getOptionValue(OPTION_MARGIN.getOpt()));
		}
		
//		short border = 20;
//		if(commandLine.hasOption(OPTION_BORDER.getOpt())){
//			border = Short.parseShort(commandLine.getOptionValue(OPTION_BORDER.getOpt()));
//		}

		boolean verbose = false;
		if (commandLine.hasOption(OPTION_VERBOSE.getOpt())) {
			verbose = true;
		}

		String[] fileNames = commandLine.getArgs();
		if ((outputFormat == null && fileNames.length != 2) || fileNames.length < 1) {
			String syntax = "convert [options] input-file output-file; or\n"
					+ "[options] -f output-format input-file [input-file...]";
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp(syntax, OPTIONS);
			System.exit(EXIT_CODE_TOO_FEW_ARGS);
		}
		
//		XConnectionProvider cp = new SocketXConnectionProvider(port);
//		XLSRowOptimalHeightRefreshUpdater updater = new XLSRowOptimalHeightRefreshUpdater(cp);
//		updater.update(input, output)
		

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(port);
		try {
			if (verbose) {
				System.out.println("-- connecting to OpenOffice.org on port " + port);
			}
			connection.connect();
		} catch (ConnectException officeNotRunning) {
			System.err.println("ERROR: connection failed. Please make sure OpenOffice.org is running and listening on port "
							+ port + ".");
			System.exit(EXIT_CODE_CONNECTION_FAILED);
		}
		try {
			RefreshableOpenOfficeDocumentConverter converter = new RefreshableOpenOfficeDocumentConverter(connection);
			
			SpreadsheetRowOptimalHeightRefresher srohr = new SpreadsheetRowOptimalHeightRefresher();
//			srohr.setBorder(border);
			srohr.setMargin(margin);
			converter.setComponentRefresher(srohr);
			
			if (outputFormat == null) {
				File inputFile = new File(fileNames[0]);
				File outputFile = new File(fileNames[1]);
				convertOne(converter, inputFile, outputFile, verbose);
			} else {
				for (int i = 0; i < fileNames.length; i++) {
					File inputFile = new File(fileNames[i]);
					File outputFile = new File(FilenameUtils
							.getFullPath(fileNames[i])
							+ FilenameUtils.getBaseName(fileNames[i])
							+ "."
							+ outputFormat);
					convertOne(converter, inputFile, outputFile, verbose);
				}
			}
		} finally {
			if (verbose) {
				System.out.println("-- disconnecting");
			}
			connection.disconnect();
		}
	}

	private static void convertOne(DocumentConverter converter, File inputFile,	File outputFile, boolean verbose) {
		if (verbose) {
			System.out.println("-- converting " + inputFile + " to " + outputFile);
		}
		converter.convert(inputFile, outputFile);
	}
}
