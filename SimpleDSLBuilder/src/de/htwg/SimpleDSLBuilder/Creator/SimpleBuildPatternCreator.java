package de.htwg.SimpleDSLBuilder.Creator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**-
 * Defines Meta Model
 *
 */
public class SimpleBuildPatternCreator {
	public static final String REGEX_PATTERN= "dslName=\\w+\\.ep=\\w+(\\.m=\\w+\\(\\w+\\s\\w+\\))+\\.build=\\w+";
	public static final String DSL_NAME = "dslName=\\w+";
	public static final String ENTRY_POINT = "\\.ep=\\w+";
	public static final String METHODS = "\\.m=\\w+\\(\\w+\\s\\w+\\)";
	public static final String BUILD = "\\.build=\\w+";
	private static final String NO_MATCH = "Given String does not match BuildPatternCreator Regex Pattern: \n" +REGEX_PATTERN;
	private static final Pattern NAME_PATTERN = Pattern.compile(DSL_NAME, Pattern.CASE_INSENSITIVE);
	private static final Pattern EP_PATTERN = Pattern.compile(ENTRY_POINT, Pattern.CASE_INSENSITIVE);
	private static final Pattern METHOD_PATTERN= Pattern.compile(METHODS, Pattern.CASE_INSENSITIVE);
	private static final Pattern BUILD_PATTERN = Pattern.compile(BUILD, Pattern.CASE_INSENSITIVE);
	
	private String languageDescr;
	private Matcher nameMatcher;
	private Matcher epMatcher;
	private Matcher methodMatcher;
	private Matcher buildMatcher;
	
	private String dslName;
	private String entryPointMethod;
	private ArrayList<String> chainMethods;
	private String buildMethodName;
	
	private SimpleBuildPatternCreator(){}
	
	public static SimpleBuildPatternCreator getInstance(String languageDescr){
		if(!languageDescr.matches(REGEX_PATTERN)){
			throw new IllegalArgumentException(NO_MATCH);
		}
		SimpleBuildPatternCreator creator = new SimpleBuildPatternCreator();
		creator.languageDescr = languageDescr;
		creator.nameMatcher = NAME_PATTERN.matcher(creator.languageDescr);
		creator.epMatcher = EP_PATTERN.matcher(creator.languageDescr);
		creator.methodMatcher = METHOD_PATTERN.matcher(creator.languageDescr);
		creator.buildMatcher = BUILD_PATTERN.matcher(creator.languageDescr);
		return creator;
	}
	
	public String getDslName(){
		if(this.dslName == null && this.nameMatcher.find()){
			String found = nameMatcher.group();
			this.dslName = found.substring(8);
		}
		return this.dslName;
	}
	
	public String getEntryPointMethod(){
		if(this.entryPointMethod == null && this.epMatcher.find()){
			String found = epMatcher.group();
			this.entryPointMethod = found.substring(4);
		}
		return this.entryPointMethod;
	}
	
	public ArrayList<String> getChainMethods(){
		if(this.chainMethods != null)
			return this.chainMethods;
		this.chainMethods = new ArrayList<>();
		while(this.methodMatcher.find()){
			String found = methodMatcher.group();
			String method = found.substring(3);
			chainMethods.add(method);
		}
		return chainMethods;
	}
	
	public String getBuildMethodName(){
		if(this.buildMethodName == null && this.buildMatcher.find()){
			String found = buildMatcher.group();
			buildMethodName = found.substring(7);
		}
		return this.buildMethodName;
	}
	

}
