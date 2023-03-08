// package be.global.aop;
//
// import java.io.OutputStream;
// import java.lang.reflect.Array;
// import java.net.URL;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Set;
//
// import javax.net.ssl.HttpsURLConnection;
//
// import org.springframework.beans.factory.annotation.Value;
//
// import lombok.NoArgsConstructor;
// import lombok.SneakyThrows;
//
// @NoArgsConstructor
// public class DiscordWebhook {
//
// 	private String webhookUrl;
//
// 	private String content;
// 	private boolean tts;
// 	private Embeds embeds;
//
// 	public DiscordWebhook(String url) {
// 		this.webhookUrl = url;
// 	}
//
// 	public void setContent(String content) {
// 		this.content = content;
// 	}
//
// 	public void setTts(boolean tts) {
// 		this.tts = tts;
// 	}
//
// 	public void setEmbeds(Embeds embeds) {
// 		this.embeds = embeds;
// 	}
//
// 	@SneakyThrows
// 	public void execute() {
// 		if (this.content == null) {
// 			throw new IllegalArgumentException("Must have content.");
// 		}
//
// 		JSONObject jsonObject = new JSONObject();
// 		jsonObject.put("content", this.content);
// 		jsonObject.put("tts", this.tts);
// 		jsonObject.put("embeds", this.embeds);
//
// 		URL url = new URL(this.webhookUrl);
// 		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
// 		// HttpURLConnection connection = (HttpURLConnection)url.openConnection();
// 		connection.setDoOutput(true);
// 		connection.setRequestMethod("POST");
// 		connection.addRequestProperty("Content-Type", "application/json");
//
// 		OutputStream outputStream = connection.getOutputStream();
// 		outputStream.write(jsonObject.toString().getBytes());
// 		outputStream.flush();
// 		outputStream.close();
//
// 		connection.getInputStream().close();
// 		connection.disconnect();
// 	}
//
// 	private static class JSONObject {
//
// 		private final Map<String, Object> map = new HashMap<>();
//
// 		void put(String key, Object value) {
// 			if (value != null) {
// 				map.put(key, value);
// 			}
// 		}
//
// 		@Override
// 		public String toString() {
// 			StringBuffer sb = new StringBuffer();
// 			Set<Map.Entry<String, Object>> entrySet = map.entrySet();
// 			sb.append("{");
//
// 			int i = 0;
// 			for (Map.Entry<String, Object> entry : entrySet) {
// 				Object value = entry.getValue();
// 				sb.append(quote(entry.getKey())).append(":");
//
// 				if (value instanceof JSONObject || value instanceof Boolean) {
// 					sb.append(value);
// 				} else if (value instanceof String) {
// 					sb.append(quote(String.valueOf(value)));
// 				} else if (value instanceof Integer) {
// 					sb.append(Integer.valueOf(String.valueOf(value)));
// 				} else if (value.getClass().isArray()) {
// 					sb.append("[");
// 					int len = Array.getLength(value);
// 					for (int j = 0; j < len; j++) {
// 						sb.append(Array.get(value, j).toString()).append(j != len - 1 ? "," : "");
// 					}
// 					sb.append("]");
// 				}
// 				sb.append(++i == entrySet.size() ? "}" : ",");
// 			}
// 			return sb.toString();
// 		}
//
// 		private String quote(String str) {
// 			return "\"" + str + "\"";
// 		}
// 	}
//
// 	private static class Embeds {
// 		private String title;
// 		private String description;
//
// 		public Embeds(String title, String description) {
// 			this.title = title;
// 			this.description = description;
// 		}
// 	}
// }
//
