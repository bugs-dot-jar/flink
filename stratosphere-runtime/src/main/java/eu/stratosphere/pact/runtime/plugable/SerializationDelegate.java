/***********************************************************************************************************************
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 **********************************************************************************************************************/

package eu.stratosphere.pact.runtime.plugable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.api.common.typeutils.TypeSerializer;
import eu.stratosphere.core.io.IOReadableWritable;
import eu.stratosphere.core.memory.OutputViewDataOutputWrapper;


public class SerializationDelegate<T> implements IOReadableWritable {
	
	private T instance;
	
	private final TypeSerializer<T> serializer;
	
	private final OutputViewDataOutputWrapper wrapper;
	
	
	public SerializationDelegate(TypeSerializer<T> serializer) {
		this.serializer = serializer;
		this.wrapper = new OutputViewDataOutputWrapper();
	}
	
	public void setInstance(T instance) {
		this.instance = instance;
	}
	
	public T getInstance() {
		return this.instance;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		this.wrapper.setDelegate(out);
		this.serializer.serialize(this.instance, this.wrapper);
	}


	@Override
	public void read(DataInput in) throws IOException {
		throw new IllegalStateException("Deserialization method called on SerializationDelegate.");
	}
}