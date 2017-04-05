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

final class MyJSON implements JSON {

  /**
   * Gets the value of the nested object with the given name. If there is
   * no nested object with that name, the method will return null.
   * 
   * @param name the name of desired JSON object
   * @return the JSON object with the given name. 
   * If no such object exists, returns null
   */
  @Override
  public JSON getObject(String name) {
    // TODO: implement
    return null;
  }

  /**
   * Sets the value of the nested object with the given name. Any old value
   * should be overwritten. This method will always return a reference to the 
   * MyJSON object
   * 
   * @param name the name of desired JSON object
   * @param value new value of the of the designated JSON object 
   * @return reference to the modified JSON object
   * If no such object exists, returns null
   */
  @Override
  public JSON setObject(String name, JSON value) {
    // TODO: implement
    return this;
  }

  /**
   * Gets the String value within this JSON object that has the given name. if
   * there is no String with the given name, the method will return null.
   * 
   * @param name the name of desired JSON object
   * @return the String value within the JSON object
   */
  @Override
  public String getString(String name) {
    // TODO: implement this
    return null;
  }
  
  /**
   * Sets the String that should be stored under the given name. Any old value
   * should be overwritten. This method will always return a reference to
   * "this".
   * 
   *  @param name the name of desired JSON object
   *  @param value the new String value of the JSON object
   *  @return a reference to the JSON object
   */
  @Override
  public JSON setString(String name, String value) {
    // TODO: implement this
    return this;
  }

  /**
   * Copies the names of all object values to the given collection.
   * 
   * @param names the String Collection to save the names of the object values to 
   */
  @Override
  public void getObjects(Collection<String> names) {
    // TODO: implement this
  }
  
  /**
   * Copies the names of all String values to the given collection.
   * 
   * @param names the String Collection to save all the names of the String values to
   */
  @Override
  public void getStrings(Collection<String> names) {
    // TODO: implement this
  }
}
