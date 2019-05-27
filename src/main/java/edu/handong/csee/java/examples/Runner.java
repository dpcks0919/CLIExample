package edu.handong.csee.java.examples;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Runner {
	
	String path;
	boolean fullpath;
	boolean verbose;
	boolean help;

	public static void main(String[] args) {

		Runner myRunner = new Runner();
		myRunner.run(args);

	}

	private void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
			// path is required (necessary) data so no need to have a branch.
			System.out.println("You provided \"" + path + "\" as the value of the option p");
			

			int lastIndex = path.lastIndexOf("/");
			File file = new File(path.substring(0,lastIndex+1));
			
			// TODO show the number of files in the path
			System.out.println( file.listFiles().length );
				
			if(verbose) {
				
				// TODO list all files in the path			
				if(file.isDirectory()) {
					File[] fileList = file.listFiles();
					for(File tFile: fileList) {
						System.out.println(tFile.getName());
					}
				}		
				System.out.println("Your program is terminated. (This message is shown because you turned on -v option!");
			}
			
			
			if(fullpath) {
				File fullpath = new File(path);
			    try {
			        System.out.println(fullpath.getCanonicalPath());
			      } catch (IOException e) {
			        System.err.println(e);
			        System.exit(1);
			      }
			}
		}
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			path = cmd.getOptionValue("p");
			verbose = cmd.hasOption("v");
			help = cmd.hasOption("h");
			fullpath = cmd.hasOption("f");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("p").longOpt("path")
				.desc("Set a path of a directory or a file to display")
				.hasArg()
				.argName("Path name to display")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("v").longOpt("verbose")
				.desc("Display detailed messages!")
				//.hasArg()     // this option is intended not to have an option value but just an option
				.argName("verbose option")
				//.required() // this is an optional option. So disabled required().
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("f").longOpt("fullpath")
				.desc("Display directories!")
				//.hasArg()     // this option is intended not to have an option value but just an option
				.argName("fullpath option")
				//.required() // this is an optional option. So disabled required().
				.build());


		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "CLI test program";
		String footer ="\nPlease report issues at https://github.com/lifove/CLIExample/issues";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}
}
