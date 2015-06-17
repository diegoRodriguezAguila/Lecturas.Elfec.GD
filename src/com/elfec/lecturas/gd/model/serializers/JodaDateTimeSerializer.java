package com.elfec.lecturas.gd.model.serializers;

import org.joda.time.DateTime;

import com.activeandroid.serializer.TypeSerializer;

public class JodaDateTimeSerializer extends TypeSerializer  {

	@Override
	public DateTime deserialize(Object data) {
		if (data == null) {
			return null;
		}

		return new DateTime((Long) data);
	}

	@Override
	public Class<?> getDeserializedType() {
		return DateTime.class;
	}

	@Override
	public Class<?> getSerializedType() {
		return long.class;
	}

	@Override
	public Long serialize(Object data) {
		if (data == null) {
			return null;
		}

		return ((DateTime) data).getMillis();
	}

}
