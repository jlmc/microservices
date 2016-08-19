# Java 8 LocalDate with Jackson Serialize and Deserialize Example - RESTful WS

We have used Joda Time for years, it is now time to migrate over to the Java 8 Date and Time API (JSR310), and make use of the new LocalDate and LocalTime classes.

wildfly-10.0.0 uses Jackson 2.5.4 which does not know how to (de)serialize the JSR310 Date Time classes. So in order to use the Date and Time API we need to add a Jackson third party datatype dependency to our pom:

so we can add the following dependencies to the pom with a scope of provided:

```

		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.5.4</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-annotations</artifactId>
			<version>2.5.4</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.5.4</version>
			<scope>provided</scope>
		</dependency>
```

Then on the LocalDateTime property we can use the following annotations:

```

	import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
	import com.fasterxml.jackson.databind.annotation.JsonSerialize;
	
	public class Part implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		@JsonDeserialize(using = JsonDateDeserializer.class)
		@JsonSerialize(using = JsonDateSerializer.class)
		private LocalDateTime dueDate;
		private BigDecimal value;
	
		protected Part() {
		}
	
		public static Part of(LocalDateTime dueDate, BigDecimal value) {
			final Part part = new Part();
			part.dueDate = dueDate;
			part.value = value;
			return part;
		}
	
		public LocalDateTime getDueDate() {
			return this.dueDate;
		}
	
		public BigDecimal getValue() {
			return this.value;
		}
	}

```

And finally, we create the custom Serializer and Deserializer classes

```

	import java.io.IOException;
	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;
	
	import com.fasterxml.jackson.core.JsonGenerator;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.JsonSerializer;
	import com.fasterxml.jackson.databind.SerializerProvider;
	
	public class JsonDateSerializer extends JsonSerializer<LocalDateTime> {
	
		private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
		@Override
		public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider arg2)
				throws IOException, JsonProcessingException {
			final String dateString = date.format(this.formatter);
			generator.writeString(dateString);
	
		}
	}

```


```

	import java.io.IOException;
	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;
	
	import com.fasterxml.jackson.core.JsonParser;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.core.ObjectCodec;
	import com.fasterxml.jackson.databind.DeserializationContext;
	import com.fasterxml.jackson.databind.JsonDeserializer;
	import com.fasterxml.jackson.databind.node.TextNode;
	
	public class JsonDateDeserializer extends JsonDeserializer<LocalDateTime> {
	
		@Override
		public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			final ObjectCodec oc = jp.getCodec();
			final TextNode node = (TextNode) oc.readTree(jp);
			final String dateString = node.textValue();
	
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			return LocalDateTime.parse(dateString, formatter);
		}
	}


```


