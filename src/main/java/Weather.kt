import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import java.util.*

class Weather {
    //90bacb11e84cc6f4c871b36ff85ace4e
    //6fff53a641b9b9a799cfd6b079f5cd4e
    companion object {
        val token = "6fff53a641b9b9a799cfd6b079f5cd4e"

        fun getWeather(message: String, model: Model): String {
            val url = URL("http://api.openweathermap.org/data/2.5/weather?q=${message}&units=metric&appid=${token}")
            val stream = url.content as InputStream
            val scanner = Scanner(stream)
            var result = ""
            while (scanner.hasNext()) {
                result +=scanner.nextLine()
            }
            val jsonObject = JSONObject (result)
            model.name = jsonObject.getString("name")
            val jsonMain = jsonObject.getJSONObject("main")
            model.temp = jsonMain.getDouble("temp")
            model.humidity = jsonMain.getDouble("humidity")

            val array = jsonObject.getJSONArray("weather")
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                model.icon = obj.getString("icon")
                model.main = obj.getString("main")
            }

            return """
            City: ${model.name} 
            Temperature: ${model.temp} C
            Humidity: ${model.humidity} %
            Weather like ${model.main}
            http://openweathermap.org/img/w/${model.icon}.png
            """.trimMargin()
        }
    }
}