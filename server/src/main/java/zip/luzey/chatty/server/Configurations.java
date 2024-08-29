package zip.luzey.chatty.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.ErrorReportConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.toml.TomlFactory;
import com.fasterxml.jackson.dataformat.toml.TomlReadFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.StringJoiner;
import java.util.function.Consumer;

public final class Configurations {
	private static final int CONFIG_VERSION = 1;

	private static final Path CONFIG_PATH = Path.of("config.toml");
	private static final String DEFAULT_CONFIG =
		 """
		  config_version = 1

		  [general]
		  host = "127.0.0.1"
		  port = 1337
		 \s""";

	private final @NotNull Consumer<Throwable> onError;
	private final ObjectMapper mapper;

	private @Nullable ConfigValues data;

	public Configurations() {
		this(Throwable::printStackTrace);
	}

	public Configurations(final @NotNull Consumer<Throwable> onError) {
		this.onError = onError;
		this.mapper = new ObjectMapper(TomlFactory.builder()
		                                          .errorReportConfiguration(ErrorReportConfiguration.defaults())
		                                          .enable(TomlReadFeature.PARSE_JAVA_TIME)
		                                          .build());
		try {
			if (Files.exists(CONFIG_PATH)) return;
			if (Files.notExists(Files.createFile(CONFIG_PATH))) {
				onError.accept(new FileNotFoundException("couldn't be created"));
				return;
			}
			Files.writeString(
				 CONFIG_PATH, DEFAULT_CONFIG,
				 StandardCharsets.UTF_8,
				 StandardOpenOption.APPEND
			);
		} catch (final Throwable throwable) {
			onError.accept(throwable);
		}
	}

	public boolean load() {
		try {
			this.data = mapper.readValue(CONFIG_PATH.toFile(), ConfigValues.class);
			if (this.getData().configVersion() == CONFIG_VERSION) return true;
			this.onError.accept(new RuntimeException("wrong config version, things might be incorrect or break."));
		} catch (final Throwable throwable) {
			this.data = null;
			this.onError.accept(throwable);
		}
		return false;
	}

	public @Nullable ConfigValues getData() {
		return this.data;
	}

	public record ConfigValues(
		 @JsonProperty("config_version") int configVersion,
		 @JsonProperty("general") @NotNull General general
	) {
		public record General(
			 @JsonProperty("host") @NotNull String host,
			 @JsonProperty("port") int port
		) {
			public @Override String toString() {
				return new StringJoiner(", ", General.class.getSimpleName() + "[", "]")
					 .add("host='" + host + "'")
					 .add("port=" + port)
					 .toString();
			}
		}
	}
}
