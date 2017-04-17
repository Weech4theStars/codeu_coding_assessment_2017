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

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

final class MyJSON implements JSON {
	private HashMap<String, Object> hm;
	
	public MyJSON(HashMap<String, Object> hm) { 
		this.hm = hm; 
	}
	
	public MyJSON() {
		this.hm = new HashMap<String,Object>();
	}

  @Override
  public JSON getObject(String name) {
	  if(this.hm.get(name) instanceof String) 
		  return null;
      return (JSON)this.hm.get(name);		
  }

  @Override
  public JSON setObject(String name, JSON value) {
	  this.hm.put(name, value);
	  return this;
  }

  @Override
  public String getString(String name) {
	  if(!(this.hm.get(name) instanceof String)) { 
		  return null;
	  } else 
		  return (String)this.hm.get(name);
  }
  
  @Override
  public JSON setString(String name, String value) {
    this.hm.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
	  Object[] keys = hm.keySet().toArray();
	  String key;
	  for(int i = 0; i < keys.length; i++){
		  key = (String)keys[i];
		  if(hm.get(key) instanceof String)
			  names.add((String)hm.get(key));
	  }
	  
  }
  
  @Override
  public void getStrings(Collection<String> names) {
	  Object[] keys = hm.keySet().toArray();
	  String key;
	  for(int i = 0; i < keys.length; i++){
		  key = (String)keys[i];
		  if(!(hm.get(key) instanceof String))
			  names.add((String)hm.get(key));
	  }
  }
}
