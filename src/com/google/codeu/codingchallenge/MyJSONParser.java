// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.HashMap;

final class MyJSONParser implements JSONParser {

  /**
   * Given a string that should be a valid JSON-lite object encoded as a String
   * return the parsed object. If for any reason the String is found to be
   * invalid, the method should throw an IOException.
   * 
   * @param in the String representation of a valid JSON-lite object
   */
  @Override
  public JSON parse(String in) throws IOException {
	//remove excess whitespace
	in = in.trim(); 
	
	if(in.length() < 1)
		throw new IOException("you tried to parse an empty string"); 
    
	if(in.charAt(0) != '{' || in.charAt(in.length() - 1) != '}')
    	throw new IOException("JSON object is missing a '{' or '}'");
    
	//remove '{' and '}"
	in = in.substring(1,in.length() - 1).trim(); 
    
	//initialize HashMap for MyJSON constructor
	String key, value;
    HashMap<String,Object> hm = new HashMap<String,Object>(); 
    
    try { 
    	while(in.length() > 1) {
    		// split input into first key and the rest of the input
    		key = in.substring(0, in.indexOf(":")).trim();
    		value = in.substring(in.indexOf(":") + 1).trim();
    		
    		if(!isValidString(key))
    			throw new IOException("key is not a valid  JSON-lite String");
    		
    		//check for JSON object 
    		if(value.contains("{") && value.contains(",")){
    			
    			//if a comma precedes the {, there is at least one String:String pair to be mapped
    			if(value.indexOf(",") < value.indexOf("{")) {
    					value = value.substring(0, value.indexOf(",")).trim();
        				in = in.substring(in.indexOf(",") + 1).trim();
        				if(isValidString(value)) {
        					key = key.substring(1, key.length() - 1);
        					value = value.substring(1, value.length() - 1);
        					hm.put(key, value);
        				} else
        					throw new IOException("value is not a valid JSON-lite String"); 			
    			
        		//otherwise, map the String:Object pair
    			} else {
        			value = value.substring(0,value.indexOf("}") + 1).trim();
        			key = key.substring(1, key.length() - 1);
        			if(in.indexOf("}") >= in.length() - 1)
        				in = "";
        			else 
        				in = in.substring(in.indexOf("}") + 2);
        			hm.put(key, parse(value));
    			}
    			
    		//if the JSON object is the only remaining key:value to be mapped
    		} else if(in.contains("{")) {
    			value = value.substring(0,value.indexOf("}") + 1).trim();
    			key = key.substring(1, key.length() - 1);
    			if(in.indexOf("}") >= in.length() - 1)
    				in = "";
    			else 
    				in = in.substring(in.indexOf("}") + 2);
    			hm.put(key, parse(value));
    		
    		//if there are no JSON objects in the input
    		} else if(!in.contains("{")) {
    			if(value.contains(",")) {
    				value = value.substring(0, value.indexOf(","));
    				in = in.substring(in.indexOf(",") + 1);
    				if(isValidString(value)) {
    					key = key.substring(1, key.length() - 1);
    					value = value.substring(1, value.length() - 1);
    					hm.put(key, value);
    				} else
    					throw new IOException("value is not a valid JSON-lite String"); 
    			} else {
    				if(isValidString(value)) {
    					key = key.substring(1, key.length() - 1);
    					value = value.substring(1, value.length() - 1);
    					hm.put(key, value);
    					in = "";
    				} else
    					throw new IOException("value is not a valid JSON-lite String");
    			}	
    		}
    	}
    } catch(IndexOutOfBoundsException e) {
    	throw new IOException("an IndexOutOfBoundsException was thrown");
    }
    return new MyJSON(hm); 
  }
  
  /**
   * Given a string that should be a valid JSON-lite String, check to see
   * whether or not the String is actually valid
   * 
   * @param s the String to be checked
   * @return true if the String is valid, false if the String is invalid
   */
  private boolean isValidString(String s) { 
	  if(s.length() < 1)
		  return false;
	  if(!(s.charAt(0) == '"') || !(s.charAt(s.length() - 1) == '"'))
		  return false;
	  if(s.contains("\\")) { 
		  char escChar = s.charAt(s.indexOf('\\') + 1); 
		  if(escChar != '\\' && escChar != 't' && escChar != 'n' && escChar != '\"') { 
			  return false; 
		  } else { 
			  return isValidString("\"" + s.substring(s.indexOf('\\') + 2));
		  }
	  }
	  if(s.substring(1, s.length() - 1).contains("\"")) {
		  try {  
			  char backslash = s.substring(1, s.length() - 1).charAt(s.indexOf('"') - 1);
			  if(backslash != '\\')
				  return false;
			  else { 
				  return isValidString("\"" + s.substring(s.indexOf('"') + 1));
			  }
		  } catch(IndexOutOfBoundsException e) {
			  return false; 
		  }
	  }
	  return true;
  }
}
