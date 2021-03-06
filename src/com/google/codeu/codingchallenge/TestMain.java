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
import java.util.Collection;
import java.util.HashSet;

final class TestMain {

  public static void main(String[] args) {

    final Tester tests = new Tester();

    tests.add("Empty Object", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ }");

        final Collection<String> strings = new HashSet<>();
        obj.getStrings(strings);

        Asserts.isEqual(strings.size(), 0);

        final Collection<String> objects = new HashSet<>();
        obj.getObjects(objects);

        Asserts.isEqual(objects.size(), 0);
      }
    });

    tests.add("String Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":\"sam doe\" }");

        Asserts.isEqual("sam doe", obj.getString("name"));
     }
    });

    tests.add("Object Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));
      }
    });
    
    tests.add("Multiple JSON Object Mappings", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {
          final JSONParser parser = factory.parser();
          String test = "{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" },"
        		  		+ "\"test\":{\"blah\":\"blahblah\"},"
        		  		+ "\"test2\": \"blah\"}";

          final JSON obj = parser.parse(test);
          final JSON nameObj = obj.getObject("name");
          
          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam", nameObj.getString("first"));
          Asserts.isEqual("doe", nameObj.getString("last"));
          
          final JSON nameObj2 = obj.getObject("test");
          
          Asserts.isNotNull(nameObj2);
          Asserts.isEqual("blahblah", nameObj2.getString("blah"));
          
          Asserts.isEqual("blah", obj.getString("test2"));

          final Collection<String> strings = new HashSet<>();
          obj.getStrings(strings);

          Asserts.isEqual(strings.size(), 1);

          final Collection<String> objects = new HashSet<>();
          obj.getObjects(objects);

          Asserts.isEqual(objects.size(), 2);
        }
      });
    
    tests.add("Multiple JSON Object Mappings, Different Order", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {
          final JSONParser parser = factory.parser();
          String test = "{ \"name\":\"john doe\","
        		  		+ "\"name2\": \"jane doe\","
        		  		+ "\"test\": {\"blah\": \"blahblah\"}}";

          final JSON obj = parser.parse(test);
          final JSON nameObj = obj.getObject("test");
          
          Asserts.isNotNull(nameObj);
          Asserts.isEqual("blahblah", nameObj.getString("blah"));
          
          final String nameString = obj.getString("name2");
          
          Asserts.isEqual(nameString, "jane doe");
          
          final Collection<String> strings = new HashSet<>();
          obj.getStrings(strings);

          Asserts.isEqual(strings.size(), 2);

          final Collection<String> objects = new HashSet<>();
          obj.getObjects(objects);

          Asserts.isEqual(objects.size(), 1);

        }
      });
    
    tests.add("Excess Whitespace", new Test() {
        @Override
        public void run(JSONFactory factory) throws Exception {

          final JSONParser parser = factory.parser();
          final JSON obj = parser.parse("   {      \"name   \"  :    {  \"first\"  :  \"sam\","
        		  						+ "\"last\":    \"doe\"  }  }");

          final JSON nameObj = obj.getObject("name   ");

          Asserts.isNotNull(nameObj);
          Asserts.isEqual("sam", nameObj.getString("first"));
          Asserts.isEqual("doe", nameObj.getString("last"));
        }
      });

// would like to test that certain errors are thrown, but am uncertain how to do that within the 
// provided Test.java framework
    
//    tests.add("Improperly Escaped Character", new Test() {
//        @Override
//        public void run(JSONFactory factory) throws Exception {
//
//          final JSONParser parser = factory.parser();
//          
//          try {
//        	  final JSON obj = parser.parse("{ \"na\\me\":{\"first\":\"sam\", \"last\":\"doe\" } }");
//          } catch(IOException e) {
//        	 Asserts.isEqual(e, "key is not a valid  JSON-lite String");
//          }
//        
//         }
//      });

    tests.run(new JSONFactory(){
      @Override
      public JSONParser parser() {
        return new MyJSONParser();
      }

      @Override
      public JSON object() {
        return new MyJSON();
      }
    });
  }
}
