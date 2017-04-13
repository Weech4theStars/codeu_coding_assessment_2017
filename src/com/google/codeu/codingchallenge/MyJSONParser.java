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
	in = in.replaceAll("\\s+", "");
	if(in.length() < 1)
		throw new IOException(); 
    if(in.charAt(0) != '{' || in.charAt(in.length() - 1) != '}')
    	throw new IOException();
    in = in.substring(1,in.length() - 1);
    String[] parts = in.split(",");
    String key, value;
    HashMap<String,Object> hm = new HashMap<String,Object>(parts.length);
    for(int i = 0; i < parts.length; i++){
    	key = parts[i].substring(0, parts[i].indexOf(":"));
    	value = parts[i].substring(parts[i].indexOf(':')  + 1);
    	if(!isValidString(key))
    		throw new IOException();
    	if(isValidString(value))
    		hm.put(key, value);
    	else
    		hm.put(key, parse(value));
    }
    MyJSON mj = new MyJSON(hm); 
    return mj;
  }
  
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
